package com.example.clindeqeuist.timecalculator.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.clindeqeuist.timecalculator.R;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback
{

    private final ItemTouchHandler itemTouchHandler;
    private final Paint swipeBackgroundPaint;


    public ItemTouchHelperCallback(ItemTouchHandler itemTouchHandler, Context context)
    {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
              ItemTouchHelper.START | ItemTouchHelper.END);

        this.itemTouchHandler = itemTouchHandler;

        swipeBackgroundPaint = new Paint();
        swipeBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.swipeBackground));
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target)
    {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        itemTouchHandler.onItemMove(fromPosition, toPosition);
        return true;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        int position = viewHolder.getAdapterPosition();
        itemTouchHandler.onItemDismiss(position);
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

}
