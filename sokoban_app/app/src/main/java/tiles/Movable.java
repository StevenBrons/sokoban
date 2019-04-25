package tiles;

/**
 * an interface that describes handlers for moving of the tile
 * @author Jelmer Firet
 */
public interface Movable extends Tile {
    /**
     * @return a tile that describes what is left behind if something is shifted off this tile
     * @author Jelmer Firet
     */
    Tile moveLeftOver();

    /**
     * @param other the tile this tile will be moved onto
     * @return the adapted goal tile of the movement
     * @author Jelmer Firet
     */
    Tile moveOnto(Tile other);
}
