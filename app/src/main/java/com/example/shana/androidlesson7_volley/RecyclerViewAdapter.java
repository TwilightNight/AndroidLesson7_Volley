package com.example.shana.androidlesson7_volley;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shana on 2015/12/21.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<PersonalProfile> profileList;
    private RequestQueue requestQueue;

    public RecyclerViewAdapter(ArrayList<PersonalProfile> profileList, RequestQueue requestQueue) {
        this.profileList = profileList;
        this.requestQueue = requestQueue;
    }

    public void appendList(ArrayList<PersonalProfile> profileList) {
        this.profileList.addAll(profileList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View contactView = LayoutInflater.from(context).inflate(R.layout.adapter_personal_profile, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).layoutWithPersonalProfile(profileList.get(position));
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageLoader imageLoader;
        @Bind(R.id.adapter_personal_data_name)
        TextView nameText;
        @Bind(R.id.adapter_personal_data_age)
        TextView ageText;
        @Bind(R.id.adapter_profile_picture_image_view)
        NetworkImageView photoImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageLoader = new ImageLoader(requestQueue, new LruImageCache());
        }

        public void layoutWithPersonalProfile(PersonalProfile personalProfile) {
            nameText.setText("Name: " + personalProfile.getName());
            ageText.setText("Age: " + personalProfile.getAge());
            photoImageView.setImageUrl(Host.getImageUrl(personalProfile.getPhoto()), imageLoader);
        }
    }
}
