package com.example.clindeqeuist.timecalculator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import com.example.clindeqeuist.timecalculator.model.Entry;
import com.example.clindeqeuist.timecalculator.model.EntryCollection;

import java.util.Collections;

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

        // FIXME: Clean up setup of the item touch helper
        final Paint swipeBackgroundPaint = new Paint();
        swipeBackgroundPaint.setColor(ContextCompat.getColor(recyclerView.getContext(),
                R.color.swipeBackground));
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target)
            {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                Collections.swap(entries.getEntries(), fromPosition, toPosition);
                entriesAdapter.notifyItemMoved(fromPosition, toPosition);

                updateResultView();
                return true;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();

                entries.getEntries().remove(position);
                entriesAdapter.notifyItemRemoved(position);

                updateResultView();
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
                    View itemView = viewHolder.itemView;
                    if (dX > 0)
                    {
                        c.drawRect(itemView.getLeft(), itemView.getTop(),
                                   itemView.getLeft() + dX, itemView.getBottom(), swipeBackgroundPaint);
                    }
                    else
                    {
                        c.drawRect(itemView.getRight() + dX, itemView.getTop(),
                                   itemView.getRight(), itemView.getBottom(), swipeBackgroundPaint);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
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
