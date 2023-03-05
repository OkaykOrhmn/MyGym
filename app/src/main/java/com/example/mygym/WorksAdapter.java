package com.example.mygym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygym.database.models.Days;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.DaysLayoutBinding;
import com.example.mygym.databinding.WorksRowBinding;

import java.util.ArrayList;
import java.util.Random;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.ViewHolder > {

    private static final String TAG = "WorksAdapter----> ";
    private Context context;
    private ArrayList<WorkOuts> worksourArrayList;
    private int deletePosition = -1;

    public WorksAdapter(ArrayList<WorkOuts> worksourArrayList, Context context) {
        this.worksourArrayList = worksourArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        WorksRowBinding v = WorksRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WorksAdapter.ViewHolder(v);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull WorksAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        WorkOuts item = worksourArrayList.get(position);

        holder.binding.a.setOnClickListener(view -> {
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (holder.binding.extendedLay.getVisibility() == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(holder.binding.cartt, new AutoTransition());
                holder.binding.extendedLay.setVisibility(View.GONE);
                holder.binding.imageTvDrawer.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_drop_down_24));
            }

            // If the CardView is not expanded, set its visibility to
            // visible and change the expand more icon to expand less.
            else {
                TransitionManager.beginDelayedTransition(holder.binding.cartt, new AutoTransition());
                holder.binding.extendedLay.setVisibility(View.VISIBLE);
                holder.binding.imageTvDrawer.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_drop_up_24));
            }
        });

        holder.binding.textTitle.setText(item.getTitles());
        holder.binding.cSet.setText(item.getSetCount());
        holder.binding.cMove.setText(item.getMoveCount());
        holder.binding.desc.setText(item.getDetails());



    }

    @Override
    public int getItemCount() {
        return worksourArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public WorksRowBinding binding;

        public ViewHolder(@NonNull WorksRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}