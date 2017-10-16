package com.example.daulet.lab3.fragments;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.daulet.lab3.R;
import com.example.daulet.lab3.api.Client;
import com.example.daulet.lab3.api.Interface;
import com.example.daulet.lab3.models.Hero;
import com.example.daulet.lab3.recycleViewAdapters.HerosAdapter;
import com.example.daulet.lab3.repository.RoomDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by daulet on 10/13/17.
 */

public class HeroFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private HerosAdapter adapter;
    private List<Hero> newsList;
    private AlertDialog.Builder alertDialog;
    private boolean add = false;

    RoomDb database;

    View view;

    // Empty public constructor
    public HeroFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Room.databaseBuilder(getActivity().getApplicationContext(), RoomDb.class, "Room.db").build();
    }

    public void initialize(){
        initDialog();
        initSwipe();
        GetHeros();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_heros, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_heroes);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_hero);
        fab.setOnClickListener(this);
        initialize();
        return rootView;
    }

    private void initDialog(){
        alertDialog = new AlertDialog.Builder(getActivity());
        view = getLayoutInflater().inflate(R.layout.hero_create, null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(add) {
                    add = false;
                    EditText titel = (EditText) view.findViewById(R.id.create_hero_name);
                    EditText body = (EditText) view.findViewById(R.id.create_hero_description);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    Hero crNews = new Hero(titel.getText().toString(), body.getText().toString(), formattedDate);
                    adapter.insert(newsList.size(), crNews);
                    AddNews(crNews);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    DeleteNews(newsList.get(position));
                    adapter.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public void GetHeros(){
        try {

            Interface apiService = Client.getClient().create(Interface.class);
            Call<List<Hero>> call = apiService.getHerosAPIList();
            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                    Log.e("Response", response.body().toString());
                    List<Hero> newHerosList = response.body();
                    setToRecyclerView(newHerosList);
                    new InsertListAsync().execute(newHerosList);
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    Log.e("Error", t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            new GetHeroesAsync().execute();
        }

    }

    public void AddNews(Hero hero){
        new InsertAsync().execute(hero);
    }

    public void DeleteNews(Hero hero){
        new DeleteAsync().execute(hero);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_hero:
                removeView();
                add = true;
                alertDialog.setTitle("Add Hero");
                alertDialog.show();
                break;
        }
    }

    public void SyncWithAPI(List<Hero> heroList){
        Interface apiService = Client.getClient().create(Interface.class);
        Call<List<Hero>> postCreateCall = apiService.createHeroesAPIBlob();

        postCreateCall.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> newsListAPI = response.body();
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

            }
        });
    }


    private class GetHeroesAsync extends AsyncTask<Void, Void, List <Hero>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List <Hero> doInBackground(Void... voids) {
            return database.newsDao().getAll();
        }

        @Override
        protected void onPostExecute(List <Hero> myList) {
            super.onPostExecute(myList);
            setToRecyclerView(myList);
        }
    }

    void setToRecyclerView(List <Hero> myList) {
        newsList = myList;
        adapter = new HerosAdapter(this.getContext(), newsList);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private class InsertListAsync extends AsyncTask<List<Hero>, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(List<Hero> ... crHero) {
            for (Hero nItem : crHero[0]) {
                database.newsDao().insert(nItem);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class InsertAsync extends AsyncTask<Hero, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Hero ... crNews) {
            database.newsDao().insert(crNews[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private  class DeleteAsync extends AsyncTask<Hero, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Hero ... crNews) {
            database.newsDao().delete(crNews[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
