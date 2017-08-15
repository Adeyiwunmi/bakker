package com.jp.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jp.bakingapp.fragments.RecipeDetailFragment;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Step;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String KEY_STEP_LIST = "ingredients";
    public static final String KEY_ING_LIST = "ingredients" ;
    public static final String KEY_STEP_SHORT_DESC = "short_desc";
    public static final  String KEY_INTENT_STEP_DESC= "step description";
    public static final  String KEY_INTENT_STEP_SHORT_DESC= "step short description";
    public static final  String KEY_INTENT_STEP_VIDEO_URL= "step video url";
    public static final  String KEY_INTENT_STEP_THUMBNAIL_URL= "step thumbnbail url";
    private  static final  String Tag_frag = "tag";
  RecipeDetailFragment recipeDetailFragment;



    private static final String KEY_INTENT_NAME= "name";
    private static final String KEY_INTENT_STEPS = "steps";
    private static final String KEY_INTENT_INGREDIENTS = "ingredients";
ArrayList<Step> stepArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    String recipeName ;
      private  static  String FRAG_TAG1 = "tag1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_detail);
        if (savedInstanceState != null) {
            stepArrayList = savedInstanceState.getParcelableArrayList(KEY_STEP_LIST);
            ingredientArrayList = savedInstanceState.getParcelableArrayList(KEY_ING_LIST);
            //recipeDetailFragment = (RecipeDetailFragment)getSupportFragmentManager().findFragmentByTag(Tag_frag);
            recipeDetailFragment = RecipeDetailFragment.newInstance(stepArrayList, ingredientArrayList,recipeName);
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            int frameLayoutId ;
            if (isLarge()){
                frameLayoutId = R.id.idFRameLayoutRecipeDetailLarge;
            }  else {
                frameLayoutId = R.id.idContainer_Step_activity;
            }
                     fragmentTransaction.replace(frameLayoutId,recipeDetailFragment);
            fragmentTransaction.commit();
         //   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.remove(recipeDetailFragment);
           // fragmentTransaction.replace(frameLayoutId, recipeDetailFragment, Tag_frag);
           // fragmentTransaction.commit();
            //setUp();

        } else {
            if (getIntent() != null) {
                stepArrayList = getIntent().getParcelableArrayListExtra(KEY_INTENT_STEPS);
                ingredientArrayList = getIntent().getParcelableArrayListExtra(KEY_INTENT_INGREDIENTS);
                recipeName = getIntent().getStringExtra(KEY_INTENT_NAME);
                getSupportActionBar().setTitle(recipeName);
                setUp();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_STEP_LIST, stepArrayList);
        outState.putParcelableArrayList(KEY_ING_LIST, ingredientArrayList);
    }
    private  void  setUp(){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
             recipeDetailFragment = RecipeDetailFragment.newInstance(stepArrayList, ingredientArrayList, recipeName);
            //       recipeDetailFragment.setRetainInstance(true);
                    int frameLayoutId ;
         if (isLarge()){
             frameLayoutId = R.id.idFRameLayoutRecipeDetailLarge;
         }  else {
             frameLayoutId = R.id.idContainer_Step_activity;
         }
        fragmentTransaction.add(frameLayoutId, recipeDetailFragment, Tag_frag);
        fragmentTransaction.commit();


        }



    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private  boolean isLarge(){
        int orientation = getBaseContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE ||
                getBaseContext().getResources().getConfiguration().smallestScreenWidthDp == 600 ||
                isTablet(getApplicationContext())) {
        return true;
        } else {
            return false;
        }


    }

}

