package org.simplesns.simplesns.ui.main.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.item.FeedResult;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.home.adapter.HomeAdapter;
import org.simplesns.simplesns.ui.main.search.adapter.FeedAdapter;
import org.simplesns.simplesns.ui.main.search.model.FeedRecommendResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6155
 */
public class FeedRecommendFragment extends BaseFragment {
    private static String TAG = "HomeFragment";

    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    private PullRefreshLayout prlRefresh;
    long lastFeedNum = -1;
    String thisUsername = "test";

    public static FeedRecommendFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FeedRecommendFragment fragment = new FeedRecommendFragment();
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

        Timber.d("onCreateView");
        View view = inflater.inflate(R.layout.fragment_feed_recommend, container, false);

        // TODO RecyclerView
        recyclerView = view.findViewById(R.id.view_home);
        prlRefresh = view.findViewById(R.id.prl_refresh);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        feedAdapter = new FeedAdapter(getActivity());
        getFeedItems(lastFeedNum);
        prlRefresh.setOnRefreshListener(() -> {
            prlRefresh.postDelayed(() -> {
                feedAdapter.removeList();
                getFeedItems(lastFeedNum);
                prlRefresh.setRefreshing(false);
            }, 1000);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
    }

    private void getFeedItems(long lastFeedNum) {
        Timber.d("getFeedItems()");

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        // 구현해야할 부분
        Call<FeedRecommendResult> call = remoteService.getFeedRecommendItems(lastFeedNum, thisUsername);

        call.enqueue(new Callback<FeedRecommendResult>() {
            @Override
            public void onResponse(Call<FeedRecommendResult> call, Response<FeedRecommendResult> response) {
                FeedRecommendResult result = response.body();

                switch (result.code) {
                    case 200:
                        Timber.d(result.message);
                        ArrayList<FeedItem> feedList = result.data;
                        recyclerView.setAdapter(feedAdapter);
                        feedAdapter.setItemList(feedList);
                        break;
                    default:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FeedRecommendResult> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}