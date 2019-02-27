package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        // Populate the user interface on this sandwich's detailed activity
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

    /**
     * TODO
     */
    private void populateUI(Sandwich sandwich) {
        // Check to make sure that the sandwich exists, and log the error if it doens't
        if (sandwich == null) {
            Log.e("DetailActivity: ", "The input sandwich object was null");
        }

        /* Set the text of the alsoKnownAs TextView. If there isn't any information,
           set the TextView to say "This information isn't available" */
        TextView alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);

        /* For each item in the array of alsoKnownAs names, append it to a string
           separated by commas */
        List<String> altNamesList = sandwich.getAlsoKnownAs();
        String altNames = "";
        for (String item : altNamesList) {
            altNames += item + " | ";
        }
        alsoKnownAsTV.setText(altNames);


        /* Set the text of the ingredients TextView. If there isn't any information,
           set the TextView to say "This information isn't available" */
        TextView ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);

        /* For each item in the array of ingredients, append it to a string
               separated by commas */
        List<String> ingredientsList = sandwich.getIngredients();
        String ingredientsString = "";
        for (String item : ingredientsList) {
            ingredientsString += item + " | ";
        }
        ingredientsTV.setText(ingredientsString);


        /* Set the text of the description TextView. If there isn't any information,
           set the TextView to say "This information isn't available" */
        TextView descriptionTV = (TextView) findViewById(R.id.description_tv);
        descriptionTV.setText(sandwich.getDescription());


        /* Set the text of the place of origin TextView. If there isn't any information,
           set the TextView to say "This information isn't available" */
        TextView placeOfOriginTV = (TextView) findViewById(R.id.origin_tv);
        placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
    }
}
