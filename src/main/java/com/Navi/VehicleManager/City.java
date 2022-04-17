package com.navi.vehiclemanager;
import java.util.HashMap;
import java.util.logging.Logger;

public class City {
    HashMap<String,Branch> branches = new HashMap<String,Branch>();
    Logger logger = Logger.getLogger(this.getClass().getName());
    public boolean addBranch(String branchName, String vehicleTypes){
        try {
            branchName = branchName.toLowerCase();
            if (branches.containsKey(branchName)){
                // Already there is an existing branch
                return false;
            }
            Branch b = new Branch(branchName, vehicleTypes);
            branches.put(branchName,b);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public Branch getBranch(String branchName){
        branchName = branchName.toLowerCase();
        if (! branches.containsKey(branchName)){
            return null;
        }
        return branches.get(branchName);
    }

}
