package com.example.myfirst2dgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    //private ManCharacter man1;

    private final List<ManCharacter> mans = new ArrayList<ManCharacter>();
    private final List<Explosion> exps = new ArrayList<Explosion>();

    public GameSurface(Context context) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
    }

    public void update() {
        for (ManCharacter man:mans)
            man.update();
        for (Explosion explosion:exps)
            explosion.update();

        Iterator<Explosion> iterator = this.exps.iterator();
        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();
            if (explosion.isFinish()) {
                iterator.remove();
                continue;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int posX = (int)event.getX();
            int posY = (int)event.getY();

            Iterator<ManCharacter> iterator = this.mans.iterator();
            while (iterator.hasNext()) {
                ManCharacter man = iterator.next();
                if (man.getX() < posX && posX < man.getX() + man.getWidth() && man.getY() < posY && posY < man.getY() + man.getHeight()) {
                    iterator.remove();
                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.boom);
                    Explosion explosion = new Explosion(this, bitmap, man.getX(), man.getY());
                    this.exps.add(explosion);
                }
            }

            for (ManCharacter man:mans) {
                int moveX = posX - man.getX();
                int moveY = posY - man.getY();
                man.setMoveVector(moveX, moveY);
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (ManCharacter man:mans)
            man.draw(canvas);
        for (Explosion explosion:exps)
            explosion.draw(canvas);
    }

    @Override
    public void surfaceCreated (SurfaceHolder surfaceHolder) {
        Bitmap man1Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.man1);
        ManCharacter man1 = new ManCharacter(this, man1Bitmap, 100, 50);

        Bitmap man2Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.man2);
        ManCharacter man2 = new ManCharacter(this, man2Bitmap, 200, 100);

        this.mans.add(man1);
        this.mans.add(man2);

        this.gameThread = new GameThread(this, surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }
}
