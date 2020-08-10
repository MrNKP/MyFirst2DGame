package com.example.myfirst2dgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        while (running) {
            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }
            catch (Exception e) {

            }
            finally {
                if (canvas != null)
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
            }
            long nowTime = System.nanoTime();
            long waitTime = (nowTime - startTime) / 1000000;
            if (waitTime < 50)
                waitTime = 50;
            System.out.println("Wait Time = " + waitTime);
            try {
                this.sleep(waitTime);
            }
            catch (InterruptedException e) {

            }
            startTime = System.nanoTime();
            //System.out.println(".");
        }
    }

    public void setRunning(boolean newRunning) {
        this.running = newRunning;
    }
}
