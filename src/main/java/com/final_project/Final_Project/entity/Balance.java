package com.final_project.Final_Project.entity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="BALANCE")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="balance")
    Long balance;
}
