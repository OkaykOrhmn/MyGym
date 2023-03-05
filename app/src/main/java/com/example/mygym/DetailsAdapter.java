package com.example.mygym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygym.databinding.DetailLayoutBinding;

import java.lang.String;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder > {

    private static final String TAG = "DetailsAdapter----> ";
    private Context context;
    private ArrayList<String> stringArrayList;
    private int deletePosition = -1;

    public DetailsAdapter(ArrayList<String> stringArrayList, Context context) {
        this.stringArrayList = stringArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        DetailLayoutBinding v = DetailLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailsAdapter.ViewHolder(v);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.mainText.setText(stringArrayList.get(position).toString());


        holder.binding.lay.setOnLongClickListener(view -> {
            stringArrayList.remove(position);
            this.notifyDataSetChanged();
            return false;
        });



    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public DetailLayoutBinding binding;

        public ViewHolder(@NonNull DetailLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}