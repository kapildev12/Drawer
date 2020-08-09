package qazi.tooba.com.drawer;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessfullyRegister extends AppCompatActivity {
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b = (Button) findViewById(R.id.succLogin);
        b.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(SuccessfullyRegister.this, Login.class);
                startActivity(intent);

            }
        });
    }
}
