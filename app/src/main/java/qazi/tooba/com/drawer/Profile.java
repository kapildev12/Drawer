package qazi.tooba.com.drawer;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import qazi.tooba.com.drawer.Common.Common;

public class Profile extends AppCompatActivity {

    TextView Name,email,phone_no,bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Name = (TextView)findViewById(R.id.nametext);
        email   = (TextView)findViewById(R.id.emailtext);
        phone_no = (TextView)findViewById(R.id.phonetext);
        bloodGroup =(TextView)findViewById(R.id.bloodgroup);

        Name.setText(Common.currentRider.getName());
        email.setText(Common.currentRider.getEmail());
        phone_no.setText(Common.currentRider.getPhone());
        bloodGroup.setText(Common.currentRider.getBloodGroup());
    }


}
