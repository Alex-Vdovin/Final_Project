package com.final_project.Final_Project.repository;

import com.final_project.Final_Project.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long> {
}
