package com.final_project.Final_Project.enums;

public enum OperationsTypes {
    GET_BALANCE(1, "GET_BALANCE"),
    PUT_MONEY(2, "PUT_MONEY"),
    TAKE_MONEY(3, "TAKE_MONEY"),
    TRANSFER_MONEY(4, "TRANSFER_MONEY");
    private Integer operationTypeNumber;
    private String operationTypeName;

    OperationsTypes(Integer operationTypeNumber, String operationTypeName) {
        this.operationTypeNumber = operationTypeNumber;
        this.operationTypeName = operationTypeName;
    }

    public Integer getOperationTypeNumber() {
        return operationTypeNumber;
    }

    public String getOperationTypeName() {
        return operationTypeName;
    }

    @Override
    public String toString() {
        return "OperationsTypes{" +
                "operationTypeNumber=" + operationTypeNumber +
                ", operationTypeName='" + operationTypeName + '\'' +
                '}';
    }
}
