package com.mm.conceptrewind.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mm.conceptrewind.R;
import com.mm.conceptrewind.activities.SecondFragment;
import com.mm.conceptrewind.model.Summary;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ViewHolder> {

    private List<Summary> summaries;
    private Context context;

    public postAdapter(FragmentActivity activity, List<Summary> mData) {
        context = activity;
        summaries = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Summary summary = summaries.get(position);
        holder.text.setText(summary.getTopic());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondFragment.class);
                intent.putExtra("Topic",summary.getTopic());
                intent.putExtra("Notes",summary.getSummary());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return summaries.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView text;
        public LinearLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.topicHome);
            card = itemView.findViewById(R.id.postitem);
        }
    }

    public interface onPostClickListener{
        void onPostClick(int position);
    }


}
