
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class MainPane extends Pane {

    BoardView boardView;
    OptionView optionsView;
    LabelView labelView;
    StatsView statsView;
    public MainPane() {
        /**
         * Pass this pane as a parent to all its children. All the children
         * keep a reference to their parent...
         */
        boardView = new BoardView( this );
        optionsView = new OptionView( this );
        labelView = new LabelView( this );
        statsView = new StatsView();
        optionsView.register( labelView );
        boardView.register( optionsView );
        optionsView.register( boardView );
        optionsView.register( statsView );

        this.getChildren().addAll( boardView, optionsView, labelView, statsView );
        boardView.getInPosition();
        optionsView.getInPosition();
        labelView.getInPosition();
        statsView.getInPosition();

        this.setPrefSize( 400, 400 );
        this.setStyle( "-fx-background-color:rgb( 3, 54, 73 );" );
    }



    public void keyPressed( KeyEvent event ) {
        boardView.keyPressed( event );
    }

    public SolutionThreadListener getOptionsView() {
        return optionsView;
    }

    public SolutionThreadListener getLabelView() {
        return labelView;
    }

    public SlideAnimationThreadListener getStatsView() {
        return statsView;
    }

}
