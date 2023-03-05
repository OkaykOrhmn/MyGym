package com.example.mygym.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mygym.R;
import com.example.mygym.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private     Boolean isAllFabsVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        isAllFabsVisible = false;


        binding.fab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {

                binding.addAlarmFab.show();
                binding.addPersonFab.show();
                binding.addAlarmFabText.setVisibility(View.VISIBLE);
                binding.addPersonFabText.setVisibility(View.VISIBLE);


                isAllFabsVisible = true;
            } else {

                binding.addAlarmFab.hide();
                binding.addPersonFab.hide();
                binding.addAlarmFabText.setVisibility(View.GONE);
                binding.addPersonFabText.setVisibility(View.GONE);


                isAllFabsVisible = false;
            }
        });

        binding.addPersonFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT
                ).show());


        binding.addAlarmFab.setOnClickListener(view -> {
                    Intent intent = new Intent(this, CreatActivity.class);
                    startActivity(intent);

            binding.addAlarmFab.hide();
            binding.addPersonFab.hide();
            binding.addAlarmFabText.setVisibility(View.GONE);
            binding.addPersonFabText.setVisibility(View.GONE);


            isAllFabsVisible = false;
                });
    }
}