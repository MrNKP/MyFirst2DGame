package com.example.myfirst2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ManCharacter extends GameObject {
    private static final int ROW_TO_B = 0;
    private static final int ROW_TO_L = 1;
    private static final int ROW_TO_R = 2;
    private static final int ROW_TO_T = 3;
    private int usingRow = ROW_TO_R;
    private int usingColumn;

    private static final int columnCount = 3;
    private static final int rowCount = 4;

    private Bitmap[] rights;
    private Bitmap[] lefts;
    private Bitmap[] bottoms;
    private Bitmap[] tops;

    public static final float speed = 0.3f;

    private int moveVectorX = 100;
    private int moveVectorY = 50;

    private long lastDrawNanoTime = -1;

    private GameSurface gameSurface;

    public ManCharacter(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, rowCount, columnCount, x, y);

        this.gameSurface = gameSurface;
        this.bottoms = new Bitmap[columnCount];
        this.tops = new Bitmap[columnCount];
        this.lefts = new Bitmap[columnCount];
        this.rights = new Bitmap[columnCount];

        for (int column = 0; column < columnCount; column++) {
            this.bottoms[column] = this.createSubImg(ROW_TO_B, column);
            this.tops[column] = this.createSubImg(ROW_TO_T, column);
            this.lefts[column] = this.createSubImg(ROW_TO_L, column);
            this.rights[column] = this.createSubImg(ROW_TO_R, column);
        }
    }

    public Bitmap[] getMoveBitmaps() {
        switch (usingRow) {
            case ROW_TO_T:
                return this.tops;
            case ROW_TO_B:
                return this.bottoms;
            case ROW_TO_L:
                return this.lefts;
            case ROW_TO_R:
                return this.rights;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap() {
        Bitmap[] allBitmaps = getMoveBitmaps();
        return allBitmaps[usingColumn];
    }

    public void update() {
        usingColumn++;
        if (usingColumn >= columnCount)
            usingColumn = 0;

        long timeNow = System.nanoTime();
        if (lastDrawNanoTime == -1)
            lastDrawNanoTime = timeNow;

        int deltaTime = (int)((timeNow - lastDrawNanoTime) / 1000000);
        float distance = speed * deltaTime;
        double length = Math.sqrt(moveVectorX * moveVectorX + moveVectorY * moveVectorY);
        this.x = x + (int)(distance * moveVectorX / length);
        this.y = y + (int)(distance * moveVectorY / length);

        if (this.x < 0) {
            this.x = 0;
            this.moveVectorX = -this.moveVectorX;
        }
        else if (this.x > this.gameSurface.getWidth() - width) {
            this.x = this.gameSurface.getWidth() - width;
            this.moveVectorX = -this.moveVectorX;
        }

        if (this.y < 0) {
            this.y = 0;
            this.moveVectorY = -this.moveVectorY;
        }
        else if (this.y > this.gameSurface.getHeight() - height) {
            this.y = this.gameSurface.getHeight() - height;
            this.moveVectorY = -this.moveVectorY;
        }

        if (moveVectorX > 0) {
            if (moveVectorY > 0 && Math.abs(moveVectorX) < Math.abs(moveVectorY)) {
                this.usingRow = ROW_TO_B;
            }
            else if (moveVectorY < 0 && Math.abs(moveVectorX) < Math.abs(moveVectorY)) {
                this.usingRow = ROW_TO_T;
            }
            else {
                this.usingRow = ROW_TO_R;
            }
        }
        else {
            if (moveVectorY > 0 && Math.abs(moveVectorX) < Math.abs(moveVectorY)) {
                this.usingRow = ROW_TO_B;
            }
            else if (moveVectorY < 0 && Math.abs(moveVectorX) < Math.abs(moveVectorY)) {
                this.usingRow = ROW_TO_T;
            }
            else {
                this.usingRow = ROW_TO_L;
            }
        }
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        this.lastDrawNanoTime = System.nanoTime();
    }

    public void setMoveVector(int moveX, int moveY) {
        this.moveVectorX = moveX;
        this.moveVectorY = moveY;
    }
}
