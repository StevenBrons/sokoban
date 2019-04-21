package tiles;

public interface Connectable extends Tile {
    void connectLeft(Tile other);
    void connectTop(Tile other);
    void connectRight(Tile other);
    void connectBottom(Tile other);
}
