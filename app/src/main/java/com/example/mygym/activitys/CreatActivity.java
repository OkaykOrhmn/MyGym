package com.example.mygym.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygym.DetailsAdapter;
import com.example.mygym.R;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.models.Days;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.ActivityCreatBinding;

import java.util.ArrayList;

public class CreatActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityCreatBinding binding;
    private String day = "A";
    private String type = "standard";
    private int addPos;
    private ArrayList<Days> daysArrayList = new ArrayList<>();
    private ArrayList<String> details = new ArrayList<>();
    private ArrayList<String> sets = new ArrayList<>();
    private DetailsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//

        SqlDatabase sqlDatabase = new SqlDatabase(this);
        daysArrayList = sqlDatabase.getDays();

        ArrayList<String> daysName = new ArrayList<>();
        for (int i = 0; i < daysArrayList.size(); i++) {
            daysName.add(daysArrayList.get(i).getDay());
        }
        daysName.add("اضافه کردن...");

        // on below line we are initializing spinner with ids.
        // on below line we are initializing adapter for our spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysName);

        // on below line we are setting drop down view resource for our adapter.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // on below line we are setting adapter for spinner.
        binding.spinnerDay.setAdapter(adapter);

        // on below line we are adding click listener for our spinner
        binding.spinnerDay.setOnItemSelectedListener(this);

        // on below line we are creating a variable to which we have to set our spinner item selected.
        String days = daysName.get(0);
        String add = "اضافه کردن...";

        // on below line we are getting the position of the item by the item name in our adapter.
        int spinnerPosition = adapter.getPosition(days);
        addPos = adapter.getPosition(add);

        // on below line we are setting selection for our spinner to spinner position.
        binding.spinnerDay.setSelection(spinnerPosition);


        binding.addExt.setOnClickListener(view -> {
            String d = "( ";
            if(binding.desc.getText().length()!=0){
                details.add(binding.desc.getText().toString());

            }
            for (int i = 0 ; i<details.size(); i++){
                d+=details.get(i)+", ";
            }
            d+= " )";
            if(binding.editTitle.getText().length() !=0&& details.size()!=0 ){
                if(binding.radioC.isChecked()){
                    if(binding.countSetEdit.getText().length()!=0 && binding.countMoveEdit.getText().length()!=0){
                        sqlDatabase.InsertWork(day,"REG","0",binding.editTitle.getText().toString(),
                                type,binding.countSetEdit.getText().toString(),
                                binding.countMoveEdit.getText().toString(), d);
                    }
                }
            }

        });

        binding.radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (binding.radioGroup.getCheckedRadioButtonId()) {

                case R.id.radio_c:
                    type = "standard";
                    binding.standardLay.setVisibility(View.VISIBLE);
                    binding.myLay.setVisibility(View.GONE);

                    break;
                case R.id.radio_d:
                    type = "custome";

                    binding.standardLay.setVisibility(View.GONE);
                    binding.myLay.setVisibility(View.VISIBLE);
                    break;

            }
        });

        binding.desc.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (textView.getText().length() != 0) {
                details.add(textView.getText().toString());
                textView.setText("");
                rec();
            }

            return false;
        });

//        binding.editSets.setOnEditorActionListener((textView, i, keyEvent) -> {
//            if (textView.getText().length() != 0) {
//                sets.add(textView.getText().toString());
//                textView.setText("");
//                recS();
//            }
//
//            return false;
//        });

        binding.addToDesc.setOnClickListener(view -> {

            if (binding.desc.getText().length() != 0) {
                details.add(binding.desc.getText().toString());
                binding.desc.setText("");
                rec();
            }
        });

        binding.desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.addToDesc.setVisibility(View.GONE);
                } else {
                    binding.addToDesc.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (addPos == i) {
            Toast.makeText(this, "dialog", Toast.LENGTH_SHORT).show();
        } else {
            day = daysArrayList.get(i).getTitle();
            Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void rec() {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        binding.recDetails.setLayoutManager(verticalLayoutManager);

        adapter = new DetailsAdapter(details, this);
        binding.recDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

//

}