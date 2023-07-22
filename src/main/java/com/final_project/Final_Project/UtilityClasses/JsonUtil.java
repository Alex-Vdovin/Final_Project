package com.final_project.Final_Project.UtilityClasses;

import com.final_project.Final_Project.entity.Balance;
import com.google.gson.GsonBuilder;

public class JsonUtil {
    private JsonUtil(){}
    public static String writeBalanceToJson(Balance balance){
        return new GsonBuilder().setPrettyPrinting().create().toJson(balance);
    }
}
