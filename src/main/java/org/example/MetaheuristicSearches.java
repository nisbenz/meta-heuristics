package org.example;

import java.util.ArrayList;
import java.util.Objects;

public  class MetaheuristicSearches {
    Positions pos = new Positions();
    Parkour parkour ;
    ArrayList<City> HasntVisited(ArrayList<City> parkour){
        ArrayList<City> a = new ArrayList<>();
      for (City c : pos.getPositions()){
          boolean result = false;
          for (City c2 : parkour){
              if (Objects.equals(c.name(), c2.name())) {
                  result = true;
                  break;
              }
          }
          if(!result){
              a.add(c);
          }
      }
      return a;
    }

}
