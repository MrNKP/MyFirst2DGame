package com.example.myfirst2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion extends GameObject {
    private int usingRow = 0;
    private int usingColumn = -1;
    private static final int allRows = 5;
    private static final int allColumns = 5;

    private boolean finish = false;
    private GameSurface gameSurface;

    public Explosion(GameSurface newGameSurface, Bitmap image, int x, int y) {
        super(image, allRows,allColumns, x, y);
        this.gameSurface = newGameSurface;
    }

    public void update() {
        this.usingColumn++;

        if (this.usingColumn >= allColumns) {
            this.usingColumn = 0;
            this.usingRow++;
            if (this.usingRow >= allRows)
                this.finish = true;
        }
    }

    public void draw(Canvas canvas) {
        if (!finish) {
            Bitmap bitmap = this.createSubImg(usingRow, usingColumn);
            canvas.drawBitmap(bitmap, this.x, this.y, null);
        }
    }

    public boolean isFinish() {
        return finish;
    }
}
