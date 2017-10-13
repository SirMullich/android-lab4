package com.example.daulet.lab3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daulet.lab3.R;
import com.example.daulet.lab3.models.Role;
import com.example.daulet.lab3.recycleViewAdapters.RolesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daulet on 10/13/17.
 */

public class RoleFragment extends Fragment {

    private RecyclerView recyclerView;
    private RolesAdapter adapter;
    private List<Role> roleList;

    public RoleFragment() {

    }

    private void addRole(Role newRole) {
        roleList.add(newRole);
    }

    private void defaultRoles() {

        // add default roles
        addRole(new Role("Carry", 8, R.drawable.antimage_full));
        addRole(new Role("Disabler", 9, R.drawable.disabler));
        addRole(new Role("Lane Support", 5, R.drawable.lane_support));
        addRole(new Role("Initiator", 12, R.drawable.initiator));
        addRole(new Role("Jungler", 7, R.drawable.jungler));
        addRole(new Role("Support", 12, R.drawable.support));
        addRole(new Role("Durable", 6, R.drawable.durable));
        addRole(new Role("Nuker", 16, R.drawable.nuker));
        addRole(new Role("Pusher", 19, R.drawable.pusher));
        addRole(new Role("Escape", 13, R.drawable.escape));

        // notify about all changes
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_roles, container, false);
        // Inflate the layout for this fragment

        recyclerView = view.findViewById(R.id.roles_view);

        roleList = new ArrayList<>();
        adapter = new RolesAdapter(this.getContext(), roleList);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        defaultRoles();

        return view;
    }


}
