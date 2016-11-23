package com.example.clindeqeuist.timecalculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.clindeqeuist.timecalculator.R;
import com.example.clindeqeuist.timecalculator.model.Entry;
import com.example.clindeqeuist.timecalculator.model.EntryCollection;

import java.util.Collections;

public class EntryCollectionAdapter extends RecyclerView.Adapter<EntryViewHolder>
                                    implements ItemTouchHandler
{

    private EntryCollection entries;
    private LayoutInflater inflater;


    public EntryCollectionAdapter(Context context, EntryCollection entries)
    {
        this.entries = entries;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View entryView = inflater.inflate(R.layout.entry_layout, parent, false);

        TextView descriptionLabel = (TextView) entryView.findViewById(R.id.description);
        TextView valueLabel = (TextView) entryView.findViewById(R.id.value);

        final ImageButton operatorButton = (ImageButton) entryView.findViewById(R.id.operator_button);
        operatorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                operatorButton.setImageResource(R.drawable.ic_remove_circle);
            }
        });

        return new EntryViewHolder(entryView, descriptionLabel, valueLabel, operatorButton);
    }


    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position)
    {
        Entry entry = entries.getEntries().get(position);

        holder.getDescriptionLabel().setText(entry.getDescription());

        String value;
        if (entry.getValue() == null)
            value = "No value";
        else
            value = Integer.toString(entry.getValue());
        holder.getValueLabel().setText(value);
    }


    @Override
    public int getItemCount()
    {
        return entries.getEntries().size();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Collections.swap(entries.getEntries(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void onItemDismiss(int position)
    {
        entries.getEntries().remove(position);
        notifyItemRemoved(position);
    }

}
