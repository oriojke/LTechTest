package com.example.orioj.ltechtest;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.w3c.dom.Text;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by orioj on 24.07.2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

    private List<JsonDataModel> cards;

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView tvHeader;
        public TextView tvBody;
        public TextView tvDate;
        public ImageView ivImage;
        public CardView cvCard;
        public JsonDataModel data;

        public ViewHolder(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            cvCard = (CardView) itemView.findViewById(R.id.cardView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            cvCard.setOnClickListener(this);
        }

        public void setData(JsonDataModel d){ data = d; }

        @Override
        public void onClick(View v) {
            MainActivity mainActivity = (MainActivity) getActivity(v);
            if(mainActivity != null) mainActivity.onCardClicked(data);
        }

        private Activity getActivity(View v) {
            Context context = v.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity)context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            return null;
        }
    }

    public CardsAdapter(List<JsonDataModel> _cards){
        cards = _cards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JsonDataModel item = cards.get(position);
        holder.tvHeader.setText(item.getTitle());
        holder.tvBody.setText(item.getText());
        holder.tvDate.setText(item.getDate());
        Glide.with(holder.tvHeader.getContext())
                .load(item.getImage())
                .apply(centerCropTransform())
                .into(holder.ivImage);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }


}
