package com.jp.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.bakingapp.R;
import com.jp.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
   private Context context;
    private ArrayList<Step> stepArrayList;

    public StepAdapter(Context context, ArrayList<Step> stepArrayList) {
        this.context = context;
        this.stepArrayList = stepArrayList;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.steps_display, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, int position) {
        Step step = stepArrayList.get(position);
       String stepDisplay = String.valueOf(step.getId()) + "." + "  " + step.getShortDescription();
        holder.stepTv.setText(stepDisplay);
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView stepTv;

        public ViewHolder(View itemView) {
            super(itemView);
            stepTv = (TextView)itemView.findViewById(R.id.idTvStep);
        }
    }
}
