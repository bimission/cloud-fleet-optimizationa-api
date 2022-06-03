package com.google.demos.generators.location;

import com.google.demos.model.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCityLocationGenerator {


    public List<Location> generateLocations(int numberOfLocations, Location areaLeftBottomCorner,
                                            Location areaRightTopCorner) {

        Random randomGenerator = new Random();
        List<Location> locations = new ArrayList();

        double latitudeDelta = areaRightTopCorner.getLatitude() - areaLeftBottomCorner.getLatitude();
        double longitudeDelta = areaRightTopCorner.getLongitude() - areaLeftBottomCorner.getLongitude();

            for (int i = 0; i < numberOfLocations; i++) {
                Location newLocation = new Location(
                         areaLeftBottomCorner.getLatitude() + latitudeDelta * randomGenerator.nextDouble(),
                        areaLeftBottomCorner.getLongitude() + longitudeDelta* randomGenerator.nextDouble()
                );

                locations.add(newLocation);
            }

            return locations;
        }


}
