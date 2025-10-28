package com.world.web.features;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class AutoCloseableResourcesFirst implements AutoCloseable {

    public AutoCloseableResourcesFirst() {
        System.out.println("Constructor; AutoCloseableResources_First");
    }

    public void doSomething() {
        System.out.println("Something; AutoCloseableResources_First");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed AutoCloseableResources_First");
    }
}

class AutoCloseableResourcesSecond implements AutoCloseable {

    public AutoCloseableResourcesSecond() {
        System.out.println("Constructor; AutoCloseableResources_Second");
    }

    public void doSomething() {
        System.out.println("Something; AutoCloseableResources_Second");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed AutoCloseableResources_Second");
    }
}

public class TryWithResource {

    public static void main(String[] args) {
        //custom AutoCloseable
        try (AutoCloseableResourcesFirst af = new AutoCloseableResourcesFirst();
             AutoCloseableResourcesSecond as = new AutoCloseableResourcesSecond()) {
            af.doSomething();
            as.doSomething();
        } catch (Exception exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        }

        //try-with-resources
        try (Scanner scanner = new Scanner(new File("src/main/resources/testRead.txt"));
             PrintWriter printWriter = new PrintWriter("src/main/resources/testWrite.txt")) {
            while (scanner.hasNext()) {
                printWriter.print(scanner.nextLine());
            }
            System.out.println("File reading and writing successful");
        } catch (FileNotFoundException exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        }

        //Before Java7
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/testRead.txt"));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException exp) {
            System.out.println("Exception message :: " + exp.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
