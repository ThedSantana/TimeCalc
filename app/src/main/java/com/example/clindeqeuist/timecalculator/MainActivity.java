package com.example.clindeqeuist.timecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clindeqeuist.timecalculator.model.EntryCollection;

public class MainActivity extends AppCompatActivity
{

    public static final String ENTRIES_FILENAME = "entries.xml";

    private EntryCollection entries = new EntryCollection();
    private ArrayAdapter<String> entriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entries.loadEntries(ENTRIES_FILENAME, getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.list);
        entriesAdapter = new ArrayAdapter<>(listView.getContext(),
                android.R.layout.simple_list_item_1, entries.getEntries());
        listView.setAdapter(entriesAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ListView listView = (ListView) findViewById(R.id.list);
                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
                entries.getEntries().add("Entry " + Integer.toString(adapter.getCount() + 1));
                entries.saveEntries(ENTRIES_FILENAME, getApplicationContext());
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(entries.getEntries().size() - 1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_clear)
        {
            entries.getEntries().clear();
            entries.saveEntries(ENTRIES_FILENAME, getApplicationContext());
            entriesAdapter.notifyDataSetChanged();

            Snackbar.make(findViewById(R.id.activity_main), "Entries cleared", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            return true;
        }
        else if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
