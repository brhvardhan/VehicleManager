package com.navi.vehiclemanager;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Driver {

    City city = new City();

    public boolean addNewBranch(City city, String[] args) {
        if (args.length != 3) {
            return false;
        }
        return city.addBranch(args[1], args[2]);
    }

    public boolean addNewVehicle(City city, String[] args) {
        // Sample Input - ADD_VEHICLE B1 CAR V1 500
        if (args.length != 5) {
            return false;
        }
        try {
            Branch branch = city.getBranch(args[1]);
            if (branch==null){
                return false;
            }
            return branch.addNewVehicle(args[2], args[3], Integer.parseInt(args[4]));
        } catch (Exception e) {
            return false;
        }
    }

    public int bookVehicle(City city, String[] args) {
        if (args.length!=5){
            return -1;
        }
        try {
            Branch b = city.getBranch(args[1]);
            return b.bookVehicle(args[2],Integer.parseInt(args[3]),Integer.parseInt(args[4]));
        } catch (Exception e) {
            return -1;
        }
    }

    public void displayVehicles(City city, String[] args){
        if (args.length!=4){
            return;
        }
        try {
            Branch b = city.getBranch(args[1]);
            b.showVehicles(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } catch (Exception e) {
            System.out.println();
            return;
        }
    }

    public void runCommand(String inputCommand){
        inputCommand = inputCommand.trim();
        if (inputCommand.length() == 0) {
            return;
        }
        String[] inputArgs = inputCommand.split(" ");
        switch (inputArgs[0].toUpperCase()) {
            case "ADD_BRANCH":
                System.out.println(Boolean.toString(addNewBranch(city, inputArgs)).toUpperCase());
                break;
            case "ADD_VEHICLE":
                System.out.println(Boolean.toString(addNewVehicle(city, inputArgs)).toUpperCase());
                break;
            case "BOOK":
                System.out.println(bookVehicle(city, inputArgs));
                break;
            case "DISPLAY_VEHICLES":
                displayVehicles(city, inputArgs);
                break;
            default:
                System.out.println("Invalid input command - " + inputArgs[0]);
        }
    }


    public void startProcess(String filePath) throws FileNotFoundException {
        filePath = filePath.trim();
        File file = new File(filePath);
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine())
            runCommand(fileScanner.nextLine());

    }

    public static void main(String[] args) {
        try {
            Driver d = new Driver();
            d.startProcess(args[0]);
        } catch (Exception e) {
            System.out.println("Exception Occurred, " + e);
        }
    }
}
