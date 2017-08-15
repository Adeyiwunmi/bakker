package com.jp.bakingapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.bakingapp.R;
import com.jp.bakingapp.fragments.RecipeDetailFragment;
import com.jp.bakingapp.model.Ingredient;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
  private Context context;
    private ArrayList<Ingredient> ingredientArrayList;


    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.ingredient_display, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;




    }

    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, int position) {
     Ingredient ingredient = ingredientArrayList.get(position);
        String ingredientName = ingredient.getName();
        String ingredientMeasure = ingredient.getMeasure();
        int ingredientQuantity = ingredient.getQuantity();

        String ingredientString =  ingredientName + "(" + String.valueOf(ingredientQuantity) + "" + ingredientMeasure + ")";
       String text = holder.ingredientTv.getText().toString() + ingredientString;
        holder.ingredientTv.setText(text);

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientTv = (TextView)itemView.findViewById(R.id.idTvIngredients);

        }
    }
}
