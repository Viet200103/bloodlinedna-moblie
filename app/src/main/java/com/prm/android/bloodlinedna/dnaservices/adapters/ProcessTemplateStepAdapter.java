package com.prm.android.bloodlinedna.dnaservices.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.models.services.ProcessTemplateStep;

import java.util.ArrayList;
import java.util.List;

public class ProcessTemplateStepAdapter extends RecyclerView.Adapter<ProcessTemplateStepAdapter.StepViewHolder> {

    private List<ProcessTemplateStep> steps = new ArrayList<>();

    public ProcessTemplateStepAdapter() {
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process_template_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        ProcessTemplateStep step = steps.get(position);
        holder.tvStepNumber.setText(String.valueOf(step.getStepOrder()));
        holder.tvStepTitle.setText(step.getStepName());
        holder.tvStepDesc.setText(step.getDescription());
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSteps(List<ProcessTemplateStep> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView tvStepNumber, tvStepTitle, tvStepDesc;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStepNumber = itemView.findViewById(R.id.tvStepNumber);
            tvStepTitle = itemView.findViewById(R.id.tvStepTitle);
            tvStepDesc = itemView.findViewById(R.id.tvStepDesc);
        }
    }
}