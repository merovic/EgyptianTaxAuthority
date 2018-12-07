package com.amirahmed.egyptiantaxauthority.Sample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amirahmed.egyptiantaxauthority.R;

import java.util.List;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {

    List<MessageItem> messageItemList;

    Context context;

    SampleAdapter(List<MessageItem> messageItemList) {
        this.messageItemList = messageItemList;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new SampleAdapter.SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {

        holder.textView.setText(messageItemList.get(position).getMessage());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(850, LinearLayout.LayoutParams.WRAP_CONTENT);


        if(position==0 || position==1)
        {
            holder.cardView.setCardBackgroundColor(Color.CYAN);
            params.gravity = Gravity.START;

            holder.cardView.setLayoutParams(params);
        }else
            {
                if((position % 2) == 0)
                {
                    holder.cardView.setCardBackgroundColor(Color.LTGRAY);
                    params.gravity = Gravity.END;

                    holder.cardView.setLayoutParams(params);
                }else
                    {
                        holder.cardView.setCardBackgroundColor(Color.CYAN);
                        params.gravity = Gravity.START;

                        holder.cardView.setLayoutParams(params);
                    }

            }

    }

    @Override
    public int getItemCount() {
        return messageItemList.size();
    }


    class SampleViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textView;

        SampleViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardmessage);
            textView = itemView.findViewById(R.id.message);
        }
    }
}
