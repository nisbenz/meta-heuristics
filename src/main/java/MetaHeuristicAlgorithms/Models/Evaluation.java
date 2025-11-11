package MetaHeuristicAlgorithms.Models;

public class  Evaluation{
    double sum = 0;
    public static double Distance(City c1, City c2){
        double dx = c1.x_km() - c2.x_km();
        double dy = c1.y_km() - c2.y_km();
        return Math.sqrt(dx * dx + dy * dy);
    }
    public static double  TotalDistance(Parkour p){
        double s = 0;
        for (int i=1 ; i<p.getParkour().size() ; i++) {
             s+= Evaluation.Distance(p.getParkour().get(i-1), p.getParkour().get(i));
        }
        return s;
    }
    static Parkour TheBestParkour(Parkour p1, Parkour p2){
        if(TotalDistance(p1)<TotalDistance(p2)){
            return p1;
        }else{
            return p2;
        }
    }


}
