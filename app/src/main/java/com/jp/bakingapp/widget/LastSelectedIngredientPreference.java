package com.jp.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LENIOVO on 8/6/2017.
 */

public class LastSelectedIngredientPreference {


    public static final String INGREDIENT_PREFERENCE = "ingredient_preference";
    private static final String KEY_INGREDIENT = "ingredient";
    private static final String DEFAULT_MESSAGE = "No Ingredient at the moment";
    SharedPreferences sharedPreferences;
    static LastSelectedIngredientPreference INSTANCE;

    private LastSelectedIngredientPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(INGREDIENT_PREFERENCE, Context.MODE_PRIVATE);

    }

    String getIngredientPreference() {
        return sharedPreferences.getString(KEY_INGREDIENT, DEFAULT_MESSAGE);
    }

    public static  void setIngredientPreference(Context context, String ingredient){
        getInstance(context).setIngredientPreference(ingredient);
    }

    public  void setIngredientPreference(String ingredient){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_INGREDIENT, ingredient);
        editor.commit();
    }
    public static String getLastSelectedIngredient(Context context) {
        return getInstance(context).getIngredientPreference();
    }

    private static LastSelectedIngredientPreference getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LastSelectedIngredientPreference(context);
        }
        return INSTANCE;
    }
}
