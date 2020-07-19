package com.example.krishnapatil.emergencyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView coordinates,messagetext;
    String latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        coordinates = (TextView) findViewById(R.id.coordinates);
        messagetext = (TextView) findViewById(R.id.messagetext);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (Boolean.parseBoolean(dataSnapshot.child("accident_flag").getValue().toString())==false){

                    messagetext.setTextColor(getResources().getColor(R.color.green));
                    messagetext.setText( "STATUS :- \n"+dataSnapshot.child("driversname").getValue().toString() + " is Fine.");

                }
                else {

                    messagetext.setTextColor(getResources().getColor(R.color.red));
                    messagetext.setText("STATUS \n"+ dataSnapshot.child("driversname").getValue().toString() + " met with an Accident.");
                    promptnotification();

                }

                longitude = dataSnapshot.child("longitude").getValue().toString();
                latitude = dataSnapshot.child("latitude").getValue().toString();

                coordinates.setText("Coordinates :- \n " + latitude+ "  "+longitude );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to Get Information!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void promptnotification() {

        if ( Build.VERSION.SDK_INT >= 26 ){

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = "some_channel_id";
            CharSequence channelName = "Some Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(1);
            notificationManager.createNotificationChannel(notificationChannel);

            String groupId = "some_group_id";
            CharSequence groupName = "Some Group";
            //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));

            //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyId = 1;
            //String channelId = "some_channel_id";

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0);

            Notification.Builder notification = new Notification.Builder(MainActivity.this)
                    .setContentTitle("ACCIDENT ALERT")
                    .setContentText("Driver meet with an Accident!!!")
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setChannelId(channelId)
                    .setContentIntent(pendingIntent);


            notificationManager.notify(notifyId,notification.build());

        }
    }

    public void onClickGetAddress(View view ){
        AddressDialog addressDialog = new AddressDialog(longitude,latitude);
        addressDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void onClickLocateOnMap(View view){

        Uri gmmIntentUri = Uri.parse("geo:"+ latitude+","+longitude+"");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }

    }

    public void onClickNearby(View view){

        Uri gmmIntentUri = Uri.parse("geo:"+ latitude+","+longitude+"?q=hospitals");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void onClickNavigation(View view){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+ latitude+","+longitude+"");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    public void onClickNearbyps(View view){

        Uri gmmIntentUri = Uri.parse("geo:"+ latitude+","+longitude+"?q=police stations");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }


}
