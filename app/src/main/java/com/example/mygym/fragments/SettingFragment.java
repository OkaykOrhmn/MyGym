package com.example.mygym.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mygym.R;
import com.example.mygym.Tools;
import com.example.mygym.activitys.MainActivity;
import com.example.mygym.database.SqlDatabase;
import com.example.mygym.database.models.Days;
import com.example.mygym.database.models.WorkOuts;
import com.example.mygym.databinding.FragmentSettingBinding;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SqlDatabase sqlDatabase;
    private boolean per = false;

    String csv1FileName = SqlDatabase.TABLE_NAME + ".csv";
    String csv2FileName = SqlDatabase.TABLE_NAME_DAYS + ".csv";
    String csv3FileName = SqlDatabase.TABLE_NAME_SUPER + ".csv";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDatabase = new SqlDatabase(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        binding.backup.setOnClickListener(view -> exportCSV());
        binding.restore.setOnClickListener(view -> importCSV());

        return binding.getRoot();
    }

    private void exportCSV() {

        if(!per){

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
                Toast.makeText(getActivity()      , "successFull", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }else{
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);

        }

    }

    private void importCSV(){
        File csv1File = new File(Environment.getExternalStorageDirectory()+"/MyGymApp/"+csv1FileName );
        File csv2File = new File(Environment.getExternalStorageDirectory()+"/MyGymApp/"+csv2FileName );
        File csv3File = new File(Environment.getExternalStorageDirectory()+"/MyGymApp/"+csv3FileName );

        try {
            CSVReader csv1Reader = new CSVReader(new FileReader(csv1File));
            CSVReader csv2Reader = new CSVReader(new FileReader(csv2File));
            CSVReader csv3Reader = new CSVReader(new FileReader(csv3File));

            String[] nextLine1;
            String[] nextLine2;
            String[] nextLine3;
            while ((nextLine1=csv1Reader.readNext())!=null){
                int id = Integer.parseInt(nextLine1[0]);
                String day = nextLine1[1];
                String type = nextLine1[2];
                String typeId = nextLine1[3];
                String titles = nextLine1[4];
                String setType = nextLine1[5];
                String setCount = nextLine1[6];
                String moveCount = nextLine1[7];
                String details = nextLine1[8];
                if(!details.equals("")) {
                    details = Tools.toArray(details);
                }

                sqlDatabase.InsertWork(day,type,typeId,titles,setType,setCount,Tools.toArray(moveCount),details);
            }

            while ((nextLine2=csv2Reader.readNext())!=null){
                int id = Integer.parseInt(nextLine2[0]);
                String day = nextLine2[1];

                sqlDatabase.InsertDayWhId(id,day);
            }

            while ((nextLine3=csv3Reader.readNext())!=null){
                int id = Integer.parseInt(nextLine3[0]);
                String superId = nextLine3[1];
                String day = nextLine3[2];
                String title = nextLine3[3];
                String setType = nextLine3[4];
                String setCount = nextLine3[5];
                String moveCount = nextLine3[6];
                String details = nextLine3[7];

                if(!details.equals("")) {
                    details = Tools.toArray(details);
                }

                sqlDatabase.InsertWorkSuper(superId,day,title,setType,setCount,Tools.toArray(moveCount),details);

            }
            Toast.makeText(getActivity(), "successFull", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                per = true;
            }
            else {
                Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                per = false;
            }
        }
    }
}