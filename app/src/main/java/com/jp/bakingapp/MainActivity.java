package com.jp.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jp.bakingapp.adapters.RecipeAdapter;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Recipe;
import com.jp.bakingapp.model.Step;
import com.jp.bakingapp.util.RecyclerItemClickListener;
import com.jp.bakingapp.widget.LastSelectedIngredientPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.idRecyclerRecipe)
    RecyclerView recyclerView;

    @BindView(R.id.idCorodinatorLayoutMain)
    CoordinatorLayout coordinatorLayoutMain;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Recipe> recipes;
    //NetworkUtil networkUtil;
    RecipeAdapter recipeAdapter;

    private static final String RECIPE_URL  = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

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
    private static final String KEY_IMAGE= "image";

    private static final String KEY_INTENT_NAME= "name";
    private static final String KEY_INTENT_STEPS = "steps";
    private static final String KEY_INTENT_INGREDIENTS = "ingredients";

     Parcelable mListSate;
    private static  int recycler_pos ;
    private  static  final  String STATE_RECYCLER_KEY = "recycler state ";
    private  static  final  String KEY_RECYCLER_POSITION = "position";
    private  int orientation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        orientation = getBaseContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE ||
                getBaseContext().getResources().getConfiguration().smallestScreenWidthDp == 600){
           layoutManager = new GridLayoutManager(this, 2);
        }
        else {
            layoutManager = new LinearLayoutManager(this);
        }


        if (savedInstanceState != null){
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(STATE_RECYCLER_KEY));
        }



        if (isNetworkAvailable()){


            AndroidNetworking.get(RECIPE_URL)
                    .setPriority(Priority.HIGH)
                    .setTag(RequestTAG)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                recipes = new ArrayList<Recipe>();
                                JSONArray array = new JSONArray(response);
                                for (int i = 0 ; i < array.length(); i ++){
                                    JSONObject recipeObj = (JSONObject)array.get(i);
                                    String recipe_name = recipeObj.getString(KEY_RECIPE_NAME);
                                    String image = recipeObj.getString(KEY_IMAGE);
                                    JSONArray ingredients = recipeObj.getJSONArray(KEY_RECIPE_INGREDIENT);
                                    ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
                                    for (int j = 0; j < ingredients.length(); j ++){
                                        JSONObject inredientObj = (JSONObject)ingredients.get(j);
                                        int qty = inredientObj.getInt(KEY_INGREDIENT_QUANTITY);
                                        String measure = inredientObj.getString(KEY_INGREDIENT_MEASURE);
                                        String nameIng = inredientObj.getString(KEY_INGREDIENT_NAME);
                                        ingredientList.add(new Ingredient(nameIng,measure,qty));
                                    }
                                    JSONArray steps = recipeObj.getJSONArray(KEY_RECIPE_STEP);
                                    ArrayList<Step> stepList = new ArrayList<Step>();
                                    for (int k = 0; k < steps.length(); k ++){
                                        JSONObject stepObj = (JSONObject)steps.get(k);
                                        int step_id = stepObj.getInt(KEY_STEP_ID);
                                        String step_desc = stepObj.getString(KEY_STEP_DESC);
                                        String step_short_desc= stepObj.getString(KEY_STEP_SHORT_DESC);
                                        String video_url = stepObj.getString(KEY_STEP_VIDEO_URL);
                                        String thumbnail_url = stepObj.getString(KEY_STEP_THUMBNAIL_URL);
                                        Step currentStep = new Step(step_id,step_desc, video_url,thumbnail_url, step_short_desc);
                                        stepList.add(currentStep);
                                    }

                                    recipes.add(new Recipe(recipe_name, ingredientList, stepList, image));
                                }
                                Toast.makeText(getApplicationContext(), recipes.toString(), Toast.LENGTH_SHORT).show();
                                setUpRecyclerView(recipes);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext(), anError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });



        }
        else {
            Snackbar.make(coordinatorLayoutMain, "No Network Connection", Snackbar.LENGTH_LONG).show();

        }

    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            return  true;
        }
        return  false;
    }


    public  void  setUpRecyclerView(ArrayList<Recipe> recipeArrayList){
        recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Recipe recipe = recipes.get(position);
                String name = recipe.getName();
                ArrayList<Ingredient> ingredients = recipe.getIngredientArrayList();
                saveTouchedRecipeIngredient(recipe.getIngredientArrayList());
                ArrayList<Step> steps = recipe.getStepArrayList();
                Intent startDetailActivity = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                startDetailActivity.putExtra(KEY_INTENT_NAME, name);
                startDetailActivity.putParcelableArrayListExtra(KEY_INTENT_STEPS, steps);
                startDetailActivity.putParcelableArrayListExtra(KEY_INTENT_INGREDIENTS, ingredients);
                startActivity(startDetailActivity);
            }
        }));


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListSate = layoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_RECYCLER_KEY, mListSate);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null ){
            mListSate = savedInstanceState.getParcelable(STATE_RECYCLER_KEY);
        }
    }
    private void saveTouchedRecipeIngredient(ArrayList<Ingredient> ingredients){
        String formattedIngredient;
        int quantity;
        String measure;
        String ingredientDetails;
        String finalFormattedString = "";

        for (Ingredient ingredient : ingredients){
            formattedIngredient  = getString(R.string.bullet);
            quantity = ingredient.getQuantity();
            measure = ingredient.getMeasure();
            ingredientDetails = ingredient.getName();
            formattedIngredient += " " + ingredientDetails + " ("+ quantity +" " + measure + ")";
            finalFormattedString += formattedIngredient+ "\n\n";

        }

        LastSelectedIngredientPreference.setIngredientPreference(getApplicationContext(), finalFormattedString);
    }


}

