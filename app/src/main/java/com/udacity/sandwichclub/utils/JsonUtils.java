package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * This method takes a Sandwich object represented in JSON form and parses
     * it's various components, such as ingredients, description, and so forth,
     * into Strings and lists for use elsewhere. It will create a new Sandwich object,
     * populate that Sandwich object's data members with the information extracted from
     * the input JSON string, and return that Sandwich object.
     *
     * @param json  The string form of the JSON that needs to be parsed.
     * @return  A Sandwich object created from the JSON string, with the revelent data members filled in.
     */
    public static Sandwich parseSandwichJson(String json) {
        // Check to see if the json string is empty; return null and log it if it is.
        if (json.isEmpty()) {
            Log.e("JsonUtils", "The input JSON string was empty");
            return null;
        }

        // Initialize JSON object and extract the relevent data
        try {
            JSONObject sandwichJSON = new JSONObject(json);

            // Extract name object
            JSONObject name = sandwichJSON.getJSONObject("name");

            // Extract mainName value
            String mainName = name.getString("mainName");

            // Extract alsoKnownAs array
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            // Extract placeOfOrigin value
            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");

            // Extract description value
            String description = sandwichJSON.getString("description");

            // Extract image string value
            String imageLocation = sandwichJSON.getString("image");

            // Extract ingredients array
            JSONArray ingredientsArray = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            // Create a new sandwich object and fill it with the data extracted above
            Sandwich newSandwich = new Sandwich();
            newSandwich.setMainName(mainName);
            newSandwich.setAlsoKnownAs(alsoKnownAs);
            newSandwich.setDescription(description);
            newSandwich.setPlaceOfOrigin(placeOfOrigin);
            newSandwich.setIngredients(ingredients);
            newSandwich.setImage(imageLocation);

            return newSandwich;


        }
        // Catch JSONException error
        catch (JSONException e) {
            Log.e("JsonUtils", e.toString());
            return null;
        }
    }
}
