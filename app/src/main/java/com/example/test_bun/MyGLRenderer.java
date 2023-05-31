package com.example.test_bun;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LESS;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glEnable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    //private Square mCube;
    private Cube mCube;
    private Pyramid mPyramid;
    private Model mModel;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    private String selected = "triangle";


    float width;
    float height;
    float ratio;
    final float left = -ratio;
    final float right = ratio;
    final float bottom = -1.0f;
    final float top = 1.0f;
    final float near = 1.0f;
    final float far = 1000.0f;
    public Bitmap bm;

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    public void setRatio() {
        this.ratio = (float) width / height;
    }
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    public volatile float mAngle;
    public volatile float[] mScale = new float[] {1f, 1f, 0};
    public volatile float[] mTranslate = new float[] {0f, 0f, 0f};
    public volatile float[] mRotate = new float[] {0f, 0f, -1.0f};

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float[] getTranslate() {
        return mTranslate;
    }

    public void setTranslate(float[] mTranslate) {
        this.mTranslate = mTranslate;
    }
    public void setRotate(float[] mRotate) {
        this.mRotate = mRotate;
    }

    public float[] getScale(){
        return mScale;
    }
    public float[] getRotate(){
        return mRotate;
    }

    public void setScale(float[] scale) {
        mScale = scale;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        //GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        GLES30.glEnable( GLES30.GL_DEPTH_TEST );
        GLES30.glDepthFunc( GLES30.GL_LEQUAL );
        GLES30.glDepthMask( true );
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glDepthMask(true);
        mTriangle = new Triangle();
       //mCube = new Square();
        mCube = new Cube();
        mPyramid = new Pyramid();
//        glEnable(GL_DEPTH_TEST);
//// Accept fragment if it closer to the camera than the former one
//        glDepthFunc(GL_LESS);

    }

    public void onDrawFrame(GL10 unused) {
        GLES30.glEnable( GLES30.GL_DEPTH_TEST );
        GLES30.glDepthFunc( GLES30.GL_LEQUAL );
        GLES30.glDepthMask( true );
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        if(this.selected == "pyramid") {
            mModel = mPyramid;
        } else if(this.selected == "cube") {
            mModel = mCube;
        } else {
            mModel = mTriangle;
        }
        // Redraw background color
        // Clear the screen



//        mTriangle.draw();
        float[] scratch = new float[16];
        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        Matrix.setRotateM(rotationMatrix, 0, mAngle, mRotate[0], mRotate[1], mRotate[2]);
       // Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.translateM(viewMatrix, 0, mTranslate[0], mTranslate[1], mTranslate[2]);



        Matrix.scaleM(viewMatrix, 0, mScale[0] ,mScale[1], mScale[2]);


        //Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        //Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        // Draw shape
       // mTriangle.draw(scratch);
       // mCube.draw(scratch);
        mModel.draw(scratch);
        //mCube.draw(scratch);
        bm  = createBitmap((int) this.width, (int) this.height);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
        setRatio();
        //float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }
    public static Bitmap createBitmap(int width, int height){

        int screenshotSize = width * height;
        ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
        bb.order(ByteOrder.nativeOrder());
        GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, bb);
        int pixelsBuffer[] = new int[screenshotSize];
        bb.asIntBuffer().get(pixelsBuffer);
        bb = null;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixelsBuffer, screenshotSize-width, -width,0, 0, width, height);
        pixelsBuffer = null;

        short sBuffer[] = new short[screenshotSize];
        ShortBuffer sb = ShortBuffer.wrap(sBuffer);
        bitmap.copyPixelsToBuffer(sb);

        //Making created bitmap (from OpenGL points) compatible with Android bitmap
        for (int i = 0; i < screenshotSize; ++i) {
            short v = sBuffer[i];
            sBuffer[i] = (short) (((v&0x1f) << 11) | (v&0x7e0) | ((v&0xf800) >> 11));
        }
        sb.rewind();
        bitmap.copyPixelsFromBuffer(sb);
//            if(savepicture!=null){
//                savepicture.onPictureSaved(bitmap);
//                // new SaveTask(bitmap, FileUtils.getFileDir().getPath(),"IMG"+System.currentTimeMillis()+".png", savepicture).execute();
//                screenshot = false;
//            }

        return bitmap;
    }

//    public void scaleModel() {
//        GLES30.glSc
//    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }
}