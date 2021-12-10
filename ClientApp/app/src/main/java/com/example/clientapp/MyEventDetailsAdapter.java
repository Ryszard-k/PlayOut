package com.example.clientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Football.Model.Comment;
import com.example.clientapp.Football.Model.FootballEvent;

import java.util.Set;

public class MyEventDetailsAdapter extends RecyclerView.Adapter<MyEventDetailsAdapter.MyEventDetailsItem> {

    private final Set<Comment> comments;

    public MyEventDetailsAdapter(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(R.layout.my_event_details_holder);
    }

    @NonNull
    @Override
    public MyEventDetailsItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new MyEventDetailsItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventDetailsItem holder, int position) {

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class MyEventDetailsItem extends RecyclerView.ViewHolder{

        private final TextView dateTextViewDetails;
        private final TextView timeTextViewDetails;
        private final TextView textViewTextComment;
        private final TextView authorTextViewComment;

        public MyEventDetailsItem(@NonNull View itemView) {
            super(itemView);

            dateTextViewDetails = itemView.findViewById(R.id.dateTextViewDetails);
            timeTextViewDetails = itemView.findViewById(R.id.timeTextViewDetails);
            textViewTextComment = itemView.findViewById(R.id.textViewTextComment);
            authorTextViewComment = itemView.findViewById(R.id.authorTextViewComment);
        }

        public TextView getDateTextViewDetails() {
            return dateTextViewDetails;
        }

        public TextView getTimeTextViewDetails() {
            return timeTextViewDetails;
        }

        public TextView getTextViewTextComment() {
            return textViewTextComment;
        }

        public TextView getAuthorTextViewComment() {
            return authorTextViewComment;
        }
    }
}
