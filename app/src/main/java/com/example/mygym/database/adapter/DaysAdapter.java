package com.example.mygym.database.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygym.R;
import com.example.mygym.database.models.Days;
import com.example.mygym.databinding.DaysLayoutBinding;

import java.util.ArrayList;
import java.util.Random;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder > {

    private static final String TAG = "DaysAdapter----> ";
    private Context context;
    private ArrayList<Days> daysArrayList;
    private int deletePosition = -1;

    public DaysAdapter(ArrayList<Days> daysArrayList, Context context) {
        this.daysArrayList = daysArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        DaysLayoutBinding v = DaysLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DaysAdapter.ViewHolder(v);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull DaysAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Days item = daysArrayList.get(position);
        Random rnd = new Random();
        Log.d(TAG, "getDay: "+item.getDay());
        int color = Color.argb(200, rnd.nextInt(150), rnd.nextInt(150), rnd.nextInt(150));
        holder.binding.dayButton.getBackground().setTint(color);
        holder.binding.dayButton.setText("روز "+item.getDay());

        holder.binding.dayButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Day", item.getDay());
            Navigation.findNavController(view).navigate(R.id.action_to_workOutsFragment, bundle);

        });





    }

    @Override
    public int getItemCount() {
        return daysArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public DaysLayoutBinding binding;

        public ViewHolder(@NonNull DaysLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}