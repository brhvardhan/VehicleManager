package com.Navi.VehicleManager;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCityClass {

    City city = new City();
    @Test
    public void testAddingBranch() {
        city.addBranch("test branch 1", "car,bike,auto");
        assertEquals(city.branches.size(), 1);
        assertEquals(city.branches.get("test branch 1").name,"test branch 1");
        assertEquals(city.branches.get("test branch 1").vehicleUniqueId.size(),0);
        assertEquals(city.branches.get("test branch 1").vehiclesMap.size(),3);
    }

    @Test
    public void testAddingExitingBranch() {
        city.addBranch("test branch 1", "car,bike,auto");
        assertEquals(city.addBranch("test branch 1", "car,bike,auto"), false);
    }

    @Test
    public void testGetBranch(){
        city.addBranch("test branch 1", "car,bike,auto");
        assertNotNull(city.getBranch("test branch 1"));
        assertNull(city.getBranch("test branch 2"));
    }

}
