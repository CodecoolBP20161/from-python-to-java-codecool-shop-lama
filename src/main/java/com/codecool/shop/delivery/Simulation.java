package com.codecool.shop.delivery;


public class Simulation {

    private Route route;
    double startingTemperature;
    int numberOfIterations;
    double coolingRate;
    public Route solution;

    public Simulation(Route route, double startingTemperature, int numberOfIterations, double coolingRate){
        this.route = route;
        this.startingTemperature = startingTemperature;
        this.numberOfIterations = numberOfIterations;
        this.coolingRate = coolingRate;
    }

    public void simulateAnnealing() {
        double t = startingTemperature;
        long bestDistance = route.calculateTotalDistance();
        Route bestSolution = route;
        Route currentSolution = bestSolution;

        for (int i = 0; i < numberOfIterations; i++) {

            if (t > 0.1) {
                currentSolution.swapLocations();
                long currentDistance = currentSolution.calculateTotalDistance();
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
                    currentSolution.undoSwap();
                }
                t *= coolingRate;
            } else {
                continue;
            }
            if (i%100==0) {
                System.out.println("iteration: " +i);
                System.out.println("best distance: " + bestDistance);
                System.out.println("temp: " + t);
            }

        }
        this.solution = bestSolution;
    }

}
