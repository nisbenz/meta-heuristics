package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;
import java.util.Random;

public class RandomParkour extends MetaheuristicSearches {

    public void RandomSolution(int k) {
        for (int i = 0; i < k; i++) {


            Parkour pk = new Parkour();
            Random random = new Random();
            City c = parkour.getParkour().get(0);
            pk.getParkour().add(c);
            int x = 0;

            while (pk.getParkour().size() < pos.getPositions().size() - 1) {
                ArrayList<City> A = this.HasntVisited(pk.getParkour());
                x = random.nextInt(A.size());
                City Rc1 = A.get(x);
                pk.getParkour().add(Rc1);
            }
            pk.getParkour().add(new City(c.name(), c.lat(), c.lng(), c.x_km(), c.y_km()));
            parkour = Evaluation.TheBestParkour(pk, parkour);

        }
    }
}
