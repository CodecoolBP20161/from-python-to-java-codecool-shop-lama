package com.codecool.shop.delivery;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class RoutePlanner {
    private static final String API_URL = "http://0.0.0.0:60003/api/timecalculator";
    private ArrayList<String> locations;
    private HashMap<Set<String>, Long> distanceMap;

    public RoutePlanner(ArrayList<String> listOfLocations){
        this.locations = listOfLocations;
        this.distanceMap = createDistanceMap(listOfLocations);
    }

    private HashMap<Set<String>, Long> createDistanceMap(ArrayList<String> listOfLocations){
        HashMap<Set<String>, Long> distanceMap = new HashMap<>();
        for (int i = 0; i < listOfLocations.size()-1; i++) {
            for (int j = i+1; j < listOfLocations.size(); j++) {
                Set locationPair = new HashSet<>();
                locationPair.add(listOfLocations.get(i));
                locationPair.add(listOfLocations.get(j));
                long distance = 0;
                    try {
                        System.out.println("in try");
                        distance = getDistance(listOfLocations.get(i), listOfLocations.get(j));
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        System.out.println(e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                distanceMap.put(locationPair, distance);
            }
        }
        return distanceMap;
    }

    private Long getDistance(String startLocation, String endLocation) throws IOException, URISyntaxException, JSONException {
        String url = API_URL;
        URIBuilder builder = new URIBuilder(url);
        builder.addParameter("origin", startLocation);
        builder.addParameter("destination", endLocation);
        String ApiAnswer = Request.Get(builder.build()).execute().returnContent().asString();
        System.out.println("api " + ApiAnswer);
        JSONObject json = new JSONObject(ApiAnswer);
        System.out.println("json " + json.toString());
        int distance = json.getInt("time");
        System.out.println("distance " + distance);
        return (long) distance;
    }

    public ArrayList<String> findSolution(){
        Route route = new Route(locations, distanceMap);
        String startLocation = route.getRoute().get(0);
        Simulation simulation = new Simulation(route, 20, 10000, 0.99);
        simulation.simulateAnnealing();
        ArrayList<String> solution = simulation.solution.getRoute();
        ArrayList<String> sortedSolution = new ArrayList<>();
        int startLocationIndex = solution.indexOf(startLocation);
        for (int i = startLocationIndex; i < solution.size(); i++) {
            sortedSolution.add(solution.get(i));
        }
        for (int i = 0; i < startLocationIndex; i++) {
            sortedSolution.add(solution.get(i));
        }
        return sortedSolution;
    }

}
