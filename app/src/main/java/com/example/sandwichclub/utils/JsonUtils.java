package com.example.sandwichclub.utils;

import android.text.TextUtils;

import com.example.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        final String SC_NAME = "name";
        final String SC_MAIN_NAME = "mainName";
        final String SC_ALSO_KNOWN_AS = "alsoKnownAs";
        final String SC_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String SC_DESCRIPTION = "description";
        final String SC_IMAGE = "image";
        final String SC_INGREDIENTS = "ingredients";

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject sandwichJsonItem = new JSONObject(json);

            JSONObject sandwichName = sandwichJsonItem.getJSONObject(SC_NAME);

            String mainName = sandwichName.getString(SC_MAIN_NAME);

            JSONArray alsoKnownAsArray = sandwichName.getJSONArray(SC_ALSO_KNOWN_AS);

            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                String knownAs = alsoKnownAsArray.optString(i);
                alsoKnownAsList.add(knownAs);

            }

            String placeOfOrigin = sandwichJsonItem.getString(SC_PLACE_OF_ORIGIN);
            String description = sandwichJsonItem.getString(SC_DESCRIPTION);
            String image = sandwichJsonItem.getString(SC_IMAGE);


            JSONArray ingredientsArray = sandwichJsonItem.getJSONArray(SC_INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredient = ingredientsArray.optString(i);
                ingredientsList.add(ingredient);
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
