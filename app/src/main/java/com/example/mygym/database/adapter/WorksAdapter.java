package com.example.mygym.database.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygym.R;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.WorksRowBinding;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.Random;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.ViewHolder> {

    private static final String TAG = "WorksAdapter----> ";
    private Context context;
    private ArrayList<WorkOuts> worksourArrayList;
    private int deletePosition = -1;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

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

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull WorksAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        WorkOuts item = worksourArrayList.get(position);
        SqlDatabase sqlDatabase = new SqlDatabase(context);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        GradientDrawable drawable = (GradientDrawable) holder.binding.cartt.getBackground();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, context.getResources().getDisplayMetrics());
        drawable.setStroke(px, color);
        expansionsCollection.add(holder.binding.expansionLayout);
        holder.binding.textTitle.setTextColor(color);
        holder.binding.cSet.getBackground().setTint(color);
        holder.binding.cSetS.getBackground().setTint(color);

        holder.binding.cMove.getBackground().setTint(color);
        holder.binding.cMoveS.getBackground().setTint(color);
        holder.binding.desc.setTextColor(color);
        holder.binding.descS.setTextColor(color);

        if (item.getType().equals("REG")) {
            holder.binding.superLay.setVisibility(View.GONE);

            holder.binding.textTitle.setText(item.getTitles());

            holder.binding.cSet.setText(item.getSetCount());

            holder.binding.cMove.setText(item.getMoveCount());

            if (item.getDetails().equals("[]") || item.getDetails().equals("")) {
                holder.binding.desc.setVisibility(View.GONE);

            } else {
                holder.binding.desc.setVisibility(View.VISIBLE);
                holder.binding.desc.setText(item.getDetails());
            }

        } else {
            holder.binding.superLay.setVisibility(View.VISIBLE);

            ArrayList<WorkOuts> itemSuper = sqlDatabase.getDataSuper();
            ArrayList<WorkOuts> supers = new ArrayList<>();

            for (int i = 0; i < itemSuper.size(); i++) {
                if (itemSuper.get(i).getSuperId().equals(item.getTypeId())) {
                    supers.add(itemSuper.get(i));
                }
            }
            WorkOuts moveOne = supers.get(0);
            WorkOuts moveTwo = supers.get(1);

            holder.binding.textTitle.setText(moveOne.getTitles() + " + " + moveTwo.getTitles());
            Log.e(TAG, "onBindViewHolder: " + moveOne.getTitles());

            holder.binding.cSet.setText(moveOne.getSetCount());
            holder.binding.cSetS.setText(moveTwo.getSetCount());

            holder.binding.cMove.setText(moveOne.getMoveCount());
            holder.binding.cMoveS.setText(moveTwo.getMoveCount());

            if (moveOne.getDetails().equals("[]")) {
                holder.binding.desc.setVisibility(View.GONE);

            } else {
                holder.binding.desc.setVisibility(View.VISIBLE);
                holder.binding.desc.setText(moveOne.getDetails());
            }

            if (moveTwo.getDetails().equals("[]")) {
                holder.binding.descS.setVisibility(View.GONE);

            } else {
                holder.binding.descS.setVisibility(View.VISIBLE);
                holder.binding.descS.setText(moveTwo.getDetails());
            }


        }


        holder.binding.headerIndicator.getDrawable().setTint(color);


        holder.binding.cSet.setOnLongClickListener(view -> {
            if (item.getType().equals("SUPER")) {
                sqlDatabase.deleteSuperById(item.getSuperId());
            }
            sqlDatabase.deleteById(worksourArrayList.get(position).getId());
            worksourArrayList.remove(position);
            notifyDataSetChanged();
            return false;
        });


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