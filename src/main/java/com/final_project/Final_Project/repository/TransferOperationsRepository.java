package com.final_project.Final_Project.repository;

import com.final_project.Final_Project.entity.TransferOperation;
import com.final_project.Final_Project.entity.UserOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferOperationsRepository extends JpaRepository<TransferOperation, Long> {
}
