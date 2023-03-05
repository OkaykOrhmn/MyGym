package com.example.mygym.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mygym.databinding.ActivityCreatBinding;
import com.example.mygym.databinding.ActivityCreatSuperBinding;

public class CreatSuperActivity extends AppCompatActivity {

    private ActivityCreatSuperBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreatSuperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}