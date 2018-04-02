package de.upb.sede.services;

import java.io.Serializable;

/**
 * Represents an image.
 *
 */
public class Image implements Serializable {
	/* VARIABLES */
	private static final long serialVersionUID = 5;
	
	// image characteristics
	private short[] mPixPack;
	private int mRows;
	private int mColumns;
	
	/* CONSTRUCTORS */
	public Image(short[] pixPack, int rows, int columns) {
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
