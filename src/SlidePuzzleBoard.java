import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class SlidePuzzleBoard {

    private ArrayList<TileView> tiles;
    private static final int NUMBER_OF_ROWS = 4;
    private static final int NUMBER_OF_COLUMNS = 4;
    private Stack<SlidePuzzleMove> movesMadeDuringRandomization = new Stack<>();


    public SlidePuzzleBoard() {
        newGame();
    }

    public void newGame() {
        createTiles();
        randomizeTiles();
    }

    private void createTiles() {
        this.tiles = new ArrayList<>();
        int counter = 0;
        for ( int row = 0; row < 4; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                if ( counter >= 15 ) {
                    tiles.add( new BlankTileView( new Tile( row, col, counter ) ) );
                    break;
                }
                tiles.add( new TileView( new Tile( row, col, counter+1 ) ) );
                counter++;
            }
        }
    }

    private void randomizeTiles() {
        for ( int i = 0; i < 100; i++ ) {
            TileView blankTile = getBlankTile();
            TileView movingTile = null;
            BoardView.DIRECTION direction = null;
            switch ( new Random().nextInt( 4 ) ) {
                case 0 :
                    if ( blankTile.getRow() - 1 >= 0 ) {
                        movingTile = getTileAt(blankTile.getRow() - 1, blankTile.getColumn());
                        direction = BoardView.DIRECTION.DOWN;  // The moving tile is above the blank tile so it must
                                                               // be moving down...
                    }
                    break;
                case 1 :
                    if ( blankTile.getRow() + 1 < NUMBER_OF_ROWS ) {
                        movingTile = getTileAt(blankTile.getRow() + 1, blankTile.getColumn());
                        direction = BoardView.DIRECTION.UP;  // The moving tile is below the blank tile so it must be
                                                             // moving up..
                    }
                case 2 :
                    if ( blankTile.getColumn() - 1 >= 0 ) {
                        movingTile = getTileAt( blankTile.getRow(), blankTile.getColumn() - 1 );
                        direction = BoardView.DIRECTION.RIGHT;  // The moving tile is to the left of the blank tile
                                                                // so it must be moving right...
                    }
                    break;
                case 3 :
                    if ( blankTile.getColumn() + 1 < NUMBER_OF_COLUMNS ) {
                        movingTile = getTileAt(blankTile.getRow(), blankTile.getColumn() + 1 );
                        direction = BoardView.DIRECTION.LEFT;  // The moving tile is to the right of the blank tile
                                                               // so it must be moving left...
                    }
                    break;
            }
            if ( movingTile == null )
                continue;
            SlidePuzzleMove move = new SlidePuzzleMove( blankTile, movingTile, direction );
            movesMadeDuringRandomization.push( move );
            makeMove( move );
        }
    }

    public void makeMove( SlidePuzzleMove move ) {
        // Let's update the moving tile so that its in the position previously occupied by blank tile...
        move.getMovingTile().setRow( move.getBlankTileCachedRow() );
        move.getMovingTile().setColumn( move.getBlankTileCachedColumn() );
        // Use cached values to update the blank tile since the values for moving tile have changed...
        move.getBlankTile().setRow( move.getMovingTileCachedRow() );
        move.getBlankTile().setColumn( move.getMovingTileCachedColumn() );
        // Update their respective coordinates...
        move.getMovingTile().updateXPosition();
        move.getMovingTile().updateYPosition();
        move.getBlankTile().updateXPosition();
        move.getBlankTile().updateYPosition();
    }

    public TileView getTileAt( int row, int col ) {
        Iterator i = iterator();
        while ( i.hasNext() ) {
            TileView tile = ( TileView ) i.next();
            if ( tile.getRow() == row && tile.getColumn() == col )
                return tile;
        }
        return null;
    }

    public Iterator iterator() {
        return tiles.iterator();
    }

    public TileView getBlankTile() {
        Iterator i = iterator();
        while ( i.hasNext() ) {
            TileView tile = ( TileView ) i.next();
            if ( tile.isBlank() )
                return tile;
        }
        return null;
    }

    public boolean tileCanMoveUp() {
        TileView blankTile = getBlankTile();
        int rowOfTileBelow = blankTile.getRow() + 1;
        if ( rowOfTileBelow < NUMBER_OF_ROWS )
            return true;
        return false;
    }

    public boolean tileCanMoveDown() {
        TileView blankTile = getBlankTile();
        int rowOfTileAbove = blankTile.getRow() - 1;
        if ( rowOfTileAbove >= 0 )
            return true;
        return false;
    }

    public boolean tileCanMoveRight() {
        TileView blankTile = getBlankTile();
        int columnOfTileOnTheLeft = blankTile.getColumn() - 1;
        if ( columnOfTileOnTheLeft >= 0 )
            return true;
        return false;
    }

    public boolean tileCanMoveLeft() {
        TileView blankTile = getBlankTile();
        int columnOfTileOnTheRight = blankTile.getColumn() + 1;
        if ( columnOfTileOnTheRight < NUMBER_OF_COLUMNS )
            return true;
        return false;
    }

    public TileView removeBlankTileFromCanvas() {
        TileView blankTile = getBlankTile();
        tiles.remove( blankTile );
        return blankTile;
    }

    public void returnBlankTileAt( TileView blankTile, int row, int column ) {
        int index = ( row*NUMBER_OF_COLUMNS ) + column;
        tiles.add( index, blankTile );
    }

    public void updateMovingTileRowAndColumn( SlidePuzzleMove move ) {
        move.getMovingTile().setRow( move.getBlankTileCachedRow() );
        move.getMovingTile().setColumn( move.getBlankTileCachedColumn() );
    }

    public Stack<SlidePuzzleMove> getInitialMoves() {
        return movesMadeDuringRandomization;
    }
}
