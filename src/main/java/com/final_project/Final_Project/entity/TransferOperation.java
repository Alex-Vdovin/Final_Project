package com.final_project.Final_Project.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transfer_operations")
@Data
public class TransferOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "usersenderid")
    Long userSenderId;
    @Column(name = "userreceiverid")
    Long userReceiverId;
    @Column(name = "transfermoney")
    Long transferMoney;
    @Column(name = "transfer_operation_time")
    Timestamp transferOperationTime;
}
