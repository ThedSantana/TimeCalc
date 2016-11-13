package com.example.clindeqeuist.timecalculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clindeqeuist.timecalculator.R;
import com.example.clindeqeuist.timecalculator.model.Entry;
import com.example.clindeqeuist.timecalculator.model.EntryCollection;

public class EntryCollectionAdapter extends RecyclerView.Adapter<EntryViewHolder>
{

    private Context context;
    private EntryCollection entries;
    private LayoutInflater inflater;


    public EntryCollectionAdapter(Context context, EntryCollection entries)
    {
        this.context = context;
        this.entries = entries;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View entryView = inflater.inflate(R.layout.entry_layout, parent, false);

        TextView descriptionLabel = (TextView) entryView.findViewById(R.id.description);
        TextView valueLabel = (TextView) entryView.findViewById(R.id.value);

        return new EntryViewHolder(entryView, descriptionLabel, valueLabel);
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

        // FIXME
        // ImageView imageView = (ImageView) entryView.findViewById(R.id.icon);
        // // change the icon for Windows and iPhone
        // String s = values[position];
        // if (s.startsWith("iPhone")) {
        //     imageView.setImageResource(R.drawable.no);
        // } else {
        //     imageView.setImageResource(R.drawable.ok);
        // }
    }


    @Override
    public int getItemCount()
    {
        return entries.getEntries().size();
    }

}
