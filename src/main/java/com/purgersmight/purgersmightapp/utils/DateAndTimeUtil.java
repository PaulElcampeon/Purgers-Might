package com.purgersmight.purgersmightapp.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@Scope("singleton")
public class DateAndTimeUtil {

    public String getDate() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String formattedDate = dateFormatter.format(LocalDate.now());

        return formattedDate;
    }

    public String getTime() {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedTime = timeFormatter.format(LocalTime.now());

        return formattedTime;
    }
}
