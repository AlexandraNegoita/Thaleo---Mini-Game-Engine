package com.example.test_bun;

import android.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.example.test_bun.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    ActivityMainBinding binding;
    private GLSurfaceView gLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new GalleryFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.gallery) {
                replaceFragment(new GalleryFragment());
            }
            else if(item.getItemId() == R.id.my_profile) {
                replaceFragment(new MyProfileFragment());
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
                replaceFragment(new NewProjectFragment());
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

    public void gotoActivity(View View)
    {
        Intent engine = new Intent(this,EngineActivity.class);
        startActivity(engine);
    }
}