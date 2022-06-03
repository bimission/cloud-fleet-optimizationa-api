package com.google.demos.generators.vehicle;

import com.github.javafaker.Faker;
import com.google.demos.helper.DatatimeUtils;
import com.google.demos.model.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class VehicleGenerator {

    public Vehicle generateVehicle(Location loc){

        Random randomGenerator = new Random();
        int weightLimit = randomGenerator.nextInt(100);
        VehicleLoadLimit limit = new VehicleLoadLimit();
        limit.setMaxLoad(weightLimit);

        Map<String, VehicleLoadLimit> loadLimits = new HashMap();
        loadLimits.put("weight", limit);

        Faker faker = new Faker();
        String label = faker.name().fullName();


        Vehicle v = new Vehicle();
        v.setStartLocation(loc);
        v.setEndLocation(loc);
        v.setLoadLimits(loadLimits);
        v.setLabel(label);

        DecimalFormat df = new DecimalFormat("0.00");
        // stawka godzinowa kierowcy
        double driverHourlyRate= Math.abs(randomGenerator.nextGaussian()*15+30); //30 +/- 15
        v.setCostPerHour(Double.parseDouble(df.format(driverHourlyRate)));
        //koszty auta na 1 km
        double spalanieNa100km = Math.abs(randomGenerator.nextGaussian()*7+2);// 7l +-2
        double kosztLitrabenzyny = 6.5;
        double carKmRate= spalanieNa100km * kosztLitrabenzyny/100.0;
        v.setCostPerKilometer(Double.parseDouble(df.format(carKmRate)));

        VehicleDurationLimit durationLimit = new VehicleDurationLimit();
        durationLimit.setMaxDuration(8*60*60+"s");
        v.setRouteDurationLimit(durationLimit); //8h = 8*60*60;


        //At least one 30min break per 12hour - lunch
        VehicleBreakRequest breakRequest = new VehicleBreakRequest();

        Date now = new Date();
        LocalDate nowAsLocalDate = now.toInstant().atZone(ZoneId.of("Europe/Warsaw")).toLocalDate();

        String minStart = DatatimeUtils.buildDateAsString(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
                12,0,0);

        String maxStart = DatatimeUtils.buildDateAsString(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
                15,0,0);

        breakRequest.setEarliestStartTime(minStart); //miedzy 12
        breakRequest.setLatestStartTime(maxStart); //a 15
        breakRequest.setMinDuration(30*60+"s");//30min

        List<VehicleBreakRequest> breakRequests = new ArrayList();
        breakRequests.add(breakRequest);

        List<VehicleBreakFrequencyConstraint> breakConstraints = new ArrayList();
        VehicleBreakFrequencyConstraint constraint = new VehicleBreakFrequencyConstraint();
        constraint.setMinBreakDuration(30*60+"s");
        constraint.setMaxInterBreakDuration(12*60*60+"s");
        breakConstraints.add(constraint);

        VehicleBreakRule breakRule = new VehicleBreakRule();
        breakRule.setBreakRequests(breakRequests);
        breakRule.setFrequencyConstraints(breakConstraints);
        v.setBreakRule(breakRule);

        return v;
    }
}
