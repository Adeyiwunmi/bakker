package com.jp.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jp.bakingapp.fragments.RecipeDetailFragment;
import com.jp.bakingapp.fragments.StepFragment;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String KEY_STEP_LIST = "ingredients";
    public static final String KEY_ING_LIST = "ingredients" ;
    public static final String KEY_STEP_SHORT_DESC = "short_desc";
    public static final  String KEY_INTENT_STEP_DESC= "step description";
    public static final  String KEY_INTENT_STEP_SHORT_DESC= "step short description";
    public static final  String KEY_INTENT_STEP_VIDEO_URL= "step video url";
    public static final  String KEY_FRAG = "fragment";
    private  static final  String Tag_frag = "tag";
    private static final String KEY_RECIPE_NAME = "reicpe name" ;
    RecipeDetailFragment recipeDetailFragment;
    StepFragment stepFragment;


    private static final String KEY_INTENT_NAME= "name";
    private static final String KEY_INTENT_STEPS = "steps";
    private static final String KEY_INTENT_INGREDIENTS = "ingredients";
ArrayList<Step> stepArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    String recipeName ;
      private  static  String FRAG_TAG1 = "tag1";
    static  int FrameLayoutid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_detail);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            Fragment mFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAG);

            if ( !(mFragment  instanceof  StepFragment)){
                stepArrayList = savedInstanceState.getParcelableArrayList(KEY_STEP_LIST);
                ingredientArrayList = savedInstanceState.getParcelableArrayList(KEY_ING_LIST);
                recipeDetailFragment = (RecipeDetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,KEY_FRAG);
                getSupportFragmentManager().beginTransaction().remove(recipeDetailFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                getSupportFragmentManager().beginTransaction().replace(getLayoutId(),recipeDetailFragment).commit();

            }  else {
                stepFragment = (StepFragment)getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAG);
                 getSupportFragmentManager().beginTransaction().remove(stepFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                getSupportFragmentManager().beginTransaction().replace(getStepLayout(), stepFragment).commit();
                if (isLarge()){
                    stepArrayList = getIntent().getParcelableArrayListExtra(KEY_INTENT_STEPS);
                    ingredientArrayList = getIntent().getParcelableArrayListExtra(KEY_INTENT_INGREDIENTS);
                    recipeName = getIntent().getStringExtra(KEY_INTENT_NAME);
                    getSupportActionBar().setTitle(recipeName);
                    setUp();

                }
            }

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
        outState.putParcelableArrayList(KEY_ING_LIST, ingredientArrayList);
        outState.putParcelableArrayList(KEY_STEP_LIST, stepArrayList);
        outState.putString(KEY_RECIPE_NAME, recipeName);
       // if (recipeDetailFragment != null   && recipeDetailFragment.isAdded()){
        getSupportFragmentManager().putFragment(outState,KEY_FRAG, getCurrentFragment());
        //}   else {
        //    getSupportFragmentManager().putFragment(outState, KEY_FRAG,getCur );


    }
    private  void  setUp(){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
             recipeDetailFragment = RecipeDetailFragment.newInstance(stepArrayList, ingredientArrayList, recipeName);
                 recipeDetailFragment.setRetainInstance(true);

        fragmentTransaction.add(getLayoutId(), recipeDetailFragment, Tag_frag);
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
private  int getLayoutId(){
    if (isLarge()){
        return R.id.idFRameLayoutRecipeDetailLarge;
    }
    else {
        return R.id.idContainer_Step_activity;
    }
}
private int getStepLayout(){
    if (isLarge()){
        return R.id.idcontainerStepFragLarge;
    }
    else {
        return R.id.idContainer_Step_activity;
    }

}

private  Fragment getCurrentFragment(){

    FragmentManager fragmentManager = RecipeDetailActivity.this.getSupportFragmentManager();
    List<Fragment> fragments = fragmentManager.getFragments();
    for (Fragment fragment: fragments) {
        if (fragment!= null && fragment.isVisible()){
            return fragment;
        }

   }
    return null;
}
}

