
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
public class OptionView extends Pane implements SlideAnimationThreadListener, OptionViewListener, BoardListener,
        SolutionThreadListener {

    private Button newGameButton = new Button( "New Game" );
    private Button quitButton = new Button( "Quit" );
    private Button resetButton = new Button( "Reset" );
    private Button solveButton = new Button( "Solve" );
    private OptionViewController controller;
    private Pane parent;

    public OptionView( Pane parent ) {
        this.parent = parent;
        this.getChildren().addAll( newGameButton, quitButton, resetButton, solveButton );
        this.setStyle( "fx-background-color: rgb( 0, 0, 50 );" );
        positionButtons();
        attachController( this );
        disableGameControls( true );
        disablePlayerControls( false );
        register( this );
        newGameButton.setOnAction( event -> controller.newGameButtonPressed() );
        quitButton.setOnAction( event -> controller.quitButtonPressed() );
        resetButton.setOnAction( event -> controller.resetButtonPressed() );
        solveButton.setOnAction( event -> controller.solveButtonPressed() );
    }

    private void positionButtons() {
        newGameButton.relocate( 50, 0 );
        solveButton.relocate( 205, 0 );
        resetButton.relocate( 270, 0 );
        quitButton.relocate( 150, 0 );
    }

    private void attachController( OptionView optionView ) {
        controller = new OptionViewController( optionView );
    }

    public void disableGameControls( boolean disable ) {
        controller.disableGameControls( disable );
    }

    public void disablePlayerControls( boolean disable ) {
        controller.disablePlayerControls( disable );
    }

    public void register( OptionViewListener listener ) {
        controller.register( listener );
    }


    @Override
    public void animationStarted() {
        disablePlayerControls( true );
    }

    @Override
    public void animationFinished() {
        disablePlayerControls( false );
    }


    @Override
    public void newGame() {}

    @Override
    public void quit() {}

    @Override
    public void solve() {
        System.out.println( "Disabling controls on solve..." );
    }

    @Override
    public void reset() {}

    @Override
    public void newGameSetUp() {
        controller.newGameSetup();
    }

    @Override
    public void moveMade() {}  // Do nothing...

    @Override
    public void solutionStarted() {}  // Do nothing...

    @Override
    public void resetStarted() {}

    @Override
    public void doneSolving() {
        disableGameControls( false );
        disablePlayerControls( true );
    }

    @Override
    public void doneResetting() {
        disablePlayerControls( false );
        disableGameControls( true );
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getSolveButton() {
        return solveButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void getInPosition() {
        relocate( 15, 350 );
    }
}
