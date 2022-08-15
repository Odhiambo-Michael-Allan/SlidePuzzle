public class Tile {

    private int rowNumber, columnNumber, faceValue;

    public Tile( int rowNumber, int columnNumber, int faceValue ) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.faceValue = faceValue;
    }

    public int getRow() {
        return this.rowNumber;
    }

    public int getColumn() {
        return this.columnNumber;
    }

    public int getFaceValue() {
        return this.faceValue;
    }

    public void setRow( int row ) {
        this.rowNumber = row;
    }

    public void setColumn( int column ) {
        this.columnNumber = column;
    }

}
