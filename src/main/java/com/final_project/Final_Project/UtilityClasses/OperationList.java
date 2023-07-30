package com.final_project.Final_Project.UtilityClasses;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OperationList {
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    private Timestamp fromTimestamp;
    private Timestamp toTimestamp;
    public Date parseDate;
}
