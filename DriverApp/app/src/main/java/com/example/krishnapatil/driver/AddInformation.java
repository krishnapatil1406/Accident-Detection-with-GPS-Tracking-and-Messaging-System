package com.example.krishnapatil.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInformation extends AppCompatActivity {

    EditText driversname, vehicleno, travellingfrom, travellingto;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        driversname = (EditText) findViewById(R.id.name);
        vehicleno = (EditText) findViewById(R.id.vehicleno);
        travellingfrom = (EditText) findViewById(R.id.travellingfrom);
        travellingto = (EditText) findViewById(R.id.travellingto);

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void onClickBackToHome(View view){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickUploadInformation(View view){

        String dn = driversname.getText().toString().trim();
        String vn = vehicleno.getText().toString().trim();
        String tf = travellingfrom.getText().toString().trim();
        String tt = travellingto.getText().toString().trim();

        if (! ( TextUtils.isEmpty(dn) || TextUtils.isEmpty(vn) || TextUtils.isEmpty(tf) || TextUtils.isEmpty(tt) ) ){

            databaseReference.push();

            DriverInformation driverInformation = new DriverInformation(dn,vn,tf,tt,0.0,0.0,false);

            databaseReference.setValue(driverInformation);



            Toast.makeText(this, "Data Added Successfully!!!", Toast.LENGTH_SHORT).show();


        }
        else {
            Toast.makeText(this, "Input Field Cannot be Blank!!!", Toast.LENGTH_SHORT).show();
        }



    }




}
