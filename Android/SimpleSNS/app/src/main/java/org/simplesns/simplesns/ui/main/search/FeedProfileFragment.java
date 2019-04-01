package org.simplesns.simplesns.ui.main.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.profile.ProfileChangeActivity;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileBadukFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileLineFragment;
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

import static android.app.Activity.RESULT_OK;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6657
 */
public class FeedProfileFragment extends BaseFragment {
    private static final String TAG = FeedProfileFragment.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView tvFollowButton;             // 프로필 내용 수정 관련
    ImageView ivProfilePhoto;             // 프로필 이미지 수정 관련
    ImageView ivGrid;
    ImageView ivList;
    ImageView ivTag;

    ProfileBadukFragment profileGridFragment;
    ProfileLineFragment profileListFragment;
    ProfileTagFragment profileTagFragment;
    FragmentManager fragmentManager;

    TextView tvPostCount;
    TextView tvFollowerCount;
    TextView tvFollowingCount;
    TextView tvUsername;
    PullRefreshLayout prlRefresh;

    String thisUsername = "no username";

    public static FeedProfileFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FeedProfileFragment fragment = new FeedProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
        RelativeLayout relativeLayout = view.findViewById(R.id.rl_profile_photo_container);

        prlRefresh.setOnRefreshListener(() -> prlRefresh.postDelayed(() -> prlRefresh.setRefreshing(false), 1000));

        tvUsername = view.findViewById(R.id.tv_username);

        thisUsername = getArguments().getString(FeedAdapter.USERNAME);
        tvUsername.setText(thisUsername);

        profileGridFragment = new ProfileBadukFragment();
        profileListFragment = new ProfileLineFragment();
        profileTagFragment = new ProfileTagFragment();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileGridFragment).commit();

        ivGrid.setOnClickListener(v -> {
            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.default_blue)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileGridFragment).commit();
        });

        ivList.setOnClickListener(v -> {
            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.default_blue)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileListFragment).commit();
        });

        ivTag.setOnClickListener(v -> {

            ViewCompat.setBackgroundTintList(ivGrid, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivList, ColorStateList.valueOf(getResources().getColor(R.color.grey2)));
            ViewCompat.setBackgroundTintList(ivTag, ColorStateList.valueOf(getResources().getColor(R.color.default_blue)));

            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileTagFragment).commit();
        });


        ivProfilePhoto = view.findViewById(R.id.civ_profile_photo);
        RelativeLayout rlProfilePhotoContainer = view.findViewById(R.id.rl_profile_photo_container);
        rlProfilePhotoContainer.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        tvFollowButton.setOnClickListener(v -> {
            follow(GlobalUser.getInstance().getMyId(), thisUsername);
        });

        return view;
    }

    public void follow(String myUsername, String hisUsername){
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<FollowResult> call = remoteService.insertFollow(myUsername, hisUsername);

        call.enqueue(new Callback<FollowResult>() {
            @Override
            public void onResponse(Call<FollowResult> call, Response<FollowResult> response) {

            }

            @Override
            public void onFailure(Call<FollowResult> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivProfilePhoto.setImageBitmap(imageBitmap);
        }
    }
}