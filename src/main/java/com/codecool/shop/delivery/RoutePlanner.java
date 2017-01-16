package com.codecool.shop.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by prezi on 2017. 01. 16..
 */
public class RoutePlanner {
    private HashMap<Set<String>, Long> locationGraph;
    private List solutionList;

    public RoutePlanner(ArrayList<String> listOfLocations){
        // todo: use method that transfers input to locationGraph and saves it
    }

    private HashMap<Set<String>, Long> createLocationGraph(ArrayList<String> listOfLocations){
        // todo: transfers input to locationGraph and saves it
        // example: {(Bp, Miskolc) : 666}
        return null;
    }

    private Long calculateTime(Set<String> locationPair){
        // todo: use the API to calculate the travel time
        return null;
    }

    private void TravellingSalesman(){
        // todo: uses an algorithm to find a solution and save it
    }

    public List planRoute(int time){
        // todo: cut up solutionList according to how much time the driver has and return it
        return null;
    }

}
