package com.Navi.VehicleManager;

class Vehicle{
    
    String type;
    String name;
    int price;
    boolean[] bookedSlots = new boolean[24];

    public Vehicle(String vehicleType, String vehicleName, int cost) {
        type = vehicleType;
        name = vehicleName;
        price = cost;
    }

    public void printDetails(){
        System.out.println("--------------------------------");
        System.out.printf("Type: %s\n", type);
        System.out.printf("name: %s\n", name);
        System.out.printf("Price: %d\n", type);
        System.out.printf("bookedSlots: %s\n", bookedSlots.toString());
        System.out.println("--------------------------------");
    }

    public String toString(){
        return String.format("(type:%s, name:%s, price:%d)", type, name, price );
    }

    public Boolean isAvailableForTimeRange(int start, int end) {
        if (start < end) {
            return isSlotFree(start, end);
        } else {
            return isSlotFree(start, bookedSlots.length) && isSlotFree( 0, end);
        }
    }

    private Boolean isSlotFree( int start, int end) {
        for (int i = start; i < end; i++) {
            if (bookedSlots[i]) {
                return false;
            }
        }
        return true;
    }

    private void blockSlot(int start, int end) {
        for (int i = start; i < end; i++) {
            bookedSlots[i] = true;
        }
    }

    public void changeBookingStatus(int start, int end) {
        if (start < end) {
            blockSlot(start, end);
        } else {
            blockSlot(start, bookedSlots.length);
            blockSlot(0, end);
        }
    }

}