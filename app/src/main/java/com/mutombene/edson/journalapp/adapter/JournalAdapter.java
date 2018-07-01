package com.mutombene.edson.journalapp.adapter;




import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mutombene.edson.journalapp.R;
import com.mutombene.edson.journalapp.database.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by EMutombene on 6/25/2018.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder>{




    final private ItemClickListener mItemClickListener;
    private List<JournalEntry> mJournalEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private static final String DATE_FORMAT = "dd/MM/yyy hh:mm:ss a";

    public JournalAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_layout,parent,false);

        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        JournalEntry journalEntry = mJournalEntries.get(position);
        String title = journalEntry.getTitle();
        String content = journalEntry.getContent();
        String createdAt = dateFormat.format(journalEntry.getCreatedAt());
        String updatedAt = dateFormat.format(journalEntry.getUpdatedAt());
        String mood = journalEntry.getMood();

        holder.createdAtView.setText(createdAt);
        holder.journalTitleView.setText(title);


        int color = ContextCompat.getColor(this.mContext,R.color.colorPrimaryLight);

        switch (mood){
            case "happy":
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodHappy);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

            case "ungry" :
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodUngry);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

            case "down" :
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodDown);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

            case "calm" :
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodCalm);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

            case "relaxed" :
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodRelaxed);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

            case "sad" :
                color = ContextCompat.getColor(this.mContext,R.color.colorMoodSad);
                holder.journalMoodImageView.setBackgroundColor(color);
                break;

        }




    }

    @Override
    public int getItemCount() {
        if (mJournalEntries == null) {
            return 0;
        }
        return mJournalEntries.size();
    }

    public void setJournals(List<JournalEntry> journalEntries) {
        mJournalEntries = journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView journalTitleView;
        TextView createdAtView;
        TextView journalContentView;
        ImageView journalMoodImageView;

        public JournalViewHolder(View itemView) {
            super(itemView);
            journalTitleView = itemView.findViewById(R.id.journalTitle);
            createdAtView = itemView.findViewById(R.id.journalCreatedAt);
            journalMoodImageView = itemView.findViewById(R.id.circleView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view){
            int elementId = mJournalEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }

    }


    public List<JournalEntry> getJournals(){
        return mJournalEntries;
    }

}
