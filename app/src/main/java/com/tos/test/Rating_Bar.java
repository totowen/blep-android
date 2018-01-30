package com.tos.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.tos.blepdemo.R;

/**
 * Created by admin on 2016/3/26.
 */
public class Rating_Bar extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_bar);

        final RatingBar ratingBar_Small = (RatingBar)findViewById(R.id.ratingbar_Small);
        final RatingBar ratingBar_Indicator = (RatingBar)findViewById(R.id.ratingbar_Indicator);
        final RatingBar ratingBar_default = (RatingBar)findViewById(R.id.ratingbar_default);

        ratingBar_default.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratingBar_Small.setRating(rating);
                ratingBar_Indicator.setRating(rating);
                Toast.makeText(Rating_Bar.this, "rating:" + String.valueOf(rating),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
