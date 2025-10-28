package com.world.web.utility;

import com.world.web.model.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorsTeeingUtility {

    enum Color {RED, BLUE, WHITE, YELLOW}

    enum Engine {ELECTRIC, HYBRID, GAS}

    enum Drive {WD2, WD4}

    public static void main(String[] args) {

        /*
         * The teeing collector is created by the Collectors.teeing() factory method that takes three arguments.
         * A first downstream collector, used to collect the data of your stream.
         * A second downstream collector, also used to collect your data, in an independent way.
         * A BiFunction, used to merge the two containers created by the two downstream collector.
         * Your data is processed in one pass to guarantee the best performances.
         */

        record Car(Color color, Engine engine, Drive drive, int passengers) implements Vehicle {
        }
        record Truck(Engine engine, Drive drive, int weight) implements Vehicle {
        }

        Collection<Vehicle> vehicles = List.of(new Car(Color.BLUE, Engine.ELECTRIC, Drive.WD2, 4),
                new Car(Color.RED, Engine.HYBRID, Drive.WD4, 5),
                new Truck(Engine.GAS, Drive.WD2, 8000),
                new Truck(Engine.GAS, Drive.WD4, 12000));
        /*
         * Collectors.teeing
         * Collectors.filtering(Predicate, DownStreamCollectors) -> produces first DownStreamCollectors Result
         * Collectors.filtering(Predicate, DownStreamCollectors) -> produces second DownStreamCollectors Result
         * BiFunction combiner, to combine both DownStreamCollectors to Single Result
         */
        List<Vehicle> electricVehicle =
                vehicles.stream()
                .collect(Collectors.teeing(
                        //Collectors.filtering() produces First DownStreamCollectors  and Second DownStreamCollectors
                        Collectors.filtering(vehicle -> vehicle instanceof Car car && car.engine() == Engine.ELECTRIC, Collectors.toList()),
                        Collectors.filtering(vehicle -> vehicle instanceof Truck truck && truck.engine() == Engine.ELECTRIC, Collectors.toList()),
                        //BiFunction Combiner
                        (cars, trucks) -> {
                            cars.addAll(trucks);
                            return cars;
                        }
                ));
        System.out.println("Electric Vehicle result :: "+ electricVehicle);
    }
}
