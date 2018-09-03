package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static List<String> alsoKnownAs = new ArrayList<String>();
    private static List<String> ingredients = new ArrayList<String>();

    public static Sandwich parseSandwichJson(String json)
    {

        Sandwich sandwich=new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject newJSON = jsonObject.getJSONObject("name");
            jsonObject = new JSONObject(newJSON.toString());
            sandwich.setMainName(jsonObject.getString("mainName"));

            alsoKnownAs.clear();
            ingredients.clear();

            JSONArray jArray = new JSONArray();
            jArray=jsonObject.getJSONArray("alsoKnownAs");
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    alsoKnownAs.add(jArray.getString(i));
                }
            }
            jsonObject = new JSONObject(json);
            JSONArray ingredientsArray = new JSONArray();
            ingredientsArray=jsonObject.getJSONArray("ingredients");
            if (ingredientsArray != null) {
                for (int j=0;j<ingredientsArray.length();j++){
                    ingredients.add(ingredientsArray.getString(j));
                }
            }

            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setImage(jsonObject.getString("image"));
            sandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
