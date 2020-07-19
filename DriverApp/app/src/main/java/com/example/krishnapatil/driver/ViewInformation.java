package com.example.krishnapatil.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewInformation extends AppCompatActivity {

    TextView driversname, vehicleno, travellingfrom, travellingto;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);

        driversname = (TextView) findViewById(R.id.driversname);
        vehicleno = (TextView) findViewById(R.id.vehicleno);
        travellingfrom = (TextView) findViewById(R.id.travellingfrom);
        travellingto = (TextView) findViewById(R.id.travellingto);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                driversname.setText( "Driver Name :- "+dataSnapshot.child("driversname").getValue().toString() );
                vehicleno.setText( "Vehicle No :- "+dataSnapshot.child("vehicleno").getValue().toString() );
                travellingto.setText( "Travelling To :- "+dataSnapshot.child("travellingto").getValue().toString() );
                travellingfrom.setText( "Travelling From :- "+dataSnapshot.child("travellingfrom").getValue().toString() );

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ViewInformation.this, "Failed to Fetch Information!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickBackToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickUpdateInformation(View view){
        Intent intent = new Intent(this, AddInformation.class);
        startActivity(intent);
    }
}
