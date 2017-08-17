package com.jp.bakingapp.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.bakingapp.R;
import com.jp.bakingapp.adapters.IngredientAdapter;
import com.jp.bakingapp.adapters.StepAdapter;
import com.jp.bakingapp.model.Ingredient;
import com.jp.bakingapp.model.Step;
import com.jp.bakingapp.util.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
@BindView(R.id.idRecyclerViewIngredient) RecyclerView recyclerIngredient;
    @BindView(R.id.idRecyclerViewSteps) RecyclerView recyclerviewSteps;
   public static ArrayList<Step> stepArrayList;
   private ArrayList<Ingredient> ingredientArrayList;
    private String recipe_name;
 private static final String KEY_STEPS = "steps";
    private static final String KEY_INGREDIENTS = "ingredients";
    private  static final  String KEY_NAME = "name";
   private  LinearLayoutManager linearLayoutManagerStep;
    private  LinearLayoutManager linearLayoutManagerIng;
    public static final  String KEY_STEP_SAVED= "step saved";
    public static final  String KEY_ING_SAVED= "ing saved";
    public static final  String KEY_INTENT_STEP_VIDEO_URL= "step video url";
    public static final  String KEY_INTENT_STEP_THUMBNAIL_URL= "step thumbnbail url";
Parcelable stateLayoutManIng;
    Parcelable stateLayoutManStep;
    private static final  String KEY_STATE_ING = "ing";
    private static final  String KEY_STATE_STEP = "step";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        stateLayoutManIng = linearLayoutManagerIng.onSaveInstanceState();
        stateLayoutManStep = linearLayoutManagerStep.onSaveInstanceState();
        outState.putParcelable(KEY_STATE_STEP, stateLayoutManStep);
        outState.putParcelable(KEY_STATE_ING, stateLayoutManIng);
        outState.putParcelableArrayList(KEY_STEP_SAVED, stepArrayList);
        outState.putParcelableArrayList(KEY_ING_SAVED, ingredientArrayList);

    }

    private  int orientation;
    Context context;


    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(ArrayList<Step> steps, ArrayList<Ingredient> ingredients, String name){
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_STEPS, steps);
        args.putParcelableArrayList(KEY_INGREDIENTS, ingredients);
        args.putString(KEY_NAME, name);
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(args);
       return recipeDetailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
         ButterKnife.bind(this, view);
            Bundle args = getArguments();
            stepArrayList = args.getParcelableArrayList(KEY_STEPS);
        ingredientArrayList = args.getParcelableArrayList(KEY_INGREDIENTS);
          linearLayoutManagerIng = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManagerStep = new LinearLayoutManager(getActivity().getApplicationContext());
         if (savedInstanceState != null){
             linearLayoutManagerIng.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE_ING));
             linearLayoutManagerStep.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_STATE_STEP));
             stepArrayList = savedInstanceState.getParcelableArrayList(KEY_STEP_SAVED);
             ingredientArrayList = savedInstanceState.getParcelableArrayList(KEY_ING_SAVED);
             setupStepRecycler(stepArrayList);
             setupIngredientRecycler(ingredientArrayList);

         }
           else {
             setupStepRecycler(stepArrayList);
             setupIngredientRecycler(ingredientArrayList);
         }
        // Inflate the layout for this fragment
        return  view;
    }
   public void setupStepRecycler(ArrayList<Step> steps){
        recyclerviewSteps.setLayoutManager(linearLayoutManagerStep);
       recyclerviewSteps.setHasFixedSize(true);
       StepAdapter stepAdapter = new StepAdapter(getActivity().getApplicationContext(), steps);
       recyclerviewSteps.setAdapter(stepAdapter);
       if (isLarge()) {
         recyclerviewSteps.addOnItemTouchListener(onItemClickLandscape);
       }
       else {
           recyclerviewSteps.addOnItemTouchListener(onItemTouchListenerSmallScreen);
       }

   }

    RecyclerItemClickListener onItemTouchListenerSmallScreen = new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
          Step step = stepArrayList.get(position);
          String step_desc = step.getDescription();
            String step_short_desc =  step.getShortDescription();
            String step_vid_url = step.getVideoUrl();
            String step_thumbUrl = step.getThumbnailUrl();
            StepFragment stepFragment = StepFragment.newInstance(step_vid_url,step_desc, step_thumbUrl, position);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.idContainer_Step_activity,stepFragment);
            fragmentTransaction.commit();


        }
    });




    public void setupIngredientRecycler(ArrayList<Ingredient> ingredients){
        recyclerIngredient.setLayoutManager(linearLayoutManagerIng);
        recyclerIngredient.setHasFixedSize(true);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity().getApplicationContext(), ingredients);
        recyclerIngredient.setAdapter(ingredientAdapter);

    }



    RecyclerItemClickListener onItemClickLandscape = new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Step step = stepArrayList.get(position);
            String step_desc = step.getDescription();
            String step_vid_url = step.getVideoUrl();
            String step_thumb_url = step.getThumbnailUrl();
            StepFragment stepFragment = StepFragment.newInstance(step_vid_url,step_desc, step_thumb_url, position);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.idcontainerStepFragLarge,stepFragment);
            fragmentTransaction.commit();

        }
    });


    private  boolean isLarge(){


       Context context = getActivity().getBaseContext();
      int  orientation = context.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE ||
                context.getResources().getConfiguration().smallestScreenWidthDp == 600|| isTablet()) {
            return  true;
        }    else {
            return  false;

        }
    }

 private  boolean isTablet(){
     return (getActivity().getApplicationContext().getResources().getConfiguration().screenLayout
             &Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

 }


}
