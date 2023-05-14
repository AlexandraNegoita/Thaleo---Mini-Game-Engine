package com.example.test_bun;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    public float[] getShapeScale() {
        return renderer.getScale();
    }

    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void rotateButton(float value){
//        if(value < 50.0f) {
//            renderer.setAngle(
//                    //renderer.getAngle() -
//                            (50 - value) * 45f
//            );
//        } else if (value > 50.0f) {
//            renderer.setAngle(
//                    //renderer.getAngle() +
//                            (value + 50) * 45f
//            );
//        }

        renderer.setAngle(value);

        // reverse direction of rotation above the mid-line
//        if (y > getHeight() / 2) {
//            dx = dx * -1 ;
//        }
//
//        // reverse direction of rotation to left of the mid-line
//        if (x < getWidth() / 2) {
//            dy = dy * -1 ;
//        }

        requestRender();
    }

    //    public void rotateButton(String direction){
//        if(direction.equals("L")) {
//            renderer.setAngle(
//                    renderer.getAngle() - 2
//            );
//        } else if (direction.equals("R")) {
//            renderer.setAngle(
//                    renderer.getAngle() + 2
//            );
//        }
//
//        // reverse direction of rotation above the mid-line
////        if (y > getHeight() / 2) {
////            dx = dx * -1 ;
////        }
////
////        // reverse direction of rotation to left of the mid-line
////        if (x < getWidth() / 2) {
////            dy = dy * -1 ;
////        }
//
//        requestRender();
//    }

    public void scaleButton(float value, String ax){
        float newScale[] = new float[]{};
        //String direction;
        float fact = 0.01f;

        switch (ax) {
            case "X" : {
                newScale = new float[]{
                        fact * value,
                        renderer.getScale()[1],
                        renderer.getScale()[2]
                };
                break;
            }
            case "Y" : {
                newScale = new float[]{
                        renderer.getScale()[0],
                        fact * value,
                        renderer.getScale()[2]
                };
                break;
            }
            case "Z" : {
                newScale = new float[]{
                        renderer.getScale()[0],
                        renderer.getScale()[1],
                        fact * value
                };
                break;
            }
        }
        renderer.setScale(
            newScale
        );

        // reverse direction of rotation above the mid-line
//        if (y > getHeight() / 2) {
//            dx = dx * -1 ;
//        }
//
//        // reverse direction of rotation to left of the mid-line
//        if (x < getWidth() / 2) {
//            dy = dy * -1 ;
//        }

        requestRender();
    }

    public void positionButton(float value, String ax){
        float newPosition[] = new float[]{};
        //String direction;
        float fact = 0.01f;

        switch (ax) {
            case "X" : {
                newPosition = new float[]{
                        fact * value,
                        renderer.getTranslate()[1],
                        renderer.getTranslate()[2]
                };
                break;
            }
            case "Y" : {
                newPosition = new float[]{
                        renderer.getTranslate()[0],
                        fact * value,
                        renderer.getTranslate()[2]
                };
                break;
            }
            case "Z" : {
                newPosition = new float[]{
                        renderer.getTranslate()[0],
                        renderer.getTranslate()[1],
                        fact * value
                };
                break;
            }
        }
        renderer.setTranslate(
                newPosition
        );

        // reverse direction of rotation above the mid-line
//        if (y > getHeight() / 2) {
//            dx = dx * -1 ;
//        }
//
//        // reverse direction of rotation to left of the mid-line
//        if (x < getWidth() / 2) {
//            dy = dy * -1 ;
//        }

        requestRender();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                renderer.setAngle(
                        renderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }
}