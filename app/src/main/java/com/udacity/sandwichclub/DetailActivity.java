package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView knownAsText;
    private TextView originText;
    private TextView descriptionText;
    private TextView ingredientsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        knownAsText=findViewById(R.id.also_known_tv);
        originText=findViewById(R.id.origin_tv);
        descriptionText=findViewById(R.id.description_tv);
        ingredientsText=findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich)
    {
        String knownstr = TextUtils.join("\n", sandwich.getAlsoKnownAs());
        if(knownstr.equals(""))
            knownstr="Unknown Information";
        knownAsText.setText("\n"+knownstr);
        if(sandwich.getPlaceOfOrigin().toString().equals(""))
            originText.setText("\t\t"+"Unknown Information");
        else
            originText.setText("\t\t"+sandwich.getPlaceOfOrigin().toString());
        if(sandwich.getDescription().toString().equals(""))
            descriptionText.setText("Unknown Information");
        else
          descriptionText.setText("\n"+sandwich.getDescription().toString());
        String ingreTxt = TextUtils.join("\n", sandwich.getIngredients());
        if(ingreTxt.equals(""))
            ingreTxt="Unknown Information";
        ingredientsText.setText("\n"+ingreTxt);
    }
}
