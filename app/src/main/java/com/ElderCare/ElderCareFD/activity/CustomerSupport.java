package com.ElderCare.ElderCareFD.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ElderCare.ElderCareFD.R;

public class CustomerSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Button call= (Button) findViewById(R.id.btnCall);
        Button email= (Button) findViewById(R.id.btnEmail);
        ImageView wsap= (ImageView) findViewById(R.id.imageWhatsapp);

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9986269411"));

             //   if (ActivityCompat.checkSelfPermission(CustomerSupport.this,
               //         Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                 //   return;
                //}
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });


        wsap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);


  //              sendIntent.putExtra(Intent.EXTRA_TEXT,"Text sent");
    //            sendIntent.setType("text/plain");
      //          startActivity(sendIntent);
                openWhatsApp(v);
            }
        });





    }




    @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","borntohelpindia@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public void openWhatsApp(View view){
        PackageManager pm=getPackageManager();
        try {


            String toNumber = "9986269411";
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"no whatsapp",Toast.LENGTH_LONG).show();

        }
    }


}