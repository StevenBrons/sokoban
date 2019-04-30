/**
 * class that contains basic implementations for a tile.
 * @author Jelmer Firet
 */
package levelgenerator.tiles;

public class Tile {
    private boolean original = false;
    public boolean isSolid(){
        return false;
    }
    
    public Tile moveLeftOver(){
        return new Void();
    }
    public Tile moveOnto(Tile other){
        return new Void();
    }
    
    public void setOriginal(boolean newVal){
        original = newVal;
    }
    
    public boolean getOriginal(){
        return original;
    }
}
