
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TileView {

    private final int TILE_SIZE = 50;
    protected Tile tile;
    private int xPosition, yPosition;

    public TileView( Tile tile ) {
        this.tile = tile;
        this.xPosition = tile.getColumn()*getTileSize();
        this.yPosition = tile.getRow()*getTileSize();
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void setXPosition( int newXPosition ) {
        this.xPosition = newXPosition;
    }

    public void setYPosition( int newYPosition ) {
        this.yPosition = newYPosition;
    }

    public void draw( GraphicsContext drawingArea ) {
        drawBackground( drawingArea );
        drawFaceValue( drawingArea );
    }

    private void drawBackground( GraphicsContext drawingArea ) {
        drawingArea.setFill( Color.GREEN );
        drawingArea.fillRect( getXPosition(), getYPosition(), 50, 50 );
        drawingArea.setStroke( Color.BLACK );
        drawingArea.strokeRect( getXPosition(), getYPosition(), 50, 50 );
    }

    private void drawFaceValue( GraphicsContext drawingArea ) {
        drawingArea.setStroke( Color.BLACK );
        drawingArea.strokeText( String.valueOf( tile.getFaceValue() ),
                getXPosition()+20, getYPosition()+30 );
    }

    public int getRow() {
        return tile.getRow();
    }

    public int getColumn() {
        return tile.getColumn();
    }

    public boolean isBlank() {
        return false;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public void setRow( int row ) {
        tile.setRow( row );
    }

    public void setColumn( int column ) {
        tile.setColumn( column );
    }

    public int getFaceValue() {
        return tile.getFaceValue();
    }

    public void updateXPosition() {
        this.xPosition = tile.getColumn()*getTileSize();
    }

    public void updateYPosition() {
        this.yPosition = tile.getRow()*getTileSize();
    }
}
