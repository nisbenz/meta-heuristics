package org.example;

import java.util.ArrayList;

public class Parkour {
    ArrayList<City> travel;
    Parkour() {
        travel = new ArrayList<>();
    }
    ArrayList<City> getParkour() {
        return travel;
    }
    void setParkour(ArrayList<City> cities){
        travel = cities;
    }
}
