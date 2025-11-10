package org.example;

import java.util.ArrayList;

public class Positions {
     private ArrayList<City> positions;
    public Positions() {
        positions = new ArrayList<>();
        positions.add(new City("Algiers",36.7538,3.0588,275.165320849382,4086.8360949087823));
        positions.add(new City("Oran",35.6971,-0.6417,-57.726424215067475,3969.336415923478));
        positions.add(new City("Constantine",36.365,6.6147,595.0490544731289,4043.6035074293786));
        positions.add(new City("Annaba",36.902,7.7639,698.4294607501363,4103.315183037506));
        positions.add(new City("Blida",36.4736,2.8298,254.5648048056692,4055.6792764629777));
        positions.add(new City("Batna",	35.5559,6.1744,555.4402893462875,3953.6356922812665));
        positions.add(new City("Sétif",	36.1911,5.4137,487.00879347531685,4024.2667096858895));
        positions.add(new City("Béjaïa",36.75,5.0736,456.4138785999164,4086.413554187533));
        positions.add(new City("Tlemcen",34.8936,-1.3169,-118.46646103914969,3879.9912923645743));
        positions.add(new City("Sidi Bel Abbès",35.2,-0.6333,-56.97077209818019,3914.061417888468));
        positions.add(new City("Jijel",	36.82,5.7666,518.7551782431169,4094.1971990526526));
        positions.add(new City("Skikda",36.8769,6.9,620.7142388716933,4100.524190378728));
        positions.add(new City("Tizi Ouzou",36.7167,4.0497,364.30528306647767,4082.71076313027));
        positions.add(new City("Biskra",34.8519,5.7331,515.7415656341022,3875.3544639234965));
        positions.add(new City("Mostaganem",35.9372,0.0906,8.150247832141364,3996.034317810836));
        positions.add(new City("Chlef",	36.1654,1.3345,120.04973214119921,4021.4090000711244));
        positions.add(new City("Bordj Bou Arréridj",36.0756,4.7578,428.0049573483684,4011.423695658443));
        positions.add(new City("Mascara",35.3964,0.1403,12.621189523724432,3935.900101481459));
        positions.add(new City("El Oued",33.3698,6.8677,617.808576565091,3710.5524631435956));
        positions.add(new City("Médéa",36.2644,2.7535,247.7009647439431,4032.41729780893));

    }
    ArrayList<City> getPositions() {
        return positions;
    }
}
class Parkour {
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
class Evaluation{
    double sum = 0;
    Parkour p;
    Evaluation(Parkour p) {
        this.p = p;

    }
    static double Distance(City c1, City c2){
        double dx = c1.x_km() - c2.x_km();
        double dy = c1.y_km() - c2.y_km();
        return Math.sqrt(dx * dx + dy * dy);
    }
    static double  TotalDistance(Parkour p){
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
    void Increment(double d){
        sum += d;
    }

}
