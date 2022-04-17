package com.navi.vehiclemanager;

import java.util.*;

class Branch {
    String name;
    HashMap<String, ArrayList<Vehicle>> vehiclesMap = new HashMap<String, ArrayList<Vehicle>>();
    HashSet<String> vehicleUniqueId = new HashSet<String>();

    Branch(String branchName, String vehicleTypes) {
        name = branchName;
        setVehicleTypes(vehicleTypes.split(","));
    }

    private void setVehicleTypes(String[] types) {
        for (int i = 0; i < types.length; i++) {
            vehiclesMap.put(types[i].trim().toLowerCase(), new ArrayList<Vehicle>());
        }
    }

    public void addVehicleTypes(String vehicleTypes) {
       setVehicleTypes(vehicleTypes.split(","));
    }

    public boolean addNewVehicle(String vehicleType, String vehicleName, int cost) {
        vehicleType = vehicleType.toLowerCase();

        if (!vehiclesMap.containsKey(vehicleType)) {
            // Vehicle type is not supported.
            return false;
        }

        String key = vehicleType + "_" + vehicleName.toLowerCase();
        if (vehicleUniqueId.contains(key)) {
            // Another vehicle existing with same name
            return false;
        }

        Vehicle v = new Vehicle(vehicleType, vehicleName, cost);
        vehicleUniqueId.add(key);
        vehiclesMap.get(vehicleType).add(v);
        return true;

    }

    public int bookVehicle(String vehicleType, int start, int end) {
        if (start < 0 || end >= 24) {
            return -1;
        }

        if (!vehiclesMap.containsKey(vehicleType.toLowerCase())) {
            return -1;
        }

        ArrayList<Vehicle> availableVehicles = getAvailableVehicles(vehiclesMap.get(vehicleType.toLowerCase()), start, end);
        if (availableVehicles.size() == 0) {
            return -1;
        }
        Vehicle v = selectVehicle(availableVehicles);
        v.changeBookingStatus(start, end);

        int h = getHours(start,end);

        // Dynamic Pricing
        if (vehicleType.toLowerCase().equals("car")){
            int totalVehiclesCount = vehiclesMap.get(vehicleType.toLowerCase()).size();
            int availableVehiclesCount = availableVehicles.size();
            if ( (double) availableVehiclesCount/totalVehiclesCount <= 0.2){
                return (int) Math.round(v.price*1.1*h);
            }
        }

        return v.price*h;
    }

    private int getHours(int start, int end) {
        if (start<end) {
            return end - start;
        } else {
            return end-start+24;
        }
    }

    private Vehicle selectVehicle(ArrayList<Vehicle> availableVehicles) {
        // Override this method with strategy you desire
        // Current Strategy = Minimum price
        int minPrice = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < availableVehicles.size(); i++) {
            if (availableVehicles.get(i).price < minPrice) {
                minPrice = availableVehicles.get(i).price;
                minIndex = i;
            }
        }
        return availableVehicles.get(minIndex);
    }

    private ArrayList<Vehicle> getAvailableVehicles(ArrayList<Vehicle> vehicles, int start, int end) {
        ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).isAvailableForTimeRange(start, end)) {
                availableVehicles.add(vehicles.get(i));
            }
        }
        return availableVehicles;
    }

    public void showVehicles(int start, int end){
        ArrayList<Vehicle> availableVehicles = new ArrayList<Vehicle>();
        for (String k: vehiclesMap.keySet()) {
            availableVehicles.addAll(getAvailableVehicles(vehiclesMap.get(k), start, end));
        }
        Collections.sort(availableVehicles, Comparator.comparingInt(o -> o.price));
        Iterator<Vehicle> iter = availableVehicles.iterator();
        StringBuilder ids = new StringBuilder();
        while (iter.hasNext()){
            ids.append(iter.next().name);
            if (iter.hasNext()) {
                ids.append(",");
            }
        }
        System.out.println(ids);

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("name: " + name + "\n");
        s.append("Types:" + vehiclesMap.keySet() + "\n");
        s.append("Vehicles: " + vehicleUniqueId.toString());
        return s.toString();
    }
}
