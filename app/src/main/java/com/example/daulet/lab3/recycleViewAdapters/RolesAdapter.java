package com.example.daulet.lab3.recycleViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daulet.lab3.R;
import com.example.daulet.lab3.models.Role;

import java.util.List;

/**
 * Created by daulet on 10/13/17.
 */

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.RolesViewHolder> {
    public class RolesViewHolder extends RecyclerView.ViewHolder {
        public TextView roleTitle;
        public TextView roleCount;
        public ImageView roleImage;

        public RolesViewHolder(View view) {
            super(view);
            roleTitle = (TextView) view.findViewById(R.id.roleTitle);
            roleCount = (TextView) view.findViewById(R.id.roleCount);
            roleImage = (ImageView) view.findViewById(R.id.roleImage);

        }
    }

    private Context mContext;
    private List<Role> rolesList;

    // constructor
    public RolesAdapter(Context mContext, List<Role> rolesList) {
        this.mContext = mContext;
        this.rolesList = rolesList;
    }

    // inflate context on creation
    @Override
    public RolesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.role_card, parent, false);
        return new RolesViewHolder(itemView);
    }

    // binding view holder
    @Override
    public void onBindViewHolder(RolesViewHolder holder, int position) {
        Role role = rolesList.get(position);
        holder.roleTitle.setText(role.getName());
        holder.roleCount.setText(role.getNumberOfHeroes() + " roles");

        // loading roles cover using Glide library
        Glide.with(mContext).load(role.getImageIndex()).into(holder.roleImage);
    }

    @Override
    public int getItemCount() {
        return rolesList.size();
    }
}
