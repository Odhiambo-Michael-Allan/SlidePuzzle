import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BoardViewController implements SlideAnimationThreadListener, SolutionThreadListener {

    private BoardView boardView;
    private SlidePuzzleBoard board;
    private boolean animationInProgress = false;

    public BoardViewController( BoardView boardView, SlidePuzzleBoard board ) {
        this.boardView = boardView;
        this.board = board;
    }

    public void keyPressed( KeyEvent event ) {
        if ( animationInProgress ) {
            System.out.println( "Wait, an animation is in progress..." );
            return;
        }
        KeyCode keyCode = event.getCode();
        if ( keyCode == KeyCode.W || keyCode == KeyCode.UP ) {
            if ( board.tileCanMoveUp() ) {
                SlidePuzzleMove move = new SlidePuzzleMove( board.getBlankTile(),
                        board.getTileAt(board.getBlankTile().getRow()+1,
                        board.getBlankTile().getColumn() ), BoardView.DIRECTION.UP );
                boardView.moveTileUp( move );
            }
        }
        else if ( keyCode == KeyCode.S || keyCode == KeyCode.DOWN ) {
            if ( board.tileCanMoveDown() ) {
                SlidePuzzleMove move = new SlidePuzzleMove( board.getBlankTile(),
                        board.getTileAt(board.getBlankTile().getRow()-1,
                        board.getBlankTile().getColumn() ), BoardView.DIRECTION.DOWN );
                boardView.moveTileDown( move );
            }
        }
        else if ( keyCode == KeyCode.A || keyCode == KeyCode.LEFT ) {
            if ( board.tileCanMoveLeft() ) {
                SlidePuzzleMove move = new SlidePuzzleMove( board.getBlankTile(),
                        board.getTileAt(board.getBlankTile().getRow(),
                        board.getBlankTile().getColumn()+1 ), BoardView.DIRECTION.LEFT );
                boardView.moveTileLeft( move );
            }
        }
        else if ( keyCode == KeyCode.D || keyCode == KeyCode.RIGHT ) {
            if ( board.tileCanMoveRight() ) {
                SlidePuzzleMove move = new SlidePuzzleMove( board.getBlankTile(),
                        board.getTileAt(board.getBlankTile().getRow(),
                        board.getBlankTile().getColumn()-1 ), BoardView.DIRECTION.RIGHT );
                boardView.moveTileRight( move );
            }
        }
    }

    public void animationStarted() {
        animationInProgress = true;
    }

    public void animationFinished() {
        animationInProgress = false;
    }

    @Override
    public void solutionStarted() {
        animationInProgress = true;
    }

    @Override
    public void resetStarted() {
        animationInProgress = true;
    }

    @Override
    public void doneSolving() {
        animationInProgress = false;
    }

    @Override
    public void doneResetting() {
        animationInProgress = false;
    }
}
