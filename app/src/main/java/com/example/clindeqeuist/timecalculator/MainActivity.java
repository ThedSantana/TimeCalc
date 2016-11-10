package com.example.clindeqeuist.timecalculator;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.clindeqeuist.timecalculator.adapters.EntryCollectionAdapter;
import com.example.clindeqeuist.timecalculator.model.EntryCollection;

public class MainActivity extends AppCompatActivity
{

    public static final String ENTRIES_FILENAME = "entries.xml";

    private EntryCollection entries = new EntryCollection();
    private EntryCollectionAdapter entriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.list);
        entries.loadEntries(ENTRIES_FILENAME, getApplicationContext());
        entriesAdapter = new EntryCollectionAdapter(listView.getContext(), entries);
        listView.setAdapter(entriesAdapter);
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
            clearEntries();
            return true;
        }
        else if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onAddNewEntryClicked(View view)
    {
        addNewEntry();
    }


    private void clearEntries()
    {
        entries.getEntries().clear();
        saveEntriesAndNotifyChanged();

        Snackbar.make(findViewById(R.id.activity_main), "Entries cleared", Snackbar.LENGTH_SHORT)
                .show();
    }


    private void addNewEntry()
    {
        entries.getEntries().add("Entry " + Integer.toString(entriesAdapter.getCount() + 1));
        saveEntriesAndNotifyChanged();

        ListView listView = (ListView) findViewById(R.id.list);
        listView.smoothScrollToPosition(entries.getEntries().size() - 1);
    }


    private void saveEntriesAndNotifyChanged()
    {
        entries.saveEntries(ENTRIES_FILENAME, getApplicationContext());
        entriesAdapter.notifyDataSetChanged();
    }

}
