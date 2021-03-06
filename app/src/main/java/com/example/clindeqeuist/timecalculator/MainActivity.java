package com.example.clindeqeuist.timecalculator;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.clindeqeuist.timecalculator.adapters.EntryCollectionAdapter;
import com.example.clindeqeuist.timecalculator.adapters.ItemTouchHelperCallback;
import com.example.clindeqeuist.timecalculator.model.Entry;
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

        entries.loadEntries(ENTRIES_FILENAME, getApplicationContext());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        entriesAdapter = new EntryCollectionAdapter(recyclerView.getContext(), entries);
        recyclerView.setAdapter(entriesAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(entriesAdapter, recyclerView.getContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        updateResultView();
    }


    @Override
    protected void onRestart()
    {
        super.onRestart();

        entries.loadEntries(ENTRIES_FILENAME, getApplicationContext());
        entriesAdapter.notifyDataSetChanged();
        updateResultView();
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        entries.saveEntries(ENTRIES_FILENAME, getApplicationContext());
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
        entriesAdapter.notifyDataSetChanged();
        updateResultView();

        Snackbar.make(findViewById(R.id.activity_main), "Entries cleared", Snackbar.LENGTH_SHORT)
                .show();
    }


    private void addNewEntry()
    {
        int position = entriesAdapter.getItemCount();

        int value = position + 1;
        String description = "Entry " + Integer.toString(value);

        entries.getEntries().add(new Entry(description, value));
        entriesAdapter.notifyItemInserted(position);
        updateResultView();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.smoothScrollToPosition(position);
    }


    private void updateResultView()
    {
        TextView resultView = (TextView) findViewById(R.id.result);
        resultView.setText(Integer.toString(entries.getEntries().size()));
    }

}
