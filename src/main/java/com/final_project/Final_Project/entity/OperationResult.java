package com.final_project.Final_Project.entity;

import java.util.List;

public class OperationResult {
    int result;
    String operationMessage;
    String operationError;

    public OperationResult(int result) {
        this.result = result;
    }

    public OperationResult() {

    }

    public OperationResult(int result, String operationMessage) {
        this.result = result;
        this.operationMessage = operationMessage;
    }

    public OperationResult(int result, String operationMessage, String operationError) {
        this.result = result;
        this.operationMessage = operationMessage;
        this.operationError = operationError;
    }

    public int getResult() {
        return result;
    }

    public OperationResult setResult(int result) {
        this.result = result;
        return this;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public OperationResult setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
        return this;
    }

    public String getOperationError() {
        return operationError;
    }

    public OperationResult setOperationError(String operationError) {
        this.operationError = operationError;
        return this;
    }
}
