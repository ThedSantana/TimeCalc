package com.example.clindeqeuist.timecalculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EntryViewHolder extends RecyclerView.ViewHolder
{

    private TextView descriptionLabel;
    private TextView valueLabel;


    public EntryViewHolder(View itemView, TextView descriptionLabel, TextView valueLabel)
    {
        super(itemView);

        this.descriptionLabel = descriptionLabel;
        this.valueLabel = valueLabel;
    }


    public TextView getValueLabel()
    {
        return valueLabel;
    }


    public TextView getDescriptionLabel()
    {
        return descriptionLabel;
    }

}
