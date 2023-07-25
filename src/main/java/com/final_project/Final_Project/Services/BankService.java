package com.final_project.Final_Project.Services;

import com.final_project.Final_Project.UtilityClasses.JsonUtil;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.entity.Operation;
import com.final_project.Final_Project.entity.OperationResult;
import com.final_project.Final_Project.repository.BalanceRepository;
import com.final_project.Final_Project.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BalanceRepository balanceRepository;
    private final OperationsRepository operationsRepository;
    private static final String PUT_MONEY = "PUT_MONEY";
    private static final String TAKE_MONEY = "TAKE_MONEY";
    private static final String TRANSFER_MONEY = "TRANSFER_MONEY";
    private static final String GET_BALANCE = "GET_BALANCE";

    public String getBalance(Long id) {
        try {
            SessionFactory sessionFactory = null;
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            try {
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(Balance.class)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Balance> query = criteriaBuilder.createQuery(Balance.class);
            Root<Balance> root = query.from(Balance.class);
            query.select(root);

            Query<Balance> quers = session.createQuery(query);
            List<Balance> balances = quers.getResultList();
            if (!balances.isEmpty()) {
                Optional<Balance> result;
                result = balances.stream().filter(b -> b.getId().equals(id)).findFirst();
                if (!result.isEmpty()) {
                    System.out.println(result.get());
                    String res = JsonUtil.writeBalanceToJson(result.get());
                    operation_log(result.get().getId(), GET_BALANCE);
                    return res;
                } else {
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(-1)
                            .setOperationMessage("Данного пользователя не существует"));
                }
            } else {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Пользователей в таблице не найдено"));
            }
        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
    }

    public String takeMoney(Long id, Long money) {
        try {
            Balance balance = JsonUtil.jsonToBalance(getBalance(id));
            Long newAmount = balance.getBalance() - money;
            if (newAmount >= 0) {
                balance.setBalance(newAmount);
                balanceRepository.save(balance);
                operation_log(id, TAKE_MONEY);
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(1)
                        .setOperationMessage("Со счета User " + id + " снято " + money));
            } else {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Недостаточно средств"));
            }
        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }

    }

    public String putMoney(Long id, Long money) {
        try {
            Balance balance = JsonUtil.jsonToBalance(getBalance(id));
            Long newAmount = balance.getBalance() + money;
            balance.setBalance(newAmount);
            balanceRepository.save(balance);
            operation_log(id, PUT_MONEY);
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(1)
                    .setOperationMessage("На счет User " + id + " добавлено " + money));
        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
    }

    public String getOperationList(Long id) {
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Operation.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Operation> query = criteriaBuilder.createQuery(Operation.class);
        Root<Operation> root = query.from(Operation.class);
        query.select(root);

        Query<Operation> quers = session.createQuery(query);
        List<Operation> operations = quers.getResultList();
        if (!operations.isEmpty()) {
            operations = operations.stream().
                    filter(operation -> operation.getUserId().
                            equals(id)).
                    collect(Collectors.toList());
        }
        if (operations.isEmpty()) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationMessage("Не найдено пользователей с данным id"));
        } else {
            return JsonUtil.writeOperationListToJson(operations);
        }
    }

    public String transferMoney(Long userSenderId, Long userReceiverId, Long money) {
        try {
            int result = JsonUtil.jsonToOperationResult(takeMoney(userSenderId, money)).getResult();
            if (result == 1) {
                putMoney(userReceiverId, money);
                operation_log(userSenderId, TRANSFER_MONEY);
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(1)
                        .setOperationMessage("User " + userSenderId + " отправил " + money + " user " + userReceiverId));
            } else if (result == 0){
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Недостаточно средств у User " + userSenderId));
            }else {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Произошла ошибка"));
            }
        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Произошла ошибка")
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
    }

    public void operation_log(Long userId, String operationType) {
        Operation operation = new Operation();
        operation.setUserId(userId);
        operation.setOperationType(operationType);
        operation.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        operationsRepository.save(operation);
    }

}
