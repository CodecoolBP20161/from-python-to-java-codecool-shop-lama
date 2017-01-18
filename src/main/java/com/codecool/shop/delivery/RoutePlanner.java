package com.codecool.shop.delivery;

import org.apache.http.client.utils.URIBuilder;

import java.util.*;

/**
 * Created by prezi on 2017. 01. 16..
 */
public class RoutePlanner {
    private static final String API_URL = "http://0.0.0.0:60003/api/timecalculator";
    private HashMap<Set<String>, Long> distanceMap;

    public RoutePlanner(ArrayList<String> listOfLocations){
        // todo: use method that transfers input to locationGraph and saves it
    }

    private HashMap<Set<String>, Long> createDistanceMap(ArrayList<String> listOfLocations){
        HashMap<Set<String>, Long> distanceMap = new HashMap<>();
        for (int i = 0; i < listOfLocations.size(); i++) {
            for (int j = i; j < listOfLocations.size(); j++) {
                Set locationPair = new HashSet<>();
                locationPair.add(listOfLocations.get(i));
                locationPair.add(listOfLocations.get(j));
                long distance = getDistance(listOfLocations.get(i), listOfLocations.get(j));
                distanceMap.put(locationPair, distance);
            }
        }

        return null;
    }

    private Long getDistance(String startLocation, String endLocation){
        //
        //
        // import Uri builder and etc
        //
        //
        return (long) Math.random() * 100;
    }

    private void TravellingSalesman(){
        // todo: uses an algorithm to find a solution and save it
    }

    public List planRoute(int time){
        // todo: cut up solutionList according to how much time the driver has and return it
        return null;
    }

}
