package com.example.clindeqeuist.timecalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.clindeqeuist.timecalculator.R;
import com.example.clindeqeuist.timecalculator.model.Entry;
import com.example.clindeqeuist.timecalculator.model.EntryCollection;

public class EntryCollectionAdapter extends BaseAdapter
{

    private Context context;
    private EntryCollection entries;


    public EntryCollectionAdapter( Context context, EntryCollection entries)
    {
        this.context = context;
        this.entries = entries;
    }


    @Override
    public int getCount()
    {
        return entries.getEntries().size();
    }


    @Override
    public Object getItem(int i)
    {
        return entries.getEntries().get(i);
    }


    @Override
    public long getItemId(int i)
    {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        Entry entry = entries.getEntries().get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryView = inflater.inflate(R.layout.entry_layout, viewGroup, false);

        TextView descriptionLabel = (TextView) entryView.findViewById(R.id.description);
        descriptionLabel.setText(entry.getDescription());

        TextView valueLabel = (TextView) entryView.findViewById(R.id.value);
        String value;
        if (entry.getValue() == null)
            value = "No value";
        else
            value = Integer.toString(entry.getValue());
        valueLabel.setText(value);

        // FIXME: ImageButton testing
        final ImageButton imageButton = (ImageButton) entryView.findViewById(R.id.operator_button);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                imageButton.setImageResource(R.drawable.ic_remove_circle);
            }
        });

        return entryView;
    }

}
