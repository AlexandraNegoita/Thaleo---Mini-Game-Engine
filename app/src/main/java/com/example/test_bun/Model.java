package com.example.test_bun;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class Model {


    protected Model(){
    }

    public abstract void draw(float[] mvpMatrix);
}
