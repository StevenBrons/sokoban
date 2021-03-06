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
public class PlayerWater extends Tile implements TilePlayer,TileWater{
    
    @Override
    public String toString(){
        return "q";
    }
    
    @Override
    public Tile moveLeftOver(){
        return new Water();
    }
    
    @Override
    public Tile moveOnto(Tile other){
        if (other instanceof Empty){
            return new Player();
        }
        if (other instanceof Goal){
            return new PlayerGoal();
        }
        if (other instanceof Water){
            return new PlayerWater();
        }
        return new Void();
    }
}
