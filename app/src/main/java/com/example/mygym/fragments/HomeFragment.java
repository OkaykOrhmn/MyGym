package com.example.mygym.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygym.DaysAdapter;
import com.example.mygym.R;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.models.Days;
import com.example.mygym.databinding.FragmentHomeBinding;
import com.example.mygym.databinding.FragmentSettingBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<Days> days  = new ArrayList<>();
    private DaysAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        SqlDatabase sqlDatabase = new SqlDatabase(getContext());
        sqlDatabase.InsertDay(0,"A","روز A");
        sqlDatabase.InsertDay(1,"B","روز B");
        sqlDatabase.InsertDay(2,"C","روز C");
        sqlDatabase.InsertDay(3,"D","روز D");
        ArrayList<Days> productslist = sqlDatabase.getDays();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        binding.recyclerDays.setLayoutManager(gridLayoutManager);
        adapter = new DaysAdapter(productslist, getContext());
        binding.recyclerDays.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return binding.getRoot();
    }
}