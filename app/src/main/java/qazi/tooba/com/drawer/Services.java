package qazi.tooba.com.drawer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
