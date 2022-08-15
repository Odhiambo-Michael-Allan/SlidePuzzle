
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class BoardView extends Pane implements OptionViewListener {

    MainPane parent;

    Canvas canvas = new Canvas( 200, 200 );
    private GraphicsContext drawingArea = canvas.getGraphicsContext2D();
    private SlidePuzzleBoard board = new SlidePuzzleBoard();
    private BoardViewController controller;
    private Stack<SlidePuzzleMove> movesMadeByThePlayer = new Stack<>();

    private ArrayList<BoardListener> listeners = new ArrayList<>();

    enum DIRECTION { UP, DOWN, LEFT, RIGHT }

    public BoardView( MainPane parent ) {
        this.parent = parent;
        this.getChildren().addAll( canvas );
        draw();
        attachController( this, board );
    }

    public void draw() {
        drawOutline();
        drawBackground();
        drawTiles();
    }

    private void drawOutline() {
        drawingArea = canvas.getGraphicsContext2D();
        drawingArea.setStroke( Color.BLACK );
        drawingArea.strokeRect( 0, 0, canvas.getWidth(), canvas.getHeight() );
    }

    private void drawBackground() {
        drawingArea.setFill( Color.rgb( 3, 54, 73 ) );
        drawingArea.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );
    }

    private void drawTiles() {
        Iterator i = board.iterator();
        while ( i.hasNext() ) {
            TileView tileView = ( TileView ) i.next();
            tileView.draw( drawingArea );
        }
    }

    private BoardViewController attachController( BoardView boardView, SlidePuzzleBoard board ) {
        controller = new BoardViewController( boardView, board );
        return controller;
    }

    public void keyPressed( KeyEvent event ) {
        controller.keyPressed( event );
    }

    public void moveTileDown( SlidePuzzleMove move ) {
        movesMadeByThePlayer.push( move );
        board.removeBlankTileFromCanvas();
        new SlideAnimationThread( move, this, DIRECTION.DOWN, controller, parent.getStatsView()  ).start();
        // In this step, make sure to use the cached values since the animation thread might finish
        // and change the row and column values before this line is executed...
        board.returnBlankTileAt( move.getBlankTile(), move.getBlankTile().getRow(), move.getBlankTile().getColumn());
    }

    public void moveTileUp( SlidePuzzleMove move ) {
        movesMadeByThePlayer.push( move );
        board.removeBlankTileFromCanvas();
        new SlideAnimationThread( move, this, DIRECTION.UP, controller, parent.getStatsView()  ).start();
        board.returnBlankTileAt( move.getBlankTile(), move.getBlankTile().getRow(), move.getBlankTile().getColumn() );
    }

    public void moveTileLeft( SlidePuzzleMove move ) {
        movesMadeByThePlayer.push( move );
        board.removeBlankTileFromCanvas();
        new SlideAnimationThread( move, this, DIRECTION.LEFT, controller, parent.getStatsView()  ).start();
        board.returnBlankTileAt( move.getBlankTile(), move.getBlankTile().getRow(), move.getBlankTile().getColumn() );
    }

    public void moveTileRight( SlidePuzzleMove move ) {
        movesMadeByThePlayer.push( move );
        new SlideAnimationThread( move, this, DIRECTION.RIGHT, controller, parent.getStatsView() ).start();
        board.returnBlankTileAt( move.getBlankTile(), move.getBlankTile().getRow(), move.getBlankTile().getColumn() );
    }

    public Stack<SlidePuzzleMove> getInitialMoves() {
        return board.getInitialMoves();
    }

    public Stack<SlidePuzzleMove> getMovesMadeByThePlayer() {
        return movesMadeByThePlayer;
    }

    public void newGame() {
        board.newGame();
        draw();
        notifyListenersNewGameHasBeenSetup();
    }

    private void notifyListenersNewGameHasBeenSetup() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            BoardListener listener = ( BoardListener ) i.next();
            listener.newGameSetUp();
        }
    }

    public void register( BoardListener listener ) {
        listeners.add( listener );
    }

    public void quit() {}  // Do nothing..

    public void reset() {
        System.out.println( "Resetting board..." );
        new SolutionThread( getMovesMadeByThePlayer(), this, controller, parent.getOptionsView(),
                parent.getLabelView(), "Reset" ).start();
    }

    private Stack<SlidePuzzleMove> getCombinedMoves() {
        System.out.println( "Total moves made by the player: " + movesMadeByThePlayer.size() );
        Stack<SlidePuzzleMove> initialMoves = getInitialMoves();
        for ( int i = 0; i < movesMadeByThePlayer.size(); i++ ) {
            initialMoves.push( movesMadeByThePlayer.get( i ) );
        }
        return initialMoves;
    }

    public void solve() {
        new SolutionThread( getCombinedMoves(), this, controller, parent.getOptionsView(),
                parent.getLabelView(), "Solve" ).start();
    }

    public void getInPosition() {
        relocate( 100, 75 );
    }

}
