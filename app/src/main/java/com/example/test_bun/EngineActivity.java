package com.example.test_bun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EngineActivity extends Activity {
    private MyGLSurfaceView gLView;
    Button btnRotateR;
    Button btnRotateL;
    Button btnResizeR;
    Button btnResizeL;
    EngineActivity e = this;
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(gLView);
        setContentView(R.layout.activity_engine);
        FrameLayout fl = findViewById(R.id.frame_layout);
        gLView = new MyGLSurfaceView(this);
        fl.addView(gLView,0);
       // gLView.setLayoutParams(new ViewGroup.LayoutParams(347, 642));
        gLView.getLayoutParams().height = (int) (getScreenHeight(this) * 0.80);
       // setContentView(R.layout.activity_engine);
//        editBox = new Button(this);
//        editBox.setText("Go to Main");
//        EngineActivity e = this;
//        editBox.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                Intent engine = new Intent(e,MainActivity.class);
//                startActivity(engine);
//            }
//        });
        btnRotateR = findViewById(R.id.buttonRotateR);
        btnRotateR.setText("RotateR");

        btnRotateR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();
                gLView.rotateButton("R");
            }
        });

        btnRotateL = findViewById(R.id.buttonRotateL);
        btnRotateL.setText("RotateL");
        btnRotateL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();
                gLView.rotateButton("L");
            }
        });

        btnResizeR = findViewById(R.id.buttonResizeR);
        btnResizeR.setText("ResizeR");
        btnResizeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gLView.scaleButton("RX");
                gLView.scaleButton("RY");
            }
        });

        btnResizeL = findViewById(R.id.buttonResizeL);
        btnResizeL.setText("ResizeL");
        btnResizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gLView.scaleButton("LX");
                gLView.scaleButton("LY");
            }
        });

      //  addContentView(editBox, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void gotoActivity(View View)
    {
        Intent engine = new Intent(this,MainActivity.class);
        startActivity(engine);
    }
}
