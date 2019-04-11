package org.simplesns.simplesns.ui.main.search;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileGridFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileListFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileTagFragment;
import org.simplesns.simplesns.ui.main.search.adapter.FeedAdapter;
import org.simplesns.simplesns.ui.main.search.model.FollowResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6657
 */
public class FeedProfileFragment extends BaseFragment {
    private static final String TAG = FeedProfileFragment.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView tvFollowButton;
    ImageView ivProfilePhoto;
    ImageView ivGrid;
    ImageView ivList;
    ImageView ivTag;

    ProfileGridFragment profileGridFragment;
    ProfileListFragment profileListFragment;
    ProfileTagFragment profileTagFragment;
    FragmentManager fragmentManager;

    TextView tvPostCount;
    TextView tvFollowerCount;
    TextView tvFollowingCount;
    TextView tvUsername;
    PullRefreshLayout prlRefresh;

    String thisUsername = "no username";
    boolean isFollowing = true;
    boolean isHeFollowingMe = true;

    public static FeedProfileFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FeedProfileFragment fragment = new FeedProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_feed_profile, container, false);

        tvFollowButton = view.findViewById(R.id.tv_follow_button);
        ivGrid = view.findViewById(R.id.iv_grid);
        ivList = view.findViewById(R.id.iv_list);
        ivTag = view.findViewById(R.id.iv_tag);
        prlRefresh = view.findViewById(R.id.prl_refresh);

        // TODO : 서버에서 숫자 받아오기. 클릭하면 해당 페이지로 넘어가기.
        tvPostCount = view.findViewById(R.id.tv_post_count);
        tvFollowerCount = view.findViewById(R.id.tv_follower_count);
        tvFollowingCount = view.findViewById(R.id.tv_following_count);
        tvUsername = view.findViewById(R.id.tv_username);

        prlRefresh.setOnRefreshListener(() -> prlRefresh.postDelayed(() -> prlRefresh.setRefreshing(false), 1000));

        thisUsername = getArguments().getString(FeedAdapter.USERNAME);
        checkFollow(GlobalUser.getInstance().getMyId(), thisUsername);
        tvUsername.setText(thisUsername);

        profileGridFragment = new ProfileGridFragment();
        profileListFragment = new ProfileListFragment();
        profileTagFragment = new ProfileTagFragment();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileGridFragment).commit();

        ivGrid.setOnClickListener(v -> {
            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.defaultBlue)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileGridFragment).commit();
        });

        ivList.setOnClickListener(v -> {
            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.defaultBlue)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileListFragment).commit();
        });

        ivTag.setOnClickListener(v -> {
            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.defaultBlue)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileTagFragment).commit();
        });

        ivProfilePhoto = view.findViewById(R.id.civ_profile_photo);

        tvFollowButton.setOnClickListener(v -> {
            Timber.d("clicked!");
            if(isFollowing){
                unfollow(GlobalUser.getInstance().getMyId(), thisUsername);
            }else{
                follow(GlobalUser.getInstance().getMyId(), thisUsername);
            }
        });

        return view;
    }

    public void checkFollow(String myUsername, String hisUsername) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<FollowResult> call = remoteService.checkFollow(myUsername, hisUsername);

        call.enqueue(new Callback<FollowResult>() {
            @Override
            public void onResponse(Call<FollowResult> call, Response<FollowResult> response) {
                FollowResult result = response.body();

                Timber.d(result.toString());
                switch (result.code){
                    case 200:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        isFollowing = true;
                        tvFollowButton.setBackground(getResources().getDrawable(R.drawable.border_rectangle));
                        tvFollowButton.setTextColor(getResources().getColor(R.color.black));
                        tvFollowButton.setText("메시지");
                        break;
                    case 201:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        isFollowing = false;
                        break;
                    case 202:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        tvFollowButton.setText("맞팔로우하기");
                        isHeFollowingMe = true;
                        break;
                    case 403:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FollowResult> call, Throwable throwable) {
                Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    public void follow(String myUsername, String hisUsername) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<FollowResult> call = remoteService.insertFollow(myUsername, hisUsername);

        call.enqueue(new Callback<FollowResult>() {
            @Override
            public void onResponse(Call<FollowResult> call, Response<FollowResult> response) {
                FollowResult result = response.body();

                Timber.d(result.toString());
                switch (result.code){
                    case 200:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        isFollowing = true;
                        tvFollowButton.setBackground(getResources().getDrawable(R.drawable.border_rectangle));
                        tvFollowButton.setTextColor(getResources().getColor(R.color.black));
                        tvFollowButton.setText("메시지");
                        break;
                    case 201:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        // 채팅 화면
                        break;
                    case 403:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FollowResult> call, Throwable throwable) {
                Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    public void unfollow(String myUsername, String hisUsername) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<FollowResult> call = remoteService.deleteFollow(myUsername, hisUsername);

        call.enqueue(new Callback<FollowResult>() {
            @Override
            public void onResponse(Call<FollowResult> call, Response<FollowResult> response) {
                FollowResult result = response.body();

                Timber.d(result.toString());
                switch (result.code){
                    case 200:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FollowResult> call, Throwable throwable) {
                Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }
}