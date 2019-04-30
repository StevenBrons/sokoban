package levelgenerator.tiles;

/**
 *
 * @author Jelmer Firet
 */
public class Void extends Tile{
    @Override
    public boolean isSolid(){
        return true;
    }
    
    @Override
    public String toString(){
        return "#";
    }
}
