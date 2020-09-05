package qazi.tooba.com.drawer;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Contactus extends AppCompatActivity {
    Button sbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sbtn = (Button) findViewById(R.id.mail);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ambulancesurveillance@gmail.com"});
                intent.setData(Uri.parse("mailto:"));
                startActivity(Intent.createChooser(intent, "Select an app to email"));
            }
        });
    }
}