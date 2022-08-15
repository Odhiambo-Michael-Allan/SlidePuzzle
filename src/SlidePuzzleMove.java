

public class SlidePuzzleMove {

    TileView movingTile;
    TileView blankTile;
    BoardView.DIRECTION direction;

    private int movingTileCachedRow, movingTileCachedColumn, blankTileCachedRow, blankTileCachedColumn;

    public SlidePuzzleMove( TileView blankTile, TileView movingTile, BoardView.DIRECTION direction ) {
        this.blankTile = blankTile;
        this.movingTile = movingTile;
        this.direction = direction;
        this.movingTileCachedRow = movingTile.getRow();
        this.movingTileCachedColumn = movingTile.getColumn();
        this.blankTileCachedRow = blankTile.getRow();
        this.blankTileCachedColumn = blankTile.getColumn();

    }

    public TileView getMovingTile() {
        return this.movingTile;
    }

    public TileView getBlankTile() {
        return this.blankTile;
    }

    public int getMovingTileCachedRow() {
        return this.movingTileCachedRow;
    }

    public int getMovingTileCachedColumn() {
        return this.movingTileCachedColumn;
    }

    public int getBlankTileCachedRow() {
        return this.blankTileCachedRow;
    }

    public int getBlankTileCachedColumn() {
        return this.blankTileCachedColumn;
    }

    public BoardView.DIRECTION getDirection() {
        return this.direction;
    }

}
