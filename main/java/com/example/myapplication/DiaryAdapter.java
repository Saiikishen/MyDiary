package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DiaryAdapter extends FirestoreRecyclerAdapter<diaryentry, DiaryAdapter.DiaryViewHolder> {
    Context context;
    public DiaryAdapter(@NonNull FirestoreRecyclerOptions<diaryentry> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DiaryViewHolder holder, int position, @NonNull diaryentry diaryentry) {
        holder.dateTextView.setText(diaryentry.date);
        holder.contentTextView.setText(diaryentry.content);
        holder.timestampTextView.setText(Utility.timestampToString(diaryentry.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, DiaryDetails.class);
            intent.putExtra("date", diaryentry.date);
            intent.putExtra("content", diaryentry.content);
            String docID = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docID", docID);
            context.startActivity(intent);

        });

    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent,false);

        return new DiaryViewHolder(view);
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder
    {   TextView dateTextView, contentTextView, timestampTextView;
        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);

        }
    }




    }
