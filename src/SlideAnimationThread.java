import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Iterator;

public class SlideAnimationThread extends Thread {

    private SlidePuzzleMove move;
    private BoardView board;
    private BoardView.DIRECTION direction;

    private ArrayList<SlideAnimationThreadListener> listeners = new ArrayList<>();;

    public SlideAnimationThread(SlidePuzzleMove move, BoardView board, BoardView.DIRECTION direction,
                                BoardViewController controller, SlideAnimationThreadListener listener ) {
        setDaemon( true );
        this.move = move;
        this.board = board;
        this.direction = direction;
        register( controller );
        register( listener );
    }

    public void register( SlideAnimationThreadListener listener ) {
        listeners.add( listener );
    }

    public void run() {
        System.out.println( "Thread is running..." );
        notifyListenersAnimationHasStarted();
        while ( move.getBlankTile().getXPosition() != move.getMovingTile().getXPosition() ||
                move.getBlankTile().getYPosition() != move.getMovingTile().getYPosition() ) {
            System.out.println( "Entering while loop" );
            if ( direction == BoardView.DIRECTION.UP )
                moveTileUp();
            else if ( direction == BoardView.DIRECTION.DOWN )
                moveTileDown();
            else if ( direction == BoardView.DIRECTION.LEFT )
                moveTileLeft();
            else
                moveTileRight();
            Platform.runLater( () -> board.draw() );
            try {
                Thread.sleep( 5 );
            } catch ( InterruptedException e ) {}
        }
        makeMove( move );
        System.out.println( "Move done..." );
        notifyListenersAnimationHasStopped();
    }

    private void makeMove( SlidePuzzleMove move ) {
        move.getBlankTile().setRow( move.getMovingTileCachedRow() );
        move.getBlankTile().setColumn( move.getMovingTileCachedColumn() );
        move.getBlankTile().updateYPosition();
        move.getBlankTile().updateXPosition();
        move.getMovingTile().setRow( move.getBlankTileCachedRow() );
        move.getMovingTile().setColumn( move.getBlankTileCachedColumn() );
        move.getMovingTile().updateXPosition();
        move.getMovingTile().updateYPosition();
    }

    private void moveTileUp() {
        int yPosition = move.getMovingTile().getYPosition();
        move.getMovingTile().setYPosition( --yPosition );
    }

    private void moveTileDown() {
        int yPosition = move.getMovingTile().getYPosition();
        move.getMovingTile().setYPosition( ++yPosition );
    }

    private void moveTileLeft() {
        int xPosition = move.getMovingTile().getXPosition();
        move.getMovingTile().setXPosition(  --xPosition );
    }

    private void moveTileRight() {
        int xPosition = move.getMovingTile().getXPosition();
        move.getMovingTile().setXPosition( ++xPosition );
    }

    private void notifyListenersAnimationHasStarted() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SlideAnimationThreadListener listener = (SlideAnimationThreadListener) i.next();
            listener.animationStarted();
        }
    }

    private void notifyListenersAnimationHasStopped() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SlideAnimationThreadListener listener = (SlideAnimationThreadListener) i.next();
            listener.animationFinished();
        }
    }
}
