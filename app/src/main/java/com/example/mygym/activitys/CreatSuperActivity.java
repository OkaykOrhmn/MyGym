package com.example.mygym.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mygym.R;
import com.example.mygym.Tools;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.adapter.DetailsAdapter;
import com.example.mygym.database.models.Days;
import com.example.mygym.databinding.ActivityCreatBinding;
import com.example.mygym.databinding.ActivityCreatSuperBinding;

import java.util.ArrayList;

public class CreatSuperActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityCreatSuperBinding binding;
    private int move = 1;

    private String day = "A";
    private String type = "standard";
    private int addPos;
    private SqlDatabase sqlDatabase;
    private ArrayList<Days> daysArrayList = new ArrayList<>();
    private ArrayList<String> details = new ArrayList<>();
    private ArrayList<String> sets = new ArrayList<>();
    private ArrayList<String> daysName = new ArrayList<>();
    private ArrayAdapter<String> adapterSpinnder;
    private DetailsAdapter adapter;
    private  String superId;

    private String[] alphba = {"A", "B", "C", "D", "E", "F", "G"};
    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreatSuperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         superId = Tools.getRandomString(4);

        binding.addExt.setText("حرکت اول");


        binding.addExt.setOnClickListener(view -> {
            if (binding.editTitle.getText().length() != 0) {
                if (binding.radioC.isChecked()) {
                    if (binding.countSetEdit.getText().length() != 0 && binding.countMoveEdit.getText().length() != 0) {
                        sqlDatabase.InsertWorkSuper(superId, day, binding.editTitle.getText().toString(),
                                type, binding.countSetEdit.getText().toString(),
                                binding.countMoveEdit.getText().toString(), Tools.removeArray(details.toString()));
                        if (move == 2) {
                            sqlDatabase.InsertWork(day, "SUPER", superId, "", "", "", "", "");
                            finish();
                        }
                    }
                } else {
                    if (!sets.isEmpty()) {
                        Log.d("TAG_KIA", "onCreate: " + sets.toString());
                        sqlDatabase.InsertWorkSuper(superId, day, binding.editTitle.getText().toString(),
                                type, String.valueOf(sets.size()),
                                Tools.removeArray(sets.toString()), Tools.removeArray(details.toString()));
                        if (move == 2) {
                            sqlDatabase.InsertWork(day, "SUPER", superId, "", "", "", "", "");
                            finish();
                        }
                    }
                }
            }
            binding.editTitle.setText("");
            binding.eachSetEdit.setText("");
            binding.countSetEdit.setText("");


            binding.countMoveEdit.setText("");
            if (!details.isEmpty() || !sets.isEmpty()) {
                details.clear();
                sets.clear();
                adapter.notifyDataSetChanged();
            }
            binding.addExt.setText("حرکت دوم");
            move++;


        });


        sqlDatabase = new SqlDatabase(this);
        daysArrayList = sqlDatabase.getDays();
        position = daysArrayList.size();

        daysName = new ArrayList<>();

        binding.spinnerDay.setVisibility(View.VISIBLE);
        spinner();
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
                rec(details, binding.recDetails);
            }

            return false;
        });

        binding.eachSetEdit.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (textView.getText().length() != 0) {
                sets.add(textView.getText().toString());
                textView.setText("");
                rec(sets, binding.recSets);
            }

            return false;
        });


        binding.addToDesc.setOnClickListener(view -> {

            if (binding.desc.getText().length() != 0) {
                details.add(binding.desc.getText().toString());
                binding.desc.setText("");
                rec(details, binding.recDetails);
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

    private void spinner() {
        for (int i = 0; i < daysArrayList.size(); i++) {
            daysName.add("روز "+daysArrayList.get(i).getDay());
        }

        if (daysName.size() < 7) {

            daysName.add("اضافه کردن...");
            Log.e("TAG_KIA", "spinner: " + daysName.size());
        }

        // on below line we are initializing spinner with ids.
        // on below line we are initializing adapter for our spinner
        adapterSpinnder = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysName);

        // on below line we are setting drop down view resource for our adapter.
        adapterSpinnder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // on below line we are setting adapter for spinner.
        binding.spinnerDay.setAdapter(adapterSpinnder);

        // on below line we are adding click listener for our spinner
        binding.spinnerDay.setOnItemSelectedListener(this);

        // on below line we are creating a variable to which we have to set our spinner item selected.
        String days = daysName.get(0);
        String add = "اضافه کردن...";

        // on below line we are getting the position of the item by the item name in our adapter.
        int spinnerPosition = adapterSpinnder.getPosition(days);
        addPos = adapterSpinnder.getPosition(add);

        // on below line we are setting selection for our spinner to spinner position.
        binding.spinnerDay.setSelection(spinnerPosition);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TAG_KIA", "onItemSelected: ");

        if (addPos == i) {

            if (position < 7) {
                sqlDatabase.InsertDay(alphba[position]);
                adapterSpinnder.remove("اضافه کردن...");

                adapterSpinnder.add("روز " + alphba[position]);

                adapterSpinnder.add("اضافه کردن...");
                String add = "اضافه کردن...";
                addPos = adapterSpinnder.getPosition(add);
                position++;
                daysArrayList = sqlDatabase.getDays();
                if (position == alphba.length) {
                    adapterSpinnder.remove("اضافه کردن...");
                    adapterSpinnder.notifyDataSetChanged();

                }
            } else {
                addPos = -1;
                adapterSpinnder.remove("اضافه کردن...");

            }
            adapterSpinnder.notifyDataSetChanged();
            day = daysArrayList.get(i).getDay();

        } else {
            day = daysArrayList.get(i).getDay();
            Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("TAG_KIA", "onNothingSelected: ");

    }

    private void rec(ArrayList<String> strings, RecyclerView recyclerView) {
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerView.setLayoutManager(verticalLayoutManager);

        adapter = new DetailsAdapter(strings, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sqlDatabase.deleteSuperById(superId);
    }
}