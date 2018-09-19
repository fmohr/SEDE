package C2Data;

import java.io.Serializable;

public class C2Image implements Serializable {
    /* VARIABLES */
    private static final long serialVersionUID = 5;

    // image characteristics
    private short[] mPixPack;
    private int mRows;
    private int mColumns;

    /* CONSTRUCTORS */
    public C2Image(short[] pixPack, int rows, int columns) {
        mPixPack	= pixPack;
        mRows		= rows;
        mColumns	= columns;
    }

    /* METHODS */
    public short[] getPixels() {
        return mPixPack;
    }

    public int getRows() {
        return mRows;
    }

    public int getColumns() {
        return mColumns;
    }

    public int getSize() {
        return mRows*mColumns;
    }

    // resets the Image to free the used memory
    public void resetImage(){
        mRows = -1;
        mColumns = -1;
        mPixPack = new short[0];
    }

}
