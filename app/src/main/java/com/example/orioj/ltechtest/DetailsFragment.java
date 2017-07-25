package com.example.orioj.ltechtest;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by orioj on 24.07.2017.
 */

public class DetailsFragment extends Fragment {

    private JsonDataModel data;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.details_fragment, container, false);
        TextView tvHeader =(TextView) v.findViewById(R.id.detailsHeader);
        TextView tvBody =(TextView) v.findViewById(R.id.detailsBody);
        ImageView ivImage = (ImageView) v.findViewById(R.id.detailsImage);
        tvHeader.setText(data.getTitle());
        tvBody.setText(data.getText());
        Glide.with(this)
                .load(data.getImage())
                .apply(placeholderOf(R.drawable.android_logo))
                .apply(centerCropTransform())
                .into(ivImage);
        return v;
    }

    public void setData(JsonDataModel _data){ data = _data; }
}
