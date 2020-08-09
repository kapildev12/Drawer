package qazi.tooba.com.drawer;

import android.media.AudioRouting;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Rating extends AppCompatActivity {
RatingBar rb;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        rb=(RatingBar) findViewById(R.id.ratingBar);
        textView= (TextView) findViewById(R.id.value);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar , float rating , boolean fromuser)
            {
                textView.setText("value is "+rating);
                textView.setText("Thank you for Rating ");

            }
        }

        );
    }
}
