package com.final_project.Final_Project.UtilityClasses;

import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.entity.UserOperation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class JsonUtil {
    private JsonUtil() {
    }

    public static String writeBalanceToJson(Balance balance) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(balance);
    }

    public static Balance jsonToBalance(String json) {
        return new Gson().fromJson(json, Balance.class);
    }

    public static String writeOperationResultToJson(OperationResult operationResult) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(operationResult);
    }

    public static OperationResult jsonToOperationResult(String json) {
        return new Gson().fromJson(json, OperationResult.class);
    }

    public static String writeOperationListToJson(List<UserOperation> operationList) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(operationList);
    }
}
