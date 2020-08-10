package com.example.myfirst2dgame;

import android.graphics.Bitmap;

public abstract class GameObject {

    protected Bitmap image;

    protected final int rowCount;
    protected final int columnCount;
    protected final int fullWidth;
    protected final int fullHeight;
    protected final int width;
    protected final int height;

    protected int x;
    protected int y;

    public GameObject(Bitmap img, int rowC, int columnC, int x, int y) {
        this.image = img;
        this.rowCount = rowC;
        this.columnCount = columnC;
        this.x = x;
        this.y = y;

        this.fullWidth = img.getWidth();
        this.fullHeight = img.getHeight();
        this.width = this.fullWidth / columnC;
        this.height = this.fullHeight / rowC;
    }

    protected Bitmap createSubImg(int row, int column) {
        return Bitmap.createBitmap(image, column*width, row*height, width, height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
