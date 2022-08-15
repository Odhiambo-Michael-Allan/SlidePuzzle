import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class SolutionThread extends Thread {

    private Stack<SlidePuzzleMove> initialMoves;
    private ArrayList<SolutionThreadListener> listeners = new ArrayList<>();
    private BoardView board;
    private String job;
    public SolutionThread( Stack<SlidePuzzleMove> initialMoves, BoardView board, SolutionThreadListener controller,
                           SolutionThreadListener optionsView, SolutionThreadListener labelView, String job  ) {
        setDaemon( true );
        this.board = board;
        listeners.add( controller );
        listeners.add( optionsView );
        listeners.add( labelView );
        this.initialMoves = initialMoves;
        this.job = job;
    }

    public void register( SolutionThreadListener listener ) {
        listeners.add( listener );
    }

    public void run() {
        System.out.println( "Solution thread started..." );
        System.out.println( "Size of stack: " + initialMoves.size() );
        if ( job.equalsIgnoreCase( "Solve" ) )
            notifyListenersSolutionHasStarted();
        else
            notifyListenersResetStarted();
        while ( !initialMoves.isEmpty() ) {
            SlidePuzzleMove move = initialMoves.pop();
            TileView blankTile = move.getBlankTile();
            TileView movingTile = move.getMovingTile();
            BoardView.DIRECTION direction = move.getDirection();
            while ( blankTile.getXPosition() != movingTile.getXPosition() ||
                    blankTile.getYPosition() != movingTile.getYPosition() ) {
                if ( direction == BoardView.DIRECTION.UP ) {
                    moveTileDown( movingTile );
                }
                else if ( direction == BoardView.DIRECTION.DOWN ) {
                    moveTileUp( movingTile );
                }
                else if ( direction == BoardView.DIRECTION.RIGHT ) {
                    moveTileLeft( movingTile );
                } else {
                    moveTileRight( movingTile );
                }
                Platform.runLater( () -> board.draw() );
                try {
                    Thread.sleep( 5 );
                } catch ( InterruptedException e ) {}
            }
            makeMove( move );
        }
        if ( job.equalsIgnoreCase( "solve" ) )
            notifyListenersSolutionDone();
        else
            notifyListenersResetDone();
    }

    private void makeMove( SlidePuzzleMove move ) {
        int blankTileRow = move.getBlankTile().getRow();
        int blankTileColumn = move.getBlankTile().getColumn();
        move.getBlankTile().setRow( move.getMovingTile().getRow() );
        move.getBlankTile().setColumn( move.getMovingTile().getColumn() );
        move.getBlankTile().updateYPosition();
        move.getBlankTile().updateXPosition();
        move.getMovingTile().setRow( blankTileRow );
        move.getMovingTile().setColumn( blankTileColumn );
        move.getMovingTile().updateXPosition();
        move.getMovingTile().updateYPosition();
    }

    private void notifyListenersSolutionHasStarted() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SolutionThreadListener listener = ( SolutionThreadListener ) i.next();
            listener.solutionStarted();
        }
    }

    private void notifyListenersSolutionDone() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SolutionThreadListener listener = ( SolutionThreadListener ) i.next();
            listener.doneSolving();
        }
    }

    private void notifyListenersResetDone() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SolutionThreadListener listener = ( SolutionThreadListener ) i.next();
            listener.doneResetting();
        }
    }

    private void moveTileUp( TileView movingTile ) {
        int yPosition = movingTile.getYPosition();
        movingTile.setYPosition( --yPosition );
    }

    private void moveTileDown( TileView movingTile ) {
        int yPosition = movingTile.getYPosition();
        movingTile.setYPosition( ++yPosition );
    }

    private void moveTileLeft( TileView movingTile ) {
        int xPosition = movingTile.getXPosition();
        movingTile.setXPosition(  --xPosition );
    }

    private void moveTileRight( TileView movingTile ) {
        int xPosition = movingTile.getXPosition();
        movingTile.setXPosition( ++xPosition );
    }

    private void notifyListenersResetStarted() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            SolutionThreadListener listener = ( SolutionThreadListener ) i.next();
            listener.resetStarted();
        }
    }
}
