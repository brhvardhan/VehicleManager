package com.Navi.VehicleManager;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestBranchClass {
    Branch b = new Branch("branch1", "car,bike");

    @Test
    public void testAddingNewVehilceTypes() {
        assertEquals(b.vehiclesMap.size(), 2);
        b.addVehicleTypes("van");
        assertEquals(b.vehiclesMap.size(), 3);
    }

    @Test
    public void testAddingNewVehicle() {
        assertTrue(b.addNewVehicle("car","v1",1));
        assertFalse(b.addNewVehicle("van","v1",1));
        assertFalse(b.addNewVehicle("CAR","v1",1));
        assertFalse(b.addNewVehicle("car","V1",1));
        assertFalse(b.addNewVehicle("CAR","V1",1));

        b.addNewVehicle("car","v2",1);
        b.addVehicleTypes("van");
        b.addNewVehicle("van","v2",1);

        assertEquals(b.vehicleUniqueId.size(), 3);
        assertEquals(b.vehiclesMap.get("car").size(), 2);
    }

    @Test
    public void testBookVehicle(){
        assertEquals(b.bookVehicle("car",-1,24), -1);
        assertEquals(b.bookVehicle("van",1,3), -1);

        b.addNewVehicle("car","v2",2);
        b.addNewVehicle("car","v1",1);
        b.addNewVehicle("car","v3",3);


        assertEquals(b.bookVehicle("car",1,3), 1);
        assertEquals(b.bookVehicle("car",1,3), 2);
        assertEquals(b.bookVehicle("car",1,3), 3);
        assertEquals(b.bookVehicle("car",1,3), -1);

        assertEquals(b.bookVehicle("car",23,1), 1);
    }
}
