package org.simplesns.simplesns.main.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.main.Adapter.HomeAdapter;
import org.simplesns.simplesns.main.Model.Data;
import org.simplesns.simplesns.main.Model.Images;
import org.simplesns.simplesns.main.Model.User;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static String TAG = "HomeFragment";

    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    public static HomeFragment newInstance() {

        // TODO Parameters

        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeLayout = view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // TODO RecyclerView
        recyclerView = view.findViewById(R.id.view_home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);
        insertDummyData();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    private void insertDummyData() {
        Log.d(TAG, "insertDummyData()");

        ArrayList<Data> feedData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Data data = new Data();
            Images images = new Images();
            images.setUrl("https://source.unsplash.com/random");
            data.setUser(new User(null,
                    "TestID::" + i,
                    null,
                    "https://picsum.photos/200/300/?random",
                    null,
                    0,
                    0,
                    0));
            data.setImages(images);
            feedData.add(data);
        }
        homeAdapter.addItem(feedData);
    }

    @Override public void onRefresh() {
        Log.d(TAG, "Refreshing...");
        new Handler().postDelayed(() -> swipeLayout.setRefreshing(false), 2000);
    }
}
