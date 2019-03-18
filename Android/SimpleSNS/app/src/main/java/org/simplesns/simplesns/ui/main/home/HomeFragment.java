package org.simplesns.simplesns.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedImageItem;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.home.adapter.HomeAdapter;

import java.util.ArrayList;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6155
 */
public class HomeFragment extends BaseFragment {
    private static String TAG = "HomeFragment";

    private LinearLayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout prlRefresh;

    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO RecyclerView
        recyclerView = view.findViewById(R.id.view_home);
        prlRefresh = view.findViewById(R.id.prl_refresh);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);
        insertDummyData();


        prlRefresh.setOnRefreshListener(() -> prlRefresh.postDelayed(() -> prlRefresh.setRefreshing(false), 1000));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    private void insertDummyData() {
        Log.d(TAG, "insertDummyData()");

        ArrayList<FeedItem> feedList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FeedItem feedItem = new FeedItem();
            FeedImageItem images = new FeedImageItem();
            images.setUrl("https://source.unsplash.com/random");
            feedItem.setUser(new MemberItem(null,
                    "TestID::" + i,
                    null,
                    "https://picsum.photos/200/300/?random",
                    null,
                    0,
                    0,
                    0));
            feedItem.setImages(images);
            feedList.add(feedItem);
        }
        homeAdapter.addItem(feedList);
    }

}
