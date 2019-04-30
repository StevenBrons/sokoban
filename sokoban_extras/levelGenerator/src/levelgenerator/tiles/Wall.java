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
public class Wall extends Tile{
    @Override
    public boolean isSolid(){
        return true;
    }
    @Override
    public String toString(){
        return "f";
    }
}
