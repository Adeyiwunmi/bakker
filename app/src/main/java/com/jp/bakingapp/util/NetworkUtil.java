package com.jp.bakingapp.util;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Recipe;
import com.jp.bakingapp.model.Step;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by LENIOVO on 8/2/2017.
 */

public class NetworkUtil {
    public static final String RequestTAG = "Request_Tag";
    private static final String KEY_STEP_ID = "id";
    private static final String KEY_STEP_SHORT_DESC = "shortDescription";
    private static final  String KEY_STEP_DESC =  "description";
    private static final String KEY_STEP_THUMBNAIL_URL = "thumbnailURL";
    private static final  String KEY_STEP_VIDEO_URL = "videoURL";
    private static  final String KEY_INGREDIENT_QUANTITY = "quantity";
    private static  final String KEY_INGREDIENT_MEASURE = "measure";
    private static final String KEY_INGREDIENT_NAME = "ingredient";
    private static final String KEY_RECIPE_NAME = "name";
    private  static  final String KEY_RECIPE_INGREDIENT = "ingredients";
    private static final String KEY_RECIPE_STEP = "steps";

   public  ArrayList<Recipe> recipeArrayList;










    public   ArrayList<Recipe> getRecipes(String recipeUrl){
         recipeArrayList = new ArrayList<>();
        AndroidNetworking.get(recipeUrl)
                .setPriority(Priority.HIGH)
                .setTag(RequestTAG)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray recipeArray = new JSONArray(response);
                            recipeArrayList = getRecipeFromArray(recipeArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                    recipeArrayList = null;
                 //   result = anError.toString();
                    }
                });




        return  recipeArrayList;
    }



    public  ArrayList<Step> getStepsFromArray(JSONArray stepsJsonArray) throws JSONException {
        ArrayList<Step> stepArrayList = new ArrayList<>();
        for (int i = 0; i < stepsJsonArray.length(); i ++){
            JSONObject stepObject = (JSONObject)stepsJsonArray.get(i);
            int step_id = stepObject.getInt(KEY_STEP_ID);
            String step_desc = stepObject.getString(KEY_STEP_DESC);
            String step_short_desc= stepObject.getString(KEY_STEP_SHORT_DESC);
            String video_url = stepObject.getString(KEY_STEP_VIDEO_URL);
            String thumbnail_url = stepObject.getString(KEY_STEP_THUMBNAIL_URL);
            Step currentStep = new Step(step_id,step_desc, video_url,thumbnail_url, step_short_desc);
            stepArrayList.add(currentStep);

        }
            return  stepArrayList;
    }


    public   ArrayList<Ingredient> getIngredientsFromArray(JSONArray ingredientsJsonArray) throws JSONException {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i ++){
            JSONObject ingredientObject = (JSONObject)ingredientsJsonArray.get(i);
                int quantity = ingredientObject.getInt(KEY_INGREDIENT_QUANTITY);
            String name = ingredientObject.getString(KEY_INGREDIENT_NAME);
            String measure = ingredientObject.getString(KEY_INGREDIENT_MEASURE);
            Ingredient currentIngredient = new Ingredient(name, measure, quantity);
            ingredientArrayList.add(currentIngredient);
        }
         return  ingredientArrayList;
    }


   public   ArrayList<Recipe> getRecipeFromArray(JSONArray recipeJsonArray) throws JSONException {
       ArrayList<Recipe> recipeArrayList = new ArrayList<>();
       for (int i = 0; i < recipeJsonArray.length(); i ++){
           JSONObject recipeObject = (JSONObject)recipeJsonArray.get(i);
           String recipeName = recipeObject.getString(KEY_RECIPE_NAME);
           JSONArray ingredientsArray = recipeObject.getJSONArray(KEY_RECIPE_INGREDIENT);
           JSONArray stepsArray = recipeObject.getJSONArray(KEY_RECIPE_STEP);
           ArrayList<Ingredient> ingredients = getIngredientsFromArray(ingredientsArray);
           ArrayList<Step> steps = getStepsFromArray(stepsArray);
          //  Recipe recipe = new Recipe(recipeName, ingredients, steps);
        //   recipeArrayList.add(recipe);

       }
       return  recipeArrayList;
   }

}
