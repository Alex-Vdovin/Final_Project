package com.final_project.Final_Project.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "operation_list")
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "operation_time")
    Timestamp timeStamp;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "operation_type")
    String operationType;
}
