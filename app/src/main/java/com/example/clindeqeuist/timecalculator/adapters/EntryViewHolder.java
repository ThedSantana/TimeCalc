package com.example.clindeqeuist.timecalculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EntryViewHolder extends RecyclerView.ViewHolder
{

    private TextView descriptionLabel;
    private TextView valueLabel;
    private ImageButton operatorButton;


    public EntryViewHolder(View itemView, TextView descriptionLabel, TextView valueLabel,
                           ImageButton operatorButton)
    {
        super(itemView);

        this.descriptionLabel = descriptionLabel;
        this.valueLabel = valueLabel;
        this.operatorButton = operatorButton;
    }


    public TextView getDescriptionLabel()
    {
        return descriptionLabel;
    }


    public TextView getValueLabel()
    {
        return valueLabel;
    }


    public ImageButton getOperatorButton()
    {
        return operatorButton;
    }

}
