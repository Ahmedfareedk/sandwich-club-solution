package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_PLACE_OF_ORIGINS = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_INGREDIENTS = "ingredients";


    private static JSONObject jsonObject;
    private static JSONObject jsonWholeName ;




    public static Sandwich parseSandwichJson(String json) {


        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> alsoKnownAs = new ArrayList<>(0);
        List<String> ingredients = new ArrayList<>(0);

        try {

            //getting a reference to json;
             jsonObject = new JSONObject(json);

            //getting the whole json name;
            jsonWholeName = jsonObject.getJSONObject(SANDWICH_NAME);

            mainName = jsonWholeName.optString(SANDWICH_MAIN_NAME);

            placeOfOrigin = jsonObject.optString(SANDWICH_PLACE_OF_ORIGINS);

            description = jsonObject.optString(SANDWICH_DESCRIPTION);

            image = jsonObject.optString(SANDWICH_IMAGE);

            ingredients = getJsonArray(SANDWICH_INGREDIENTS);

            alsoKnownAs = getJsonArray(SANDWICH_ALSO_KNOWN_AS);


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }


    private static List<String>  getJsonArray(String titleString) {

        List<String> list = new ArrayList<>(0);

        try {

        if (titleString.equals(SANDWICH_ALSO_KNOWN_AS)) {

                JSONArray alsoKnownAsArray = jsonWholeName.getJSONArray(SANDWICH_ALSO_KNOWN_AS);
                for (int i = 0, n = alsoKnownAsArray.length(); i < n; i++) {

                    list.add(alsoKnownAsArray.getString(i));
                }
            }
                if (titleString.equals(SANDWICH_INGREDIENTS)) {

                    JSONArray ingredientsArray = jsonObject.getJSONArray(SANDWICH_INGREDIENTS);
                    for (int i = 0, n = ingredientsArray.length(); i < n; i++) {

                        list.add(ingredientsArray.getString(i));
                    }
                }
            }catch (JSONException e){

            Log.d(TAG, e.getMessage());
        }
        return list;
    }

}
