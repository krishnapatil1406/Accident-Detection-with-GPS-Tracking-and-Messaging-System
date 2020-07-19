package com.example.krishnapatil.emergencyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import androidx.appcompat.app.AppCompatDialogFragment;

public class AddressDialog extends AppCompatDialogFragment {

    static public String longitude,latitude;

    public AddressDialog(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Address :- ")
                .setMessage( "\n"+ convertLocationToAddress() +"\n");
        return builder.create();
    }

    private String convertLocationToAddress() {
        String addressText;

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    1
            );
        } catch (Exception Exception) {
            addressText = "Error : Coordinates cannot be Converted into Address.";
        }

        if (addresses == null || addresses.size() == 0) {
            addressText = "Error : Coordinates cannot be Converted into Address.";
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            addressText =
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments);
        }

        return addressText;

    }


}
