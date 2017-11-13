package com.nestor.app.todolist;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nestor.app.todolist.data.TaskContract.TaskEntry;

/**
 * Created by Chief on 11.11.2017.
 */

public class TaskCursorAdapter extends RecyclerView.Adapter<TaskCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor for the TaskCursorAdapter that initializes the Context.
     * @param mContext the current Context
     */
    public TaskCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(TaskEntry._ID);
        int titleIndex = mCursor.getColumnIndex(TaskEntry.COLUMN_TITLE);
        int descriptionIndex = mCursor.getColumnIndex(TaskEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(TaskEntry.COLUMN_PRIORITY);
        int stateIndex = mCursor.getColumnIndex(TaskEntry.COLUMN_STATE);

        mCursor.moveToPosition(position);

        // Determine the values of the wanted data
        final long id = mCursor.getLong(idIndex);
        String title = mCursor.getString(titleIndex);
        String description = mCursor.getString(descriptionIndex);
        int priority = mCursor.getInt(priorityIndex);
        boolean state = mCursor.getInt(stateIndex) == 1;

        //Set values
        holder.taskTitleView.setText(title);
        holder.taskDescriptionView.setText(description);

        holder.itemView.setTag((int) id);


        holder.priorityView.setBackgroundResource(getPriorityColor(priority));

        holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditorActivity.class);
                        Uri currentPetUri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI, id);

                        // Set the URI on the data field of the intent
                        intent.setData(currentPetUri);

                        // Launch the {@link EditorActivity} to display the data for the current pet.
                        mContext.startActivity(intent);
                    }
        });
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    private int getPriorityColor(int priority) {
        int priorityColor = R.color.materialRed;

        switch(priority) {
            case 1: priorityColor = R.color.materialRed;
                break;
            case 2: priorityColor = R.color.materialBlue;
                break;
            case 3: priorityColor = R.color.materialGreen;
                break;
            default: break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitleView;
        TextView taskDescriptionView;
        View priorityView;
        CardView view;

        /**
         * Constructor for the TaskViewHolders.
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            taskTitleView = itemView.findViewById(R.id.title_textView);
            taskDescriptionView = itemView.findViewById(R.id.description_textView);
            priorityView = itemView.findViewById(R.id.priority_view);
            view = itemView.findViewById(R.id.card_view);
        }
    }
}
