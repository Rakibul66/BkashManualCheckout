package com.user.bkashcheckout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FeedAdapter extends FirestoreRecyclerAdapter<Feed, FeedAdapter.FeedHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    public FeedAdapter(@NonNull FirestoreRecyclerOptions<Feed> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull FeedHolder holder, int position, @NonNull Feed model) {
        holder.bkasno.setText("Bkash NO:"+model.getBkashno());
        holder.orderid.setText("OrderId:"+model.getOrderid());
        holder.transid.setText("TranscationID:"+model.getTranscationid());
        holder.Date.setText(model.getDate());
        holder.Category.setText("Method:"+model.getCategory());


    }


    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_layout,
                parent, false);
        return new FeedHolder(view);
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView bkasno,orderid, Date, Category,transid;

        public FeedHolder(View itemView) {
            super(itemView);
            bkasno = itemView.findViewById(R.id.Bkashno);
            orderid = itemView.findViewById(R.id.orderid);
           transid = itemView.findViewById(R.id.transcationid);
            Date = itemView.findViewById(R.id.date);
            Category = itemView.findViewById(R.id.category);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    getSnapshots().getSnapshot(position).getReference().delete();
                    notifyDataSetChanged();


                    Toast.makeText(mContext, "Deleting..", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}