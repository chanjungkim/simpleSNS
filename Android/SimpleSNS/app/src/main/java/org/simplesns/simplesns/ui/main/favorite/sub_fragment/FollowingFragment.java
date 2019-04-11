package org.simplesns.simplesns.ui.main.favorite.sub_fragment;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.adapter.FollowingAdapter;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.model.FollowingActivityResult;

public class FollowingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static String TAG = FollowingFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeLayout;
    RecyclerView rlFollowingList;

    public static FollowingFragment newInstance() {
        // TODO Parameters
        return new FollowingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        swipeLayout = view.findViewById(R.id.following_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rlFollowingList = view.findViewById(R.id.rv_following_list);


        getFollowingActivityList(GlobalUser.getInstance().getMyId());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "Refreshing...");
        new Handler().postDelayed(() -> swipeLayout.setRefreshing(false), 2000);
    }

    void getFollowingActivityList(String username) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<FollowingActivityResult> call = remoteService.getFollowingActivitiesWithoutMe(username);

        call.enqueue(new Callback<FollowingActivityResult>() {
            @Override
            public void onResponse(Call<FollowingActivityResult> call, Response<FollowingActivityResult> response) {
                FollowingActivityResult result = response.body();

                // 내가 팔로우 하고 있는 사람들의 게시물, 좋아요, 댓글에 대한 정보를 시간 순서대로 가져온다.
                switch (result.code) {
                    case 200:
                        rlFollowingList.setLayoutManager(new LinearLayoutManager(getActivity()));
                        FollowingAdapter followingAdapter = new FollowingAdapter(getActivity());
                        followingAdapter.setDatalist(result.data);
                        rlFollowingList.setAdapter(followingAdapter);
                        break;
                    case 403:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FollowingActivityResult> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
