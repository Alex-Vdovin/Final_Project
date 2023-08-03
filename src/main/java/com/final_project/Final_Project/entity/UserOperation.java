package com.final_project.Final_Project.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "operation_list")
@Data
public class UserOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "operation_type_number")
    Integer operationTypeNumber;
    @Column(name = "operation_type_name")
    String operationTypeName;
    @Column(name = "operation_time")
    Timestamp timeStamp;

}
