/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levelgenerator.tiles;

/**
 *
 * @author Jelmer Firet
 */
public class BoulderWater extends Tile implements TileWater, TileBoulder{
    private final int goalX,goalY;
    
    public BoulderWater(int goalX,int goalY){
        this.goalX = goalX; this.goalY = goalY;
    }
    
    @Override
    public int getGoalX(){ return goalX; }
    @Override
    public int getGoalY(){ return goalY; }
    
    @Override
    public boolean isSolid(){
        return true;
    }
    
    @Override
    public String toString(){
        return "W";
    }
    
    @Override
    public Tile moveLeftOver(){
        return new Water();
    }
    
    @Override
    public Tile moveOnto(Tile other){
        if (other instanceof Empty){
            return new Boulder(goalX,goalY);
        }
        if (other instanceof Goal){
            return new BoulderGoal(goalX,goalY);
        }
        return new Void();
    }
}
