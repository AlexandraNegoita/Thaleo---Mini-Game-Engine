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
    User user = UserDAO.getInstance(this).getUserData();
    ActivityMainBinding binding;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new WelcomePageFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.public_posts) {
                gotoActivityPublicPosts();
            }
            else if(item.getItemId() == R.id.my_profile) {
                gotoActivityProfile();
            }
            return true;

        });

        Button btnAdmin = findViewById(R.id.btnAdmin);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivityAdmin();
            }
        });
        user.setRole();
        if(user.getRole().equals("admin")){
            btnAdmin.setVisibility(View.VISIBLE);
        }

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
    public void gotoActivityPublicPosts()
    {
        Intent publicPosts = new Intent(MainActivity.this, PublicPostsActivity.class);
        startActivity(publicPosts);
    }
    public void gotoActivityAdmin()
    {
        Intent admin = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(admin);
    }
}