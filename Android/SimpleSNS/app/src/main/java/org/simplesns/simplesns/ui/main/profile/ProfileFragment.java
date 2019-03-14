package org.simplesns.simplesns.ui.main.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileBadukFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileLineFragment;
import org.simplesns.simplesns.ui.main.profile.fragment.ProfileTagFragment;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=6657
 */
public class ProfileFragment extends Fragment {
    TextView tvProfileChange;             // 프로필 내용 수정 관련
    ImageView ivProfilePhoto;             // 프로필 이미지 수정 관련
    ImageView button_baduk;
    ImageView button_line;
    ImageView button_tag;

    ProfileBadukFragment profileBadukFragment;
    ProfileLineFragment profileLineFragment;
    ProfileTagFragment profileTagFragment;
    FragmentManager fragmentManager;

    TextView tvPostCount;
    TextView tvFollowerCount;
    TextView tvFollowingCount;

    TextView tvUsername;
    ImageView btnSetting;

    public static ProfileFragment newInstance() {

        // TODO Parameters

        ProfileFragment profileFragment = new ProfileFragment();
        return  profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfileChange = (TextView) view.findViewById(R.id.tv_profile_change);
        button_baduk = (ImageView) view.findViewById(R.id.button_baduk);
        button_line = (ImageView) view.findViewById(R.id.button_line);
        button_tag = (ImageView) view.findViewById(R.id.button_tag);

        // TODO : 서버에서 숫자 받아오기. 클릭하면 해당 페이지로 넘어가기.
        tvPostCount = (TextView) view.findViewById(R.id.tv_post_count);
        tvFollowerCount = (TextView) view.findViewById(R.id.tv_follower_count);
        tvFollowingCount = (TextView) view.findViewById(R.id.tv_following_count);

        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        tvUsername.setText(GlobalUser.getInstance().getMyId());

        btnSetting = (ImageView) view.findViewById(R.id.btn_setting);

        profileBadukFragment = new ProfileBadukFragment();
        profileLineFragment = new ProfileLineFragment();
        profileTagFragment = new ProfileTagFragment();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileBadukFragment).commit();

        button_baduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileBadukFragment).commit();
            }
        });

        button_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileLineFragment).commit();
            }
        });

        button_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.profile_container, profileTagFragment).commit();
            }
        });


        ivProfilePhoto = (ImageView) view.findViewById(R.id.iv_profile_photo);


        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 서버에서 사진 받아와서 띄워놓기, 클릭하면 카메라로 이동하기
                Toast.makeText(getActivity(),"TODO : 프로필 사진 바꾸기", Toast.LENGTH_SHORT).show();
            }
        });

        tvProfileChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileChangeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Setting 페이지로 이동/로그아웃 구현", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
