package com.jp.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jp.bakingapp.R;
import com.jp.bakingapp.model.Recipe;
import com.jp.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
   private Context context;
   private ArrayList<Recipe> recipeArrayList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recipe_card, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
     Recipe recipe = recipeArrayList.get(position);
        ArrayList<Step> steps = recipe.getStepArrayList();
        holder.recipeNameTv.setText(recipe.getName());
      if (TextUtils.isEmpty(recipe.getImageUrl())){

       holder.recipeImageView.setImageResource(R.drawable.star);
      } else {

          Glide.with(context).load(recipe.getImageUrl()).into(holder.recipeImageView);
      }



    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     public TextView recipeNameTv;
        public TextView stepsTV;
        public ImageView recipeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeNameTv = (TextView)itemView.findViewById(R.id.idRecipeNameTv);
            stepsTV = (TextView)itemView.findViewById(R.id.idStepsTv);
            recipeImageView = (ImageView)itemView.findViewById(R.id.idImageRecipe);
        }
    }
}
