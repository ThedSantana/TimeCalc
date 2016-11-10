package com.example.clindeqeuist.timecalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.clindeqeuist.timecalculator.R;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.entry_layout, viewGroup, false);

        TextView label = (TextView) rowView.findViewById(R.id.label);
        label.setText(entries.getEntries().get(i));

        TextView dummy = (TextView) rowView.findViewById(R.id.dummy);
        dummy.setText("Second dummy label...");

//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        // change the icon for Windows and iPhone
//        String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }

}
