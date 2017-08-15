package com.jp.bakingapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jp.bakingapp.R;
import com.jp.bakingapp.adapters.RecipeAdapter;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Recipe;
import com.jp.bakingapp.model.Step;
import com.jp.bakingapp.util.NetworkUtil;
import com.jp.bakingapp.util.RecyclerItemClickListener;
import com.jp.bakingapp.widget.LastSelectedIngredientPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.FileNameMap;
import java.net.URI;
import java.util.ArrayList;
import java.util.MissingResourceException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {
@BindView(R.id.idRecyclerRecipe) RecyclerView recyclerView;
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
    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this,view);
        AndroidNetworking.initialize(getActivity().getApplicationContext());








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
                            Toast.makeText(getActivity().getApplicationContext(), recipes.toString(), Toast.LENGTH_SHORT).show();
                             recipeAdapter = new RecipeAdapter(getActivity().getApplicationContext(), recipes);
                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(recipeAdapter);
                            recyclerView.addOnItemTouchListener(onItemTouchListener);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity().getApplicationContext(), anError.toString(), Toast.LENGTH_LONG).show();
                    }
                });



















        //recipes = NetworkUtil.getRecipes(RECIPE_URL);
              // Inflate the layout for this fragment

        












        return view;
    }

    RecyclerItemClickListener onItemTouchListener = new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
       Recipe recipe = recipes.get(position);
            saveTouchedRecipeIngredient(recipe.getIngredientArrayList());
       String name = recipe.getName();
            ArrayList<Ingredient> ingredients = recipe.getIngredientArrayList();
            ArrayList<Step> steps = recipe.getStepArrayList();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(steps,ingredients,name);
       //     fragmentTransaction.replace(R.id.idcontainer_main,recipeDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    });



    private void saveTouchedRecipeIngredient(ArrayList<Ingredient> ingredients){
        String formattedIngredient;
        int quantity;
        String measure;
        String ingredientDetails;
        String finalFormattedString = "";

        for (Ingredient ingredient : ingredients){
            formattedIngredient  = this.getActivity().getApplicationContext().getString(R.string.bullet);
            quantity = ingredient.getQuantity();
            measure = ingredient.getMeasure();
            ingredientDetails = ingredient.getName();
            formattedIngredient += " " + ingredientDetails + " ("+ quantity +" " + measure + ")";
            finalFormattedString += formattedIngredient+ "\n\n";

        }

        LastSelectedIngredientPreference.setIngredientPreference(this.getActivity().getApplicationContext(), finalFormattedString);
    }



}
