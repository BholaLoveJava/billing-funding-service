package com.world.web.utility;

import com.world.web.model.Car;
import com.world.web.model.Truck;
import com.world.web.model.Vehicle;

public class DataOrientedPattern {

    /**
     * Dispatcher method for Truck Processing Logic
     * @param truck as input parameter
     * @return String
     */
    public static String processTruck(Truck truck) {
        return "Processing logic for truck :: " + truck;
    }

    /**
     * Dispatcher method for Car processing Logic
     * @param car as input parameter
     * @return String
     */
    public static String processCar(Car car) {
        return "Processing logic for car :: " + car;
    }

    public static String process(Vehicle vehicle) {
        /*For better readability, we can use Switch Pattern Matching Features from Java 19 */
        /*
          switch(vehicle) {
           case Truck truck -> processTruck(truck);
           case Car car -> processCar(car);
           default -> "no clue";
          }
         */
        if (vehicle instanceof Truck truck) {
            return processTruck(truck);
        } else if (vehicle instanceof Car car) {
            return processCar(car);
        } else {
            return "no clue";
        }
    }

    public static void main(String[] args) {
        System.out.println(process(new Car()));
        System.out.println(process(new Truck()));
    }
}
