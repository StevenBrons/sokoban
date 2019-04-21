package tiles;

public interface Movable extends Tile {
    Tile MoveLeftOver();
    Tile moveOnto(Tile other);
}
