package com.cathaybank.coindesk.config;

import java.time.format.DateTimeFormatter;

public class Constant {
    private Constant(){}

    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
