package com.amir.implicitintent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String phoneNumber = "01670015319";
    private String bdbl = "12 Kazi Nazrul Islam Avenue, Kawranbazar, Dhaka";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialNumber(View view) {
        Uri phoneUri = Uri.parse("tel:"+phoneNumber);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL,phoneUri);
        if (dialIntent.resolveActivity(getPackageManager()) !=null){
            startActivity(dialIntent);
        }else {
            Toast.makeText(this, "No app found", Toast.LENGTH_SHORT).show();
        }

    }

    public void callNumber(View view) {
        initiatePhoneCall();
    }

    public void showOnMap(View view) {
        Uri addressUri = Uri.parse("geo:0,0?q="+bdbl);
        Intent addressIntent = new Intent(Intent.ACTION_VIEW,addressUri);
        if (addressIntent.resolveActivity(getPackageManager()) !=null){
            startActivity(addressIntent);
        }else {
            Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCallPermission(){
        String[] permissions = {Manifest.permission.CALL_PHONE};
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,123);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            initiatePhoneCall();
        }
    }

    private void initiatePhoneCall(){
        Uri phoneUri = Uri.parse("tel:"+phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL,phoneUri);
        if (callIntent.resolveActivity(getPackageManager()) !=null){
            if(checkCallPermission()){
                startActivity(callIntent);
            }
        }else {
            Toast.makeText(this, "No app found", Toast.LENGTH_SHORT).show();
        }
    }
}
