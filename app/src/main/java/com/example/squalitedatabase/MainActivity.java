package com.example.squalitedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squalitedatabase.data.DatabaseHelper;
import com.example.squalitedatabase.model.PersonalInfo;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText enterText ;
    TextView showText;
    Button writeText,readText,goToList;
    public static final String FILE_NAME="file.txt";
    public final int READ_CODE=100;
    public static final int WRITE_CODE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper helper=new DatabaseHelper(MainActivity.this);
        goToList=findViewById(R.id.goListA);
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListViewDB.class);
                startActivity(intent);

            }
        });
        PersonalInfo personalInfo=new PersonalInfo();
        personalInfo.setPhone_no("888558598");
        personalInfo.setName("Jivan");
        helper.addContact(personalInfo);
        PersonalInfo pi=new PersonalInfo("Samrat","8527848125");
        int x=helper.updateRecord(pi);

        Log.d("TAG","no. of rows affected "+x);
      // int y=helper.deleteRecord();
      //  Log.d("TAG","deleted "+y);
       List<PersonalInfo> personal= helper.showQuery();
       for(PersonalInfo p1:personal){

           Log.d("TAG","name "+p1.getName()+" phone "+p1.getPhone_no()+" "+personal.size()+" id"+ p1.getId());
       }

        enterText=findViewById(R.id.editText);
        showText=findViewById(R.id.textView);
        writeText=findViewById(R.id.write);
        readText=findViewById(R.id.read);
        writeText.setOnClickListener(this);
        readText.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int grantnum= grantResults.length;
        switch(requestCode){

            case READ_CODE:

                if(grantnum>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"permission granted for read storage",Toast.LENGTH_LONG).show();
                    readFileAndShow();
                    break;}

            case WRITE_CODE:
                if(grantnum>0 &&  grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    writeFile();
                }
        }
    }



    public void readStoragePermission(){
        int permissionCode= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCode!= PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE},READ_CODE );
        }else{
            readFileAndShow();
        }

    }
    public void writeStoragePermission(){
        int permissionCode =ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCode!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_CODE);
        }else{
            writeFile();
        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.read:
                readStoragePermission();
                break;
            case R.id.write:
                writeStoragePermission();
                break;
        }
    }

    private void readFileAndShow() {
        try {

            showText.setText(readFile());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void writeFile()  {
        String stringFromEditText=enterText.getText().toString();
        if(stringFromEditText!=null && stringFromEditText.length()>0 && !stringFromEditText.equals("")){
            try {
                writeContent(stringFromEditText);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void writeContent(String stringFromEditText) throws IOException,FileNotFoundException{
       File file=new File(Environment.getExternalStorageDirectory(),getPackageName());
       Log.d("TAG",getPackageName()+"  "+file);
       file.mkdir();

//        FileOutputStream fileOutputStream=openFileOutput(FILE_NAME,MODE_PRIVATE);
//        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream);
//        outputStreamWriter.write(stringFromEditText);
//        outputStreamWriter.flush();
//        outputStreamWriter.close();
        FileWriter fileWriter=new FileWriter(new File(file,FILE_NAME));
        fileWriter.write(stringFromEditText);
        fileWriter.flush();
        fileWriter.close();
    }

    private String readFile() throws IOException,FileNotFoundException{
        String read="";
        File file=new File(Environment.getExternalStorageDirectory(),getPackageName());

//        FileInputStream fileInputStream=openFileInput(FILE_NAME);
//        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(new FileReader(new File(file,FILE_NAME)));
        StringBuilder stringBuilder=new StringBuilder(read);
        while((read=bufferedReader.readLine())!=null){
            stringBuilder.append(read);
        }
        bufferedReader.close();
        return stringBuilder.toString();

    }

}
