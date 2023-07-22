package com.final_project.Final_Project.Services;

import com.final_project.Final_Project.UtilityClasses.JsonUtil;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.repository.BalanceRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BalanceRepository balanceRepository;

    public String addUser(Long id, Long userBalance){
        Balance balance = new Balance();
        balance.setId(id);
        balance.setBalance(userBalance);
        balanceRepository.save(balance);
        return "User " + id + " with balance " + userBalance + " was added ";
    }
    public static String getBalance(Long id){
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Balance.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }catch (Exception e){
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Balance> query = criteriaBuilder.createQuery(Balance.class);
        Root<Balance> root = query.from(Balance.class);
        query.select(root);

        Query<Balance> quers = session.createQuery(query);
        List<Balance> balances = quers.getResultList();
        if(!balances.isEmpty()){
            Optional<Balance> result = null;
            result = balances.stream().filter(b -> b.getId().equals(id)).findFirst();
            if(result.isPresent()){
                String res = JsonUtil.writeBalanceToJson(result.get());
                System.out.println(res);
                return res;
            }else {
                return "-1";
            }
        }
       return "-1";
    }
}
