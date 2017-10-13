package com.example.daulet.lab3.recycleViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daulet.lab3.HeroByIdActivity;
import com.example.daulet.lab3.R;
import com.example.daulet.lab3.models.Hero;

import java.util.List;

/**
 * Created by daulet on 10/13/17.
 */

public class HerosAdapter extends RecyclerView.Adapter<HerosAdapter.HeroesViewHolder> {
    private Context mContext;
    private List<Hero> heroesList;

    // View Holder for heroes
    public class HeroesViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView body;
        int position;

        public HeroesViewHolder(View view, final Context event) {
            super(view);
            title = (TextView) view.findViewById(R.id.heroTitles);
            body = (TextView) view.findViewById(R.id.heroBody);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(event, HeroByIdActivity.class);
                    intent.putExtra("news-item", (Parcelable) heroesList.get(position));
                    event.startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }



    public HerosAdapter(Context mContext, List<Hero> heroList){
        this.mContext = mContext;
        this.heroesList = heroList;
    }

    @Override
    public HeroesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_card, parent, false);
        return new HeroesViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(HeroesViewHolder holder, int position) {
        holder.setPosition(position);
        holder.title.setText(heroesList.get(position).getName());
        holder.body.setText(heroesList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }


    // new item in position is inserted
    public void insert(int position, Hero news) {
        heroesList.add(position, news);
        notifyItemInserted(position);
    }

    // remove item by position
    public void remove(int position) {
        heroesList.remove(position);
        notifyItemRemoved(position);
    }



}
