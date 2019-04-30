/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levelgenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import levelgenerator.tiles.*;

/**
 *
 * @author jelmer
 */
public class LevelGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int width = sc.nextInt();
        int height = sc.nextInt();
        sc.nextLine();
        String name = sc.nextLine();
        String author = sc.nextLine();
        Level start = new Level(width,height,name,author);
        Random r = new Random();
        start.placeTileAt(r.nextInt(width), r.nextInt(height), new Player());
        double bestScore = 0.0d;
        Node origin = new Node(start);
        for (int round = 0;round<200000;round++){
            ArrayList<Node> history;
            history = new ArrayList<>();
            Node top = origin;
            history.add(origin);
            while (!top.getFinished() && top.isGenerated()){
                top = top.getBestChild();
                history.add(top);
//                System.out.println(top.getLevel());
            }
            while (!top.getFinished()){
                top.generateChildren();
                top = top.getRandomChild();
                history.add(top);
//                System.out.println(top.getLevel());
            }
            top.preprocess();
            double score = top.evaluate();
            for (int i = 0;i<history.size();i++){
                history.get(i).addResult(score);
                history.get(i).removeChildren();
            }
            if (score > bestScore){
                bestScore = score+0.00001d;
                System.out.println(String.format("%d, %.3f:",round,score));
                System.out.println(top.getLevel());
            }
        }
        
    }
    
}
