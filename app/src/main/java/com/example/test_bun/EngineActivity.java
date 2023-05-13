package com.example.test_bun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

public class EngineActivity extends Activity {
    private MyGLSurfaceView gLView;
//    Button btnRotateR;
//    Button btnRotateL;
//    Button btnResizeR;
//    Button btnResizeL;

    Slider sldRotateX;
    EditText valueRotateX;
    Slider sldRotateY;
    EditText valueRotateY;
    Slider sldRotateZ;
    EditText valueRotateZ;

    Slider sldResizeX;
    EditText valueResizeX;
    Slider sldResizeY;
    EditText valueResizeY;
    Slider sldResizeZ;
    EditText valueResizeZ;


    LinearLayout rotateMenu;
    LinearLayout resizeMenu;
    Button btnRotateMenu;
    Button btnResizeMenu;

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


        rotateMenu = findViewById(R.id.rotateMenu);
        resizeMenu = findViewById(R.id.resizeMenu);

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
        // debug btns
//        btnRotateR = findViewById(R.id.buttonRotateR);
//        btnRotateR.setText("RotateR");
//
//        btnRotateR.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                // Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();
//                gLView.rotateButton(60);
//            }
//        });
//
//        btnRotateL = findViewById(R.id.buttonRotateL);
//        btnRotateL.setText("RotateL");
//        btnRotateL.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                // Toast.makeText(getApplicationContext(), "Hello!", Toast.LENGTH_SHORT).show();
//                gLView.rotateButton(40);
//            }
//        });

        btnResizeMenu = findViewById(R.id.btnResize);
        btnResizeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resizeMenu.getVisibility() == View.VISIBLE) {
                    resizeMenu.setVisibility(View.GONE);
                } else {
                    if(rotateMenu.getVisibility() == View.VISIBLE) {
                        rotateMenu.setVisibility(View.GONE);
                    }
                    resizeMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        btnRotateMenu = findViewById(R.id.btnRotate);
        btnRotateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rotateMenu.getVisibility() == View.VISIBLE) {
                    rotateMenu.setVisibility(View.GONE);
                } else {
                    if(resizeMenu.getVisibility() == View.VISIBLE) {
                        resizeMenu.setVisibility(View.GONE);
                    }
                    rotateMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        sldRotateX = findViewById(R.id.sliderRotateX);
        sldRotateX.addOnChangeListener((slider, value, fromUser) -> {
            gLView.rotateButton(value);

        });

        valueRotateX = findViewById(R.id.textRotateX);
        valueRotateX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int value;
                try {
                    value = Integer.parseInt(s.toString());
                    if(value > 180 || value < -180) {
                        Toast.makeText(getApplicationContext(), "Insert a number from -180 to 180", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sldRotateX.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sldRotateY = findViewById(R.id.sliderRotateY);
        sldRotateY.addOnChangeListener((slider, value, fromUser) -> {
            gLView.rotateButton(value);

        });

        valueRotateY = findViewById(R.id.textRotateY);
        valueRotateY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int value;
                try {
                    value = Integer.parseInt(s.toString());
                    if(value > 180 || value < -180) {
                        Toast.makeText(getApplicationContext(), "Insert a number from -180 to 180", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sldRotateY.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sldRotateZ = findViewById(R.id.sliderRotateZ);
        sldRotateZ.addOnChangeListener((slider, value, fromUser) -> {
            gLView.rotateButton(value);

        });

        valueRotateZ = findViewById(R.id.textRotateZ);
        valueRotateZ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int value;
                try {
                    value = Integer.parseInt(s.toString());
                    if(value > 180 || value < -180) {
                        Toast.makeText(getApplicationContext(), "Insert a number from -180 to 180", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sldRotateZ.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sldResizeX = findViewById(R.id.sliderResizeX);
        sldResizeX.setValue(gLView.getShapeScale()[0] * 100);
        sldResizeX.addOnChangeListener((slider, value, fromUser) -> {
            //Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
            gLView.scaleButton(value, "X");
        });

        valueResizeX = findViewById(R.id.textResizeX);
        valueResizeX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int cjount, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float value;
                try {
                    value = Float.parseFloat(s.toString());
                    if(value < 0 || value > 200) {
                        Toast.makeText(getApplicationContext(), "Insert a number from 0 to 200", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //value *= 10;
                        sldResizeX.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sldResizeY = findViewById(R.id.sliderResizeY);
        sldResizeY.setValue(gLView.getShapeScale()[0] * 100);
        sldResizeY.addOnChangeListener((slider, value, fromUser) -> {
            //Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
            gLView.scaleButton(value, "Y");
        });

        valueResizeY = findViewById(R.id.textResizeY);
        valueResizeY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int cjount, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float value;
                try {
                    value = Float.parseFloat(s.toString());
                    if(value < 0 || value > 200) {
                        Toast.makeText(getApplicationContext(), "Insert a number from 0 to 200", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //value *= 10;
                        sldResizeY.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sldResizeZ = findViewById(R.id.sliderResizeZ);
        sldResizeZ.setValue(gLView.getShapeScale()[0] * 100);
        sldResizeZ.addOnChangeListener((slider, value, fromUser) -> {
            //Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
            gLView.scaleButton(value, "Z");
        });

        valueResizeZ = findViewById(R.id.textResizeZ);
        valueResizeZ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float value;
                try {
                    value = Float.parseFloat(s.toString());
                    if(value < 0 || value > 200) {
                        Toast.makeText(getApplicationContext(), "Insert a number from 0 to 200", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //value *= 10;
                        sldResizeZ.setValue(value);
                    }

                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//
//        sldResize = findViewById(R.id.sliderResize);
//        sldResize.setValue(gLView.getShapeScale()[0] * 100);
//        sldResize.addOnChangeListener((slider, value, fromUser) -> {
//            //Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
//            gLView.scaleButton(value);
//        });
//
//        valueResize = findViewById(R.id.textResize);
//        valueResize.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int cjount, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                float value;
//                try {
//                    value = Float.parseFloat(s.toString());
//                    if(value < 0 || value > 200) {
//                        Toast.makeText(getApplicationContext(), "Insert a number from 0 to 200", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        //value *= 10;
//                        sldResize.setValue(value);
//                    }
//
//                } catch (NumberFormatException nfe) {
//                    Toast.makeText(getApplicationContext(), "Not a number", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        // btn debug
//        btnResizeR = findViewById(R.id.buttonResizeR);
//        btnResizeR.setText("ResizeR");
//        btnResizeR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gLView.scaleButton("RX");
//                gLView.scaleButton("RY");
//            }
//        });
//
//        btnResizeL = findViewById(R.id.buttonResizeL);
//        btnResizeL.setText("ResizeL");
//        btnResizeL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gLView.scaleButton("LX");
//                gLView.scaleButton("LY");
//            }
//        });

      //  addContentView(editBox, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void gotoActivity(View View)
    {
        Intent engine = new Intent(this,MainActivity.class);
        startActivity(engine);
    }
}
