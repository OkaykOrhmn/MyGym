package com.example.mygym.database.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygym.OnItemClickAdapter;
import com.example.mygym.R;
import com.example.mygym.database.models.Days;
import com.example.mygym.databinding.DaysRowBinding;
import com.example.mygym.databinding.DaysRowBinding;

import java.util.ArrayList;
import java.util.Random;

public class Days2Adapter extends RecyclerView.Adapter<Days2Adapter.ViewHolder > {

    private static final String TAG = "Days2Adapter----> ";
    private Context context;
    private ArrayList<Days> daysArrayList;
    private int deletePosition = -1;
    private OnItemClickAdapter onItemClickAdapter;

    public Days2Adapter(ArrayList<Days> daysArrayList, Context context, OnItemClickAdapter onItemClickAdapter) {
        this.daysArrayList = daysArrayList;
        this.context = context;
        this.onItemClickAdapter = onItemClickAdapter;
    }

    @NonNull
    @Override
    public Days2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        DaysRowBinding v = DaysRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Days2Adapter.ViewHolder(v);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull Days2Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Days item = daysArrayList.get(position);
        Random rnd = new Random();
        Log.d(TAG, "getDay: "+item.getDay());
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.binding.mainButton.setText("روز "+item.getDay());
        holder.binding.mainButton.getBackground().setTint(color);
        holder.binding.mainButton.setOnClickListener(view -> onItemClickAdapter.onItemClick(position));





    }

    @Override
    public int getItemCount() {
        return daysArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public DaysRowBinding binding;

        public ViewHolder(@NonNull DaysRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}