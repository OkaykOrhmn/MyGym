package com.example.mygym;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.FragmentHomeBinding;
import com.example.mygym.databinding.FragmentWorkOutsBinding;

import java.util.ArrayList;


public class WorkOutsFragment extends Fragment {

    private FragmentWorkOutsBinding binding;
    private WorksAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkOutsBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        String day = getArguments().getString("Day");

        SqlDatabase sqlDatabase = new SqlDatabase(getContext());
        ArrayList<WorkOuts> workOutsArrayList = new ArrayList<>();
        ArrayList<WorkOuts> workOuts = sqlDatabase.getData();
        for(int i =0; i<workOuts.size(); i++){
            if(workOuts.get(i).getDay().equals(day)){
                workOutsArrayList.add(workOuts.get(i));
            }
        }
        Log.d("KIAAA", "onCreateView: "+workOuts.toString());

        rec(workOutsArrayList);



        return binding.getRoot();
    }

    private void rec( ArrayList<WorkOuts> workOuts) {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        binding.worksRec.setLayoutManager(verticalLayoutManager);

        adapter = new WorksAdapter(workOuts, getContext());
        binding.worksRec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}