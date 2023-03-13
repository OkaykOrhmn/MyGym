package com.example.mygym.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mygym.BuildConfig;
import com.example.mygym.OnItemClickAdapter;
import com.example.mygym.Tools;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.adapter.Days2Adapter;
import com.example.mygym.database.adapter.DaysAdapter;
import com.example.mygym.database.models.Days;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.FragmentSettingBinding;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class SettingFragment extends Fragment implements OnItemClickAdapter {

    private FragmentSettingBinding binding;
    private SqlDatabase sqlDatabase;
    private ArrayList<Days> days = new ArrayList<>();

    public static final int STORAGE_PERMISSION_CODE = 100;


    String csv1FileName = SqlDatabase.TABLE_NAME + ".csv";
    String csv2FileName = SqlDatabase.TABLE_NAME_DAYS + ".csv";
    String csv3FileName = SqlDatabase.TABLE_NAME_SUPER + ".csv";

    String textFilename ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        sqlDatabase = new SqlDatabase(getContext());
        days = sqlDatabase.getDays();
        Days days1 = new Days(-1,"کل");
        days.add(days1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        binding.recDay.setLayoutManager(gridLayoutManager);
        Days2Adapter adapter = new Days2Adapter(days, getContext(), this);
        binding.recDay.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.backup.setOnClickListener(view -> {
            if (checkPermission()) {
                exportCSV();

            } else {
                requestPermission();
            }
        });
        binding.restore.setOnClickListener(view -> {
            if (checkPermission()) {
                importCSV();

            } else {
                requestPermission();
            }
        });





        return binding.getRoot();
    }

    private void createTable(int position) {
        switch (days.get(position).getId()){
            case -1:
                exportText();
                break;
            default:
                exportTextDay(days.get(position).getDay());
                break;
        }
    }

    private void exportTextDay(String day) {
        textFilename = "Day"+day+".txt";
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "MyGymApp");
        if (!folder.exists()) {
            folder.mkdir();
        }

        String textFilePath = folder.toString() + "/" + textFilename;
        ArrayList<WorkOuts> workOuts = new ArrayList<>();
        ArrayList<WorkOuts> workOutsResult = new ArrayList<>();
        ArrayList<WorkOuts> supers = new ArrayList<>();

        workOuts = sqlDatabase.getData();
        supers = sqlDatabase.getDataSuper();
        String result = "";

            result+="روز " + day + "\n";
            result+="---" + "\n";
            for(int x = 0; x<workOuts.size(); x++){
                if(workOuts.get(x).getDay().equals(day)){
                    if(workOuts.get(x).getType().equals("REG")){
                        result+= workOuts.get(x).getTitles()+ " "+ workOuts.get(x).getDetails() + "\n";
                        result+= workOuts.get(x).getSetCount()+ " x "+ workOuts.get(x).getMoveCount() + "\n"+ "\n";
                    }else{
                        result+= "سوپر ست{ \n";
                        for(int n = 0; n<supers.size(); n++){
                            if(supers.get(n).getSuperId().equals(workOuts.get(x).getTypeId())){
                                result+= supers.get(n).getTitles()+ " "+ supers.get(n).getDetails() + "\n";
                                result+= supers.get(n).getSetCount()+ " x "+ supers.get(n).getMoveCount() + "\n"+ "\n";
                            }
                        }
                        result+= "{"+ "\n"+ "\n";



                    }

                }
            }
            result+="---" + "\n";


        try {
            FileWriter file1Writer = new FileWriter(textFilePath);
            file1Writer.append(result);
            file1Writer.flush();
            file1Writer.close();
            Toast.makeText(getActivity(), "successfull", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void exportText() {
        textFilename = "AllWeek.txt";
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "MyGymApp");
        if (!folder.exists()) {
            folder.mkdir();
        }

        String textFilePath = folder.toString() + "/" + textFilename;
        ArrayList<WorkOuts> workOuts = new ArrayList<>();
        ArrayList<WorkOuts> workOutsResult = new ArrayList<>();
        ArrayList<Days> days = new ArrayList<>();
        ArrayList<WorkOuts> supers = new ArrayList<>();

        workOuts = sqlDatabase.getData();
        days = sqlDatabase.getDays();
        supers = sqlDatabase.getDataSuper();
        String result = "";

        for(int i = 0; i<days.size();i++){
            result+="روز " + days.get(i).getDay() + "\n";
            result+="---" + "\n";
            for(int x = 0; x<workOuts.size(); x++){
                if(workOuts.get(x).getDay().equals(days.get(i).getDay())){
                    if(workOuts.get(x).getType().equals("REG")){
                        result+= workOuts.get(x).getTitles()+ " "+ workOuts.get(x).getDetails() + "\n";
                        result+= workOuts.get(x).getSetCount()+ " x "+ workOuts.get(x).getMoveCount() + "\n"+ "\n";
                    }else{
                        result+= "سوپر ست{ \n";
                        for(int n = 0; n<supers.size(); n++){
                            if(supers.get(n).getSuperId().equals(workOuts.get(x).getTypeId())){
                                result+= supers.get(n).getTitles()+ " "+ supers.get(n).getDetails() + "\n";
                                result+= supers.get(n).getSetCount()+ " x "+ supers.get(n).getMoveCount() + "\n"+ "\n";
                            }
                        }
                        result+= "{"+ "\n"+ "\n";



                    }

                }
            }
            result+="---" + "\n";

        }
        try {
            FileWriter file1Writer = (FileWriter) new OutputStreamWriter(new FileOutputStream(textFilename), StandardCharsets.UTF_8);
            file1Writer.append(result);
            file1Writer.flush();
            file1Writer.close();
            Toast.makeText(getActivity(), "successfull", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


    private void exportCSV() {


        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "MyGymApp");
        if (!folder.exists()) {
            folder.mkdir();
        }


        String csv1FilePath = folder.toString() + "/" + csv1FileName;
        String csv2FilePath = folder.toString() + "/" + csv2FileName;
        String csv3FilePath = folder.toString() + "/" + csv3FileName;

        ArrayList<WorkOuts> workOuts = new ArrayList<>();
        ArrayList<Days> days = new ArrayList<>();
        ArrayList<WorkOuts> supers = new ArrayList<>();

        workOuts = sqlDatabase.getData();
        days = sqlDatabase.getDays();
        supers = sqlDatabase.getDataSuper();

        try {
            FileWriter file1Writer = new FileWriter(csv1FilePath);
            FileWriter file2Writer = new FileWriter(csv2FilePath);
            FileWriter file3Writer = new FileWriter(csv3FilePath);

            for (int i = 0; i < workOuts.size(); i++) {
                file1Writer.append("" + workOuts.get(i).getId());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getDay());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getType());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getTypeId());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getTitles());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getSetType());
                file1Writer.append(",");

                file1Writer.append("" + workOuts.get(i).getSetCount());
                file1Writer.append(",");

                file1Writer.append("" + Tools.removeArray(workOuts.get(i).getMoveCount()));
                file1Writer.append(",");

                file1Writer.append("" + Tools.removeArray(workOuts.get(i).getDetails()));
                file1Writer.append(",");
                file1Writer.append("\n");


            }
            file1Writer.flush();
            file1Writer.close();

            for (int i = 0; i < days.size(); i++) {
                file2Writer.append("" + days.get(i).getId());
                file2Writer.append(",");

                file2Writer.append("" + days.get(i).getDay());
                file2Writer.append(",");
                file2Writer.append("\n");

            }
            file2Writer.flush();
            file2Writer.close();

            for (int i = 0; i < supers.size(); i++) {
                file3Writer.append("" + supers.get(i).getId());
                file3Writer.append(",");

                file3Writer.append("" + supers.get(i).getSuperId());
                file3Writer.append(",");

                file3Writer.append("" + supers.get(i).getDay());
                file3Writer.append(",");

                file3Writer.append("" + supers.get(i).getTitles());
                file3Writer.append(",");

                file3Writer.append("" + supers.get(i).getSetType());
                file3Writer.append(",");

                file3Writer.append("" + supers.get(i).getSetCount());
                file3Writer.append(",");

                file3Writer.append("" + Tools.removeArray(supers.get(i).getMoveCount()));
                file3Writer.append(",");

                file3Writer.append("" + Tools.removeArray(supers.get(i).getDetails()));
                file3Writer.append(",");
                file3Writer.append("\n");


            }
            file3Writer.flush();
            file3Writer.close();
            Toast.makeText(getActivity(), "successFull", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void importCSV() {
        File csv1File = new File(Environment.getExternalStorageDirectory() + "/MyGymApp/" + csv1FileName);
        File csv2File = new File(Environment.getExternalStorageDirectory() + "/MyGymApp/" + csv2FileName);
        File csv3File = new File(Environment.getExternalStorageDirectory() + "/MyGymApp/" + csv3FileName);

        try {
            CSVReader csv1Reader = new CSVReader(new FileReader(csv1File));
            CSVReader csv2Reader = new CSVReader(new FileReader(csv2File));
            CSVReader csv3Reader = new CSVReader(new FileReader(csv3File));

            String[] nextLine1;
            String[] nextLine2;
            String[] nextLine3;
            while ((nextLine1 = csv1Reader.readNext()) != null) {
                int id = Integer.parseInt(nextLine1[0]);
                String day = nextLine1[1];
                String type = nextLine1[2];
                String typeId = nextLine1[3];
                String titles = nextLine1[4];
                String setType = nextLine1[5];
                String setCount = nextLine1[6];
                String moveCount = nextLine1[7];
                String details = nextLine1[8];
                if (!details.equals("")) {
                    details = Tools.toArray(details);
                }

                sqlDatabase.InsertWork(day, type, typeId, titles, setType, setCount, Tools.toArray(moveCount), details);
            }

            while ((nextLine2 = csv2Reader.readNext()) != null) {
                int id = Integer.parseInt(nextLine2[0]);
                String day = nextLine2[1];

                sqlDatabase.InsertDayWhId(id, day);
            }

            while ((nextLine3 = csv3Reader.readNext()) != null) {
                int id = Integer.parseInt(nextLine3[0]);
                String superId = nextLine3[1];
                String day = nextLine3[2];
                String title = nextLine3[3];
                String setType = nextLine3[4];
                String setCount = nextLine3[5];
                String moveCount = nextLine3[6];
                String details = nextLine3[7];

                if (!details.equals("")) {
                    details = Tools.toArray(details);
                }

                sqlDatabase.InsertWorkSuper(superId, day, title, setType, setCount, Tools.toArray(moveCount), details);

            }
            Toast.makeText(getActivity(), "successFull", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                intentActivityResultLauncher.launch(intent);


            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intentActivityResultLauncher.launch(intent);

            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    private ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        importCSV();

                    }
                } else {

                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (write && read) {
                    importCSV();
                } else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(int Position) {
        if (checkPermission()) {
            createTable(Position);
        } else {
            requestPermission();
        }
    }
}