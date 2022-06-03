package com.google.demos.generators.shipment;

import com.github.javafaker.Faker;
import com.google.demos.helper.DatatimeUtils;
import com.google.demos.model.Location;
import com.google.demos.model.ShipmentVisit;
import com.google.demos.model.TimeWindow;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class VisitGenerator {

    public ShipmentVisit generateVisit(Location loc){

        Random randomGenerator = new Random();
        int duration = (int) (600*Math.round(Math.abs(randomGenerator.nextGaussian()*20*60+(5*60))/600));  //standard deviation: 5min Mean: 10 min
        if(duration<10*60) duration = 10*60;
        String durationAsString = duration+"s";
        //System.out.println(durationAsString);

        ShipmentVisit v = new ShipmentVisit();
        v.setArrivalLocation(loc);
        v.setDuration(durationAsString);


        //Allowed Delivery windows
        Date now = new Date();
        LocalDate nowAsLocalDate = now.toInstant().atZone(ZoneId.of("Europe/Warsaw")).toLocalDate();


        int startHour = 8+ randomGenerator.nextInt(12);
        int windowHours = 2;

        String startOfWindow = DatatimeUtils.buildDateAsString(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
                startHour,0,0);

        String endOfWindow = DatatimeUtils.buildDateAsString(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
                startHour+windowHours,0,0);

        TimeWindow allowedWindow = new TimeWindow();
        allowedWindow.setStartTime(startOfWindow);
        allowedWindow.setEndTime(endOfWindow);

        List<TimeWindow> visitAllowedWindows = new ArrayList();
        visitAllowedWindows.add(allowedWindow);

        v.setTimeWindows(visitAllowedWindows);

        return v;
    }

}
