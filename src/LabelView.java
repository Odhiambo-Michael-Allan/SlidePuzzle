import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LabelView extends Pane implements OptionViewListener, SlideAnimationThreadListener, BoardListener,
        SolutionThreadListener {

    Pane parent;
    private Label message;
    public LabelView( Pane parent ) {
        this.parent = parent;
        message = new Label( "Game in progress..." );
        message.setTextFill( Color.WHITE );
        super.getChildren().addAll( message );
    }

    /**
     * All the methods below will be called from other threads so make sure to update the
     * label properly using the animation thread...
     */

    @Override
    public void newGame() {
        Platform.runLater( () -> message.setText( "Game in progress..." ) );
    }

    @Override
    public void quit() {
        Platform.runLater( () -> message.setText( "Quitting game..." ) );
    }

    @Override
    public void solve() {
        Platform.runLater( () -> message.setText( "Solving Board. Please wait..." ) );
    }

    @Override
    public void reset() {
        Platform.runLater( () -> message.setText( "Resetting board..." ) );
    }

    @Override
    public void animationStarted() {
        Platform.runLater( () -> message.setText( "Animation in progress. Please wait..." ) );
    }

    @Override
    public void animationFinished() {
        Platform.runLater( () -> message.setText( "Game in progress..." ) );
    }

    @Override
    public void newGameSetUp() {
        Platform.runLater( () -> message.setText( "Game in progress.." ) );
    }

    @Override
    public void moveMade() {}  // Do nothing...

    @Override
    public void solutionStarted() {}

    @Override
    public void resetStarted() {
    }

    @Override
    public void doneSolving() {
        Platform.runLater( () -> message.setText( "Press \"New Game\" to begin a new game..." ) );
    }

    @Override
    public void doneResetting() {
        Platform.runLater( () -> message.setText( "Game in progress..." ) );
    }

    public void getInPosition() {
        relocate( 10, 10 );
    }
}
