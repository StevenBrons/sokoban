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
            }
            Node selectedNode = top;
            selectedNode.generateChildren();
            for (int node = 0;node<selectedNode.getNumChildren();node++){
                top = selectedNode.getChild(node);
                history.add(top);
                while (!top.getFinished()){
                    top.generateChildren();
                    top = top.getRandomChild();
                }
                top.preprocess();
                double score = top.evaluate();
                for (int i = 0;i<history.size();i++){
                    history.get(i).addResult(score);
                }
                if (score > bestScore){
                    bestScore = score+0.0000001d;
                    System.out.println(String.format("%d, %.3f:",round,score));
                    System.out.println(top.getLevel());
                }
                selectedNode.getChild(node).removeChildren();
                history.remove(history.size()-1);
            }
        }
        
    }
    
}
