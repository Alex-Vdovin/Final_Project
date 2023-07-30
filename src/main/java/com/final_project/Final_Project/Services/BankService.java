package com.final_project.Final_Project.Services;

import com.final_project.Final_Project.UtilityClasses.JsonUtil;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.entity.UserOperation;
import com.final_project.Final_Project.UtilityClasses.OperationResult;
import com.final_project.Final_Project.enums.OperationsTypes;
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

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.final_project.Final_Project.enums.OperationsTypes.*;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BalanceRepository balanceRepository;
    private final OperationsRepository operationsRepository;

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
            Session session = null;
            try {
                session = sessionFactory.openSession();
                session.beginTransaction();
                Query<Balance> query = session.createQuery("FROM Balance as bl WHERE bl.id =:id");
                query.setParameter("id", id);
                Balance balance = null;
                try {
                    balance = query.getSingleResult();
                }catch (NoResultException e){
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(-1)
                            .setOperationMessage("Данного пользователя не существует"));
                }
                    operation_log(id, OperationsTypes.GET_BALANCE);
                    return JsonUtil.writeBalanceToJson(balance);
            }catch (Exception e){
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }finally {
                session.close();
            }

        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
    }

    public String takeMoney(Long id, Long money) {
        if(money < 0){
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно взять отрицательную сумму"));
        }
        try {
            Balance balance = JsonUtil.jsonToBalance(getBalance(id));
            try {
                Long newAmount = balance.getBalance() - money;
                if (newAmount >= 0) {
                    balance.setBalance(newAmount);
                    operation_log(id, TAKE_MONEY);
                    balanceRepository.save(balance);
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(1)
                            .setOperationMessage("Со счета User " + id + " снято " + money));
                } else {
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(0)
                            .setOperationMessage("Недостаточно средств"));
                }
            } catch (NullPointerException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Данного пользователя не существует"));
            }

        } catch (Exception e) {
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }

    }

    public String putMoney(Long id, Long money) {
        if(money < 0){
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно добавить отрицательную сумму"));
        }
        try {
            Balance balance = JsonUtil.jsonToBalance(getBalance(id));
            try {
                Long newAmount = balance.getBalance() + money;
                balance.setBalance(newAmount);
                operation_log(id, PUT_MONEY);
                balanceRepository.save(balance);
            } catch (NullPointerException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Данного пользователя не существует"));
            }
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(1)
                    .setOperationMessage("На счет User " + id + " добавлено " + money));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationError(Arrays.toString(e.getStackTrace())));
        }
    }
    public String transferMoney(Long userSenderId, Long userReceiverId, Long money) {
        if(money < 0){
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(0)
                    .setOperationMessage("Невозможно отправить отрицательную сумму"));
        }
        try {
            if(userSenderId.equals(userReceiverId)){
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("У отправителя и получателя не может быть одинаковый id"));
            }
            int resultSender = JsonUtil.jsonToOperationResult(takeMoney(userSenderId, money)).getResult();
            int resultReceiver = JsonUtil.jsonToOperationResult(putMoney(userReceiverId, money)).getResult();
            if (resultSender == 0 || resultReceiver == 0) {
                if (JsonUtil.jsonToOperationResult(takeMoney(userSenderId, money)).getOperationMessage().equals("Данного пользователя не существует")) {
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(0)
                            .setOperationMessage("Отправителя не существует"));
                } else if (JsonUtil.jsonToOperationResult(putMoney(userReceiverId, money))
                        .getOperationMessage()
                        .equals("Данного пользователя не существует")) {
                    return JsonUtil.writeOperationResultToJson(new OperationResult()
                            .setResult(0)
                            .setOperationMessage("Получателя не существует"));
                }
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(0)
                        .setOperationMessage("Недостаточно средств у User " + userSenderId));
            } else if (resultSender == 1 && resultReceiver == 1) {
                operation_log(userSenderId, TRANSFER_MONEY);
                putMoney(userReceiverId, money);
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(1)
                        .setOperationMessage("User " + userSenderId + " отправил " + money + " user " + userReceiverId));
            } else {
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

    public void operation_log(Long userId, OperationsTypes operationsTypes) {
        UserOperation operation = new UserOperation();
        operation.setUserId(userId);
        operation.setOperationTypeName(operationsTypes.getOperationTypeName());
        operation.setOperationTypeNumber(operationsTypes.getOperationTypeNumber());
        operation.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        operationsRepository.save(operation);
    }
    public String getOperationList(Long userId, String fromStringDate, String toStringDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate;
        Date toDate;
        Timestamp fromTimestamp = null;
        Timestamp toTimestamp = null;

        if(fromStringDate != null && toStringDate != null){
            try {
                fromDate = dateFormat.parse(fromStringDate);
                toDate = dateFormat.parse(toStringDate);
                fromTimestamp = new Timestamp(fromDate.getTime());
                toTimestamp = new Timestamp(toDate.getTime());
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты начала и/или окончания")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        } else if (toStringDate == null && fromStringDate != null) {
            try {
                fromDate = dateFormat.parse(fromStringDate);
                fromTimestamp = new Timestamp(fromDate.getTime());
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты начала")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        } else if (toStringDate != null){
            try {
                toDate = dateFormat.parse(toStringDate);
                toTimestamp = new Timestamp(toDate.getTime());
            } catch (ParseException e) {
                return JsonUtil.writeOperationResultToJson(new OperationResult()
                        .setResult(-1)
                        .setOperationMessage("Возникла ошибка с извлечением даты окончания")
                        .setOperationError(Arrays.toString(e.getStackTrace())));
            }
        }

        if(fromTimestamp != null && toTimestamp != null && fromTimestamp.after(toTimestamp) ){
            return JsonUtil.writeOperationResultToJson(new OperationResult()
                    .setResult(-1)
                    .setOperationMessage("Дата начала не может быть больше даты окончания"));
        }
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(UserOperation.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserOperation> userOperationList;
        String idQuery = "FROM UserOperation as pl WHERE pl.userId =:id ";
        if(fromTimestamp != null && toTimestamp != null){
            Query<UserOperation> query = session.createQuery(idQuery + "AND pl.timeStamp > :fromTimestamp AND pl.timeStamp < :toTimestamp");
            query.setParameter("id", userId).
                    setParameter("fromTimestamp", fromTimestamp)
                    .setParameter("toTimestamp", toTimestamp);
            userOperationList = query.list();
        } else if (fromTimestamp != null) {
            Query<UserOperation> query = session.createQuery(idQuery + "AND pl.timeStamp > :fromTimestamp");
            query.setParameter("id", userId).
                    setParameter("fromTimestamp", fromTimestamp);
            userOperationList = query.list();
        } else if(toTimestamp != null){
            Query<UserOperation> query = session.createQuery(idQuery + "AND pl.timeStamp < :toTimestamp");
            query.setParameter("id", userId).
                    setParameter("toTimestamp", toTimestamp);
            userOperationList = query.list();
        }else {
            Query<UserOperation> query = session.createQuery(idQuery);
            query.setParameter("id", userId);
            userOperationList = query.list();
        }
        session.getTransaction().commit();
        return JsonUtil.writeOperationListToJson(userOperationList);

    }


}
