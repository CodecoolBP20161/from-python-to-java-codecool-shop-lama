package com.codecool.shop.delivery;

import java.util.*;


public class Route {
    private ArrayList<String> route;
    private ArrayList<String> previousRoute;
    private HashMap<Set<String>, Long> distanceMap;

    {
        route = new ArrayList<>();
    }

    public Route(ArrayList<String> locations, HashMap<Set<String>, Long> distances) {
        for (int i = 0; i < locations.size(); i++) {
            route.add(locations.get(i));
        }
        Collections.shuffle(route);
        distanceMap = distances;
    }

    public void swapLocations() {
        Random random = new Random();
        int a = random.nextInt(route.size()-1);
        int b;
        do {
            b = random.nextInt(route.size()-1);
        }
        while (a == b);

        previousRoute = route;
        Collections.swap(route, a, b);
    }

    public void undoSwap() {
        route = previousRoute;
    }

    public int calculateTotalDistance() {
        int distance = 0;
        route.add(route.get(0));
        for (int i = 0; i < route.size()-1; i++) {
            String startLocation = route.get(i);
            String endLocation = route.get(i+1);
            distance += getDistance(startLocation, endLocation);
        }
        route.remove(route.size()-1);
        return distance;
    }

    private Long getDistance(String startLocation, String endLocation){
        Set<String> locationPair = new HashSet<>();
        locationPair.add(startLocation);
        locationPair.add(endLocation);
        return distanceMap.get(locationPair);
    }

    public ArrayList<String> getRoute(){
        return this.route;
    }
}
