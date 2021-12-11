package com.example.clientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Football.Model.Comment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyEventDetailsAdapter extends RecyclerView.Adapter<MyEventDetailsAdapter.MyEventDetailsItem> {

    private List<Comment> commentsList;
    private List<Comment> resultOfSort;

    public MyEventDetailsAdapter( List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public MyEventDetailsItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_event_details_holder, parent, false);

        resultOfSort = commentsList.stream().sorted(Comparator.comparing(Comment::getDate).thenComparing(Comment::getTime)).collect(Collectors.toList());
        return new MyEventDetailsItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventDetailsItem holder, int position) {

        holder.getDateTextViewDetails().setText(resultOfSort.get(position).getDate().toString());
        holder.getAuthorTextViewComment().setText(resultOfSort.get(position).getAuthor().getUsername());
        holder.getTimeTextViewDetails().setText(resultOfSort.get(position).getTime().toString());
        holder.getTextViewTextComment().setText(resultOfSort.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
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
