package tiles;

public interface Movable extends Tile {
    Tile moveLeftOver();
    Tile moveOnto(Tile other);
}
