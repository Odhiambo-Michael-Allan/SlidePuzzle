import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class StatsView extends Pane implements SlideAnimationThreadListener, OptionViewListener {

    private StatsViewController controller;
    private Label totalMovesMade;
    private Label elapsedTime;
    private int numberOfMovesMadeByThePlayer = 0;
    public StatsView() {
        elapsedTime = new Label( String.format( "Time Elapsed: %d", 0 ) );
        totalMovesMade = new Label( String.format( "Moves made: %d", numberOfMovesMadeByThePlayer ) );
        elapsedTime.setTextFill( Color.WHITE );
        totalMovesMade.setTextFill( Color.WHITE );
        totalMovesMade.relocate( 0, 15 );
        super.getChildren().addAll( elapsedTime, totalMovesMade );
        attachController( new StatsViewController( this ) );
    }

    private void attachController( StatsViewController controller ) {
        this.controller = controller;
    }

    public void getInPosition() {
        relocate( 250, 300 );
    }

    @Override
    public void animationStarted() {}  // Do nothing...

    @Override
    public void animationFinished() {
        // Since the slide animation thread is only used for user moves, we can use
        // it here to count the number of moves made by the player...
        numberOfMovesMadeByThePlayer++;
        Platform.runLater( () -> totalMovesMade.setText( "Moves made: " + numberOfMovesMadeByThePlayer ) );

    }

    public Label getElapsedTime() {
        return elapsedTime;
    }

    public Label getTotalMovesMade() {
        return totalMovesMade;
    }

    @Override
    public void newGame() {
        controller.newGame();
        numberOfMovesMadeByThePlayer = 0;
    }

    @Override
    public void quit() {}

    @Override
    public void solve() {
        controller.solve();
    }

    @Override
    public void reset() {
        numberOfMovesMadeByThePlayer = 0;
        controller.reset();
    }
}
