package com.final_project.Final_Project.entity;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="BALANCE")
@Data
public class Balance {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance1 = (Balance) o;
        return id.equals(balance1.id) && balance.equals(balance1.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="balance")
    Long balance;
}
