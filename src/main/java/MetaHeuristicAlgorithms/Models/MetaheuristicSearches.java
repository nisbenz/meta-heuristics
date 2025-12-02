package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

public  class MetaheuristicSearches {
    Positions pos = new Positions();
    public Parkour parkour ;
    MetaheuristicSearches(){
        parkour = new Parkour();
        parkour.setParkour(pos.getPositions());
        City c = parkour.getParkour().getFirst();
        parkour.getParkour().removeFirst();
        Collections.shuffle(parkour.getParkour());
        parkour.getParkour().addFirst(c);
        City clone = new City(c.name(),c.lat(),c.lng(),c.x_km(),c.y_km());
        parkour.getParkour().add(clone);
    }
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
