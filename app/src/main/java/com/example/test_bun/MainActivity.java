package com.example.test_bun;

import android.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test_bun.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    ActivityMainBinding binding;
    Button btn;
    private GLSurfaceView gLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new WelcomePageFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.gallery) {
                replaceFragment(new WelcomePageFragment());
            }
            else if(item.getItemId() == R.id.my_profile) {
                gotoActivityProfile();
            }
//            switch (item.getItemId()) {
//                case R.id.home:
//                    replaceFragment(new HomeFragment());
//                    break;
//
//                case R.id.shorts:
//                    replaceFragment(new ShortsFragment());
//                    break;
//
//                case R.id.subscriptions:
//                    replaceFragment(new SubscriptionsFragment());
//                    break;
//
//                case R.id.library:
//                    replaceFragment(new LibraryFragment());
//                    break;
//            }
            return true;

        });

        binding.plusButton.setOnClickListener(item -> {
               gotoActivity();
        });
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivityGallery();
            }
        });
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void gotoActivity()
    {
        Intent engine = new Intent(this,EngineActivity.class);
        startActivity(engine);
    }
    public void gotoActivityLogin(View View)
    {
        Intent login = new Intent(this,LoginActivity.class);
        startActivity(login);
    }
    public void gotoActivityProfile()
    {
        Intent profile = new Intent(MainActivity.this,MyProfileFragment.class);
        startActivity(profile);
    }
    public void gotoActivityGallery()
    {
        Intent gallery = new Intent(MainActivity.this, GalleryActivity.class);
        startActivity(gallery);
    }
}