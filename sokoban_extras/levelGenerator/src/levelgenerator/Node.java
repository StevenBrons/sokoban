/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levelgenerator;

import java.util.ArrayList;
import java.util.Random;
import levelgenerator.tiles.*;
import levelgenerator.tiles.Void;

/**
 *
 * @author jelmer
 */
public class Node {
    private final Level level;
    private final ArrayList<Node> children;
    private final boolean playLevel;
    private final boolean finished;
    private int numVisit = 0;
    private double resultSum = 0.0d;
    private static int lastRandom = 0;
    
    public Node(Level level){
        this.children = new ArrayList<>();
        this.level = level;
        playLevel = false;
        finished = false;
    }
    
    public Node (Level level, boolean playLevel, boolean finished){
        this.children = new ArrayList<>();
        this.level = level;
        this.playLevel = playLevel;
        this.finished = finished;
    }
    
    public boolean getFinished(){
        return this.finished;
    }
    
    public Level getLevel(){
        return this.level;
    }
    
    public double getResultSum(){
        return resultSum;
    }
    
    public int getNumVisit(){
        return numVisit;
    }
    
    public boolean isGenerated(){
        return children.size() > 0;
    }
    
    public void generateChildren(){
        if (children.size() > 0) return;
        if (!playLevel){
            Level levelCpy = new Level(level);
            for (int x = 0;x<levelCpy.getWidth();x++){
                for (int y = 0;y<levelCpy.getHeight();y++)
                 if (!(levelCpy.getTileAt(x,y) instanceof Player)){
                    levelCpy.getTileAt(x,y).setOriginal(true);
                }
            }
            children.add(new Node(levelCpy,true,false));
            int width = level.getWidth();
            int height = level.getHeight();
            for (int y = 0;y<height;y++){
                for (int x = 0;x<width;x++){
                    if (level.getTileAt(x,y) instanceof Void && 
                           (!(level.getTileAt(x-1,y) instanceof Void) ||
                            !(level.getTileAt(x,y-1) instanceof Void) ||
                            !(level.getTileAt(x+1,y) instanceof Void) ||
                            !(level.getTileAt(x,y+1) instanceof Void))
                    ){
                        levelCpy = new Level(level);
                        levelCpy.placeTileAt(x,y,new Empty());
                        children.add(new Node(levelCpy,false,false));
                        levelCpy = new Level(level);
                        levelCpy.placeTileAt(x,y,new BoulderGoal(x,y));
                        children.add(new Node(levelCpy,false,false));
                        levelCpy = new Level(level);
                        levelCpy.placeTileAt(x,y,new Water());
                        children.add(new Node(levelCpy,false,false));
                    }
                }
            }
            return;
        }
        if (!finished){
            Level levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.UP,false);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.UP,true);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.RIGHT,false);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.RIGHT,true);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.DOWN,false);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.DOWN,true);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.LEFT,false);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            levelCpy = new Level(level);
            levelCpy.undoMove(Level.Direction.LEFT,true);
            if (!levelCpy.equals(level)){
                children.add(new Node(levelCpy,true,false));
            }
            children.add(new Node(level,true,true));
        }
    }
    
    public int getNumChildren(){
        return children.size();
    }
    
    public Node getChild(int idx){
        return children.get(idx);
    }
    
    public Node getRandomChild(){
        Random rand = new Random(lastRandom);
        lastRandom = rand.nextInt(1000000000);
        int idx = rand.nextInt(children.size());
        return children.get(idx);
    }
    
    public Node getBestChild(){
        ArrayList<Double> weights = new ArrayList<>();
        double sum = 0.0d;
        for (Node child:children){
            double USB = child.getResultSum()/child.getNumVisit()+Math.sqrt(2)
                    *Math.sqrt(Math.log(numVisit)/child.getNumVisit());
            weights.add(USB);
            sum += USB;
        }
        Random rand = new Random();
        double randInd = rand.nextDouble()*sum;
        for (int i = 0;i<children.size();i++){
            randInd -= weights.get(i);
            if (randInd < 0.0001){
                return children.get(i);
            }
        }
        return null;
    }
    
    public void removeChildren(){
        children.clear();
    }
    
    public void addResult(double result){
        resultSum += result;
        numVisit++;
    }
    
    public void preprocess(){
        for (int x = 0;x<level.getWidth();x++){
            for (int y = 0;y<level.getHeight();y++){        
                if (level.getTileAt(x, y).getOriginal()){
                    level.placeTileAt(x,y,new Void());
                }
            }
        }
        for (int x = 0;x<level.getWidth();x++){
            for (int y = 0;y<level.getHeight();y++){
                if (!(level.getTileAt(x,y) instanceof Void)
                        && !(level.getTileAt(x,y) instanceof Wall)){
                    for (int dx = -1;dx<=1;dx++){
                        for (int dy = -1;dy <= 1;dy++){
                            if (level.getTileAt(x+dx,y+dy) instanceof Void){
                                level.placeTileAt(x+dx,y+dy,new Wall());
                            }
                        }
                    }
                }
            }
        }
    }
    
    public double evaluate(){
        // make level tight
        double terrainScore = 0.0d;
        for (int y = 0;y<level.getHeight();y++){
            for (int x = 0;x<level.getWidth();x++){
                if (level.getTileAt(x,y).isSolid() ^ level.getTileAt(x+1,y).isSolid()){
                    terrainScore+=1.0d;
                }
                if (level.getTileAt(x,y).isSolid() ^ level.getTileAt(x,y+1).isSolid()){
                    terrainScore+=1.0d;
                }
            }
        }
        terrainScore /= (2.0*level.getHeight()*level.getWidth());
        
        // make sure water is connected
        double waterScore = 0.0d;
        int numWater = 0;
        for (int x = 0;x<level.getWidth();x++){
            for (int y = 0;y<level.getHeight();y++){
                if (level.getTileAt(x,y) instanceof Water){
                    numWater++;
                    int numConn = 0;
                    numConn += ((level.getTileAt(x-1,y) instanceof Water)
                     || level.getTileAt(x-1,y) instanceof Void)?1:0;
                    numConn += ((level.getTileAt(x,y-1) instanceof Water)
                     || level.getTileAt(x,y-1) instanceof Void)?1:0;
                    numConn += ((level.getTileAt(x+1,y) instanceof Water)
                     || level.getTileAt(x+1,y) instanceof Void)?1:0;
                    numConn += ((level.getTileAt(x,y+1) instanceof Water)
                     || level.getTileAt(x,y+1) instanceof Void)?1:0;
                    if (numConn >= 2){
                        waterScore += 1.0d;
                    }
                }
            }
        }
        if (numWater != 0){ waterScore /= numWater; }
        else{ waterScore = 0.5; }
        
        double congestionScore = 0.0;
        int totalBoulder = 0;
        for (int boulderX = 0;boulderX<level.getWidth();boulderX++){
            for (int boulderY = 0;boulderY<level.getHeight();boulderY++)
             if (level.getTileAt(boulderX,boulderY) instanceof Boulder){
                totalBoulder++;
                TileBoulder boulderTile = (TileBoulder)level.getTileAt(boulderX,boulderY);
                int goalX = boulderTile.getGoalX();
                int goalY = boulderTile.getGoalY();
                TileGoal goalTile = (TileGoal)level.getTileAt(goalX,goalY);
                int numBoulder = 0;
                for (int x = Math.min(boulderX,goalX);x<=Math.max(boulderX,goalX);x++){
                    for (int y = Math.min(boulderY,goalY);y<=Math.max(boulderY,goalY);y++){
                        if (level.getTileAt(x,y) instanceof TileBoulder){
                            numBoulder++;
                        }
                    }
                }
                congestionScore += numBoulder;
            }
        }
        if (totalBoulder == 0){
            congestionScore = 0.0d;
        }
        else{
            congestionScore /= level.getWidth()*level.getHeight();
        }
        
        return congestionScore*terrainScore;
    }
}
