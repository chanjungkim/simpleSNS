package org.simplesns.simplesns.ui.main.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileBadukFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileLineFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileTagFragment;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6657
 */
public class ProfileFragment extends BaseFragment {
    TextView tvProfileChange;             // 프로필 내용 수정 관련
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

    public static ProfileFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        ProfileFragment fragment = new ProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfileChange = view.findViewById(R.id.tv_profile_change);
        ivGrid = view.findViewById(R.id.iv_grid);
        ivList = view.findViewById(R.id.iv_list);
        ivTag = view.findViewById(R.id.iv_tag);

        // TODO : 서버에서 숫자 받아오기. 클릭하면 해당 페이지로 넘어가기.
        tvPostCount = view.findViewById(R.id.tv_post_count);
        tvFollowerCount = view.findViewById(R.id.tv_follower_count);
        tvFollowingCount = view.findViewById(R.id.tv_following_count);

        tvUsername = view.findViewById(R.id.tv_username);
        tvUsername.setText(GlobalUser.getInstance().getMyId());

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


        ivProfilePhoto = view.findViewById(R.id.iv_profile_photo);


        ivProfilePhoto.setOnClickListener(v ->

        {
            // TODO : 서버에서 사진 받아와서 띄워놓기, 클릭하면 카메라로 이동하기
            Toast.makeText(getActivity(), "TODO : 프로필 사진 바꾸기", Toast.LENGTH_SHORT).show();
        });

        tvProfileChange.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProfileChangeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}