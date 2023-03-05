package com.example.mygym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ColorStateList csl = ColorStateList.valueOf(color);

        holder.binding.dayButton.setText(item.getDay());
        holder.binding.dayButton.setStrokeColor(csl);

        holder.binding.dayButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Day", item.getTitle());
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