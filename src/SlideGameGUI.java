
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SlideGameGUI extends Application {

    public static void main( String[] args ) {
        launch( args );
    }

    public void start( Stage stage ) {
        MainPane mainPane = new MainPane();
        // Let the key event trickle down the hierarchy until it reaches the boardView...
        mainPane.setOnKeyPressed( event -> mainPane.keyPressed( event ) );
        Scene scene = new Scene( mainPane );
        stage.setScene( scene );
        stage.show();
    }
}
