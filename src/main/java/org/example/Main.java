package org.example;

public class Main {
    public static void main(String[] args) {

        Positions positions = new Positions();

        RandomParkour randomParkour = new RandomParkour();

//        for (int i = 0; i < 10000; i++) {
//            randomParkour.RandomSolution();
//
//            for (City c : randomParkour.parkour.getParkour()) {
//                System.out.println(c.name() + " (" + c.lat() + ", " + c.lng() + ")");
//            }
//
//            double totalDistance = Evaluation.TotalDistance(randomParkour.parkour);
//            System.out.println("\nTotal Distance: " + totalDistance + " km");
//        }
        HillClimbParkour p = new HillClimbParkour();

               p.HillClimbSolution();
               for (City c : p.parkour.getParkour()) {
                   System.out.println(c.name() + " (" + c.lat() + ", " + c.lng() + ")");
               }
               double totalDistance = Evaluation.TotalDistance(p.parkour);
               System.out.println("\nTotal Distance: " + totalDistance + " km");




        

    }
        }

