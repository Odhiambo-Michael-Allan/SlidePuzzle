import java.util.ArrayList;
import java.util.Iterator;

public class OptionViewController {

    private OptionView optionView;
    private ArrayList<OptionViewListener> listeners = new ArrayList<>();

    public OptionViewController( OptionView optionView ) {
        this.optionView = optionView;
    }

    public void newGameButtonPressed() {
        disableAllButtons();
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            OptionViewListener listener = (OptionViewListener) i.next();
            listener.newGame();
        }
    }

    public void quitButtonPressed() {
        disableAllButtons();
        notifyListenersThatGameIsEnding();
        System.exit( 0 );
    }

    public void resetButtonPressed() {
        disableAllButtons();
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            OptionViewListener listener = (OptionViewListener) i.next();
            listener.reset();
        }
    }

    public void solveButtonPressed() {
        disableAllButtons();
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            OptionViewListener listener = (OptionViewListener) i.next();
            listener.solve();
        }
    }

    public void register( OptionViewListener listener ) {
        listeners.add( listener );
    }

    public void newGameSetup() {
        disablePlayerControls( false );
        disableGameControls( true );
    }

    private void disableAllButtons() {
        disableGameControls( true );
        disablePlayerControls( true );
    }

    private void notifyListenersThatGameIsEnding() {
        Iterator i = listeners.iterator();
        while ( i.hasNext() ) {
            OptionViewListener listener = ( OptionViewListener ) i.next();
            listener.quit();
        }
    }

    public void disableGameControls( boolean disable ) {
        optionView.getNewGameButton().setDisable( disable );
        optionView.getQuitButton().setDisable( disable );
    }

    public void disablePlayerControls( boolean disable ) {
        optionView.getSolveButton().setDisable( disable );
        optionView.getResetButton().setDisable( disable );
    }
}
