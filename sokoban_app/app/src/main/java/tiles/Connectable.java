package tiles;


/**
 * interface for tiles that are able to adapt their texture to neighbouring tiles
 * @author Jelmer Firet
 */
public interface Connectable extends Tile {
    /**
     * @param other the tile on the left to check for connectivity
     */
    void connectLeft(Tile other);

    /**
     * @param other the tile on the top to check for connectivity
     */
    void connectTop(Tile other);

    /**
     * @param other the tile on the right to check for connectivity
     */
    void connectRight(Tile other);

    /**
     * @param other the tile on the bottom to check for connectivity
     */
    void connectBottom(Tile other);
}
