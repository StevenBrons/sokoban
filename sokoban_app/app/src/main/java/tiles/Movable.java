package tiles;

/**
 * @author Jelmer Firet
 * an interface that describes handlers for moving of the tile
 */
public interface Movable extends Tile {
    /**
     * @author Jelmer Firet
     * @return a tile that describes what is left behind if something is shifted off this tile
     */
    Tile moveLeftOver();

    /**
     * @author Jelmer Firet
     * @param other the tile this tile will be moved onto
     * @return the adapted goal tile of the movement
     */
    Tile moveOnto(Tile other);
}
