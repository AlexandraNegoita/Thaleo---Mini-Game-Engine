package com.example.test_bun;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Pyramid extends Model{
    private final int program;
    private int positionHandle;
    private int colorHandle;
    private final int vertexCount = vertices.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private float[][]colors = {
            {1, 0, 0, 1},
            {0, 1, 0, 1},
            {0, 0, 1, 1},
            {1, 0, 0, 1},
            {0, 0, 1, 1},
    };
    static final int COORDS_PER_VERTEX =3;
    static float vertices[]=
            {0,  1,  0,
            -1, -1,  1,
            1, -1,  1,
            0,  1,  0,
            1, -1,  1,
            1, -1, -1,
            0,  1,  0,
            1, -1, -1,
            -1, -1, -1,
            0,  1,  0,
            -1, -1, -1,
            -1, -1,  1
            };
    short[] indices={
            // First triangle
            0, 1, 2,
            // Second triangle
            3, 4, 5,
            // Third triangle
            6, 7, 8,
            // Fourth triangle
            9, 10, 11,
            12,13,14
    };
    int vPMatrixHandle;

    private final String vertexShaderCode =
// the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public Pyramid(){
        super();
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
        // initialize byte buffer for the draw list
        indexBuffer = ByteBuffer.allocateDirect(indices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indices).position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        // create empty OpenGL ES Program
        program = GLES30.glCreateProgram();
        // add the vertex shader to program
        GLES20.glAttachShader(program, vertexShader);
        // add the fragment shader to program
        GLES20.glAttachShader(program, fragmentShader);
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);
    }
    @Override
    public void draw(float[] mvpMatrix) {
        // Adding program to OpenGL ES environment
        GLES30.glUseProgram(program);
        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(positionHandle);
        // Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        colorHandle = GLES30.glGetUniformLocation(program, "vColor");
        vPMatrixHandle= GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        for (int face = 0; face < 5; face++) {
            // Set the color for each of the faces
            GLES30.glUniform4fv(colorHandle, 1, colors[face], 0);
            indexBuffer.position(face * 3);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, 5, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        }
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // Disable vertex array
        GLES30.glDisableVertexAttribArray(positionHandle);
    }

}
