package com.world.web.utility;

class DatabaseService {

    /*static block executed only one time, when the class loading started */
    static {
        System.out.println("Connecting to Database");
        System.out.println("Database connection Successful");
    }

    /*Instead of static method, use static Block for better performance.*/
    public static void connect() {
        System.out.println("Connecting to Database");
        System.out.println("Database connection Successful");
    }
}

class BookingService extends DatabaseService {

    public void bookingDbConnect() {
        System.out.println("Booking DB Connect Started");
        /*This call will trigger static connect() method to re-execute*/
        connect();
        System.out.println("Booking DB Connect Successful");
    }
}

class InventoryService extends DatabaseService {

    public void inventoryDbConnect() {
        System.out.println("Inventory DB Connect Started");
        /*This call will trigger static connect() method to re-execute */
        connect();
        System.out.println("Inventory DB Connect Successful");
    }
}

public class StaticUtility {

    public static void main(String[] args) {

        BookingService bookingService = new BookingService();
        bookingService.bookingDbConnect();

        InventoryService inventoryService = new InventoryService();
        inventoryService.inventoryDbConnect();
    }
}
