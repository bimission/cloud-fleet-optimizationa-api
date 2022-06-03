package com.google.demos;


//Last Mile Fleet Solution --- mainy Google Maps Platform
//Cloud Fleet Routing API - part of Last Mile Fleet Solutions - but part of GCP SLA
//Up to 25 stops per route

//https://docs.google.com/presentation/d/12V1m_NeIUJNGOG_aRr0MbtXIz66O4mTLw6s2jha_i2o/edit?resourcekey=0-9tC1PuFa_xr9xnVYE_XD0A#slide=id.g90afa5a234_2_5

//https://cloud.google.com/optimization/docs/overview

import com.github.javafaker.Faker;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.demos.generators.location.RandomCityLocationGenerator;
import com.google.demos.generators.shipment.VisitGenerator;
import com.google.demos.generators.vehicle.VehicleGenerator;
import com.google.demos.helper.DatatimeUtils;
import com.google.demos.model.*;
import com.google.demos.utils.CredentialsUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//Vehicle Routing Problem
public class CFleetRoutingMain {

    public static void main(String[] args) throws IOException {

        String jsonPath = "/Users/lolejniczak/Downloads/ce-fleetrouting-9f9fac3c29e4.json";


        //Enable mapsfleetrouting.googleapis.com api (Google Maps for Fleet Routing

        int numberOfShipments = 10;
        int numberOfVehicles = 5;
        String gcpProjectId = "ce-fleetrouting";//lolejniczak00p1";

        int startHour = 8;
        int shiftInterval = 14;
        int endHour = startHour + shiftInterval;

        //Losouj wizyty w tym obszarze
        Location areaLeftBottomCorner = new Location(52.154996877313, 20.919926120993125);
        Location areaRightTopCorner = new Location(52.32715709266114, 21.13445849633365);

        //Auta startuja i koncza w tym punkcie
        Location vehicleStartLocation = new Location(52.230924, 20.983260);


        //1970-01-01T01:06:41Z
        String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat cfrDateFormat = new SimpleDateFormat(pattern);

        RandomCityLocationGenerator g = new RandomCityLocationGenerator();
        List<Location> locations = g.generateLocations(numberOfShipments, areaLeftBottomCorner, areaRightTopCorner);

        VisitGenerator vg = new VisitGenerator();

        List<Shipment> shipments = new ArrayList();


        for(int i = 0; i<locations.size(); i++){
        List<ShipmentVisit> deliveries = new ArrayList();
        List<ShipmentVisit> pickups = new ArrayList();

            if(i % 2 == 0){
                deliveries.add(vg.generateVisit(locations.get(i)));
            }else{
                pickups.add(vg.generateVisit(locations.get(i)));
            }

            Shipment shipment = new Shipment(deliveries, pickups);
            Faker faker = new Faker();
            String orderId = faker.bothify("#ID##??7????");
            shipment.setLabel(orderId.toUpperCase());
            shipments.add(shipment);
        }


        List<Vehicle> vehicles = new ArrayList();
        VehicleGenerator vgen = new VehicleGenerator();

        for(int j=0;j<numberOfVehicles;j++){
            Vehicle v = vgen.generateVehicle(vehicleStartLocation);
            vehicles.add(v);
        }
        ShipmentModel shipmentModel = new ShipmentModel();
        shipmentModel.setShipments(shipments);
        shipmentModel.setVehicles(vehicles);
        //The model's time span must be less than a year,


        Date now = new Date();
        LocalDate nowAsLocalDate = now.toInstant().atZone(ZoneId.of("Europe/Warsaw")).toLocalDate();


        Date startDate = DatatimeUtils.buildDate(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
        startHour,0,0);

        Date endDate = DatatimeUtils.buildDate(
                nowAsLocalDate.getYear(),
                nowAsLocalDate.getMonthValue(),
                nowAsLocalDate.getDayOfMonth(),
                endHour,0,0);

        shipmentModel.setGlobalStartTime(cfrDateFormat.format(startDate));
        shipmentModel.setGlobalEndTime(cfrDateFormat.format(endDate));



        //// Send request to generate solution
        FleetRoutingRequest request = new FleetRoutingRequest();
        request.setModel(shipmentModel);
        request.setParent("projects/"+gcpProjectId);
        request.setSearchMode("RETURN_FAST");
        request.setTimeout("120s");

        //Wysylamy model do Optmization API
        //---------------------------------------------------------------------
        Gson gson = new Gson();
        String requestBody = gson.toJson(request);

        System.out.println("--------------------------------");
        System.out.println(requestBody);
        System.out.println("--------------------------------");

        //gcloud auth application-default print-access-token
        String gcpAccessToken = CredentialsUtils.accessToken;

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(jsonPath))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");

        credentials.refresh();


        String ACCESS_TOKEN = credentials.getAccessToken().getTokenValue();

        String BASE_URL = "https://cloudoptimization.googleapis.com/v1/projects/"
                + gcpProjectId + ":optimizeTours?access_token="+ACCESS_TOKEN;//key="+gcpAccessToken;

        OkHttpClient client = new OkHttpClient();
        MediaType JSON  = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, requestBody);

        Request httpRequest = new Request.Builder()
                //.header("Authorization", "Bearer +"+ACCESS_TOKEN)
                .url(BASE_URL)
                .post(body)
                .build();

        Call call = client.newCall(httpRequest);
        Response response = call.execute();
        String responseBody = response.body().string();
        System.out.println(responseBody);
        System.out.println(response.code());
        //---------------------------------------------------------------

        // Busujemy ZIPa z modelem i rozwiazaniem pod wizualizacje.
        List<String> cloudfleetFiles = Arrays.asList("scenario.json", "solution.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(cloudfleetFiles.get(0)));
        writer.write(requestBody);
        writer.close();

        writer = new BufferedWriter(new FileWriter(cloudfleetFiles.get(1)));
        writer.write(responseBody);
        writer.close();

        //Save to zip file
        FileOutputStream fos = new FileOutputStream("/Users/lolejniczak/lolejniczak-cloudfleet-demo-cfr.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : cloudfleetFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();


    }
}
