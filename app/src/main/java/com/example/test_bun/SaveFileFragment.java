package com.example.test_bun;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class SaveFileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_savefile, container, false);
        ImageView iv = root.findViewById(R.id.imgPreview);
        Button btnSaveConfirm = root.findViewById(R.id.btnSaveImage);
        Bitmap bitmapimage = getActivity().getIntent().getExtras().getParcelable("BitmapImage");
        iv.setImageBitmap(bitmapimage);
        btnSaveConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new EngineActivity().saveImage(bitmapimage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return root;
    }

}
