import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlankTileView extends TileView {


    public BlankTileView( Tile tile ) {
        super( tile );
    }

    public void draw( GraphicsContext drawingArea ) {
        // Draw nothing since this is a blank tile...
    }

    public boolean isBlank() {
        return true;
    }
}
