package org.simplesns.simplesns.activity.main.profile;

import android.content.Intent;
import android.os.Build;
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

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.profile.fragment.ProfileBadukFragment;
import org.simplesns.simplesns.activity.main.profile.fragment.ProfileLineFragment;
import org.simplesns.simplesns.activity.main.profile.fragment.ProfileTagFragment;

public class ProfileFragment extends Fragment {
    TextView tv_profile_change;             // 프로필 내용 수정 관련
    ImageView iv_profile_photo;             // 프로필 이미지 수정 관련
    ImageView button_baduk;
    ImageView button_line;
    ImageView button_tag;

    ProfileBadukFragment profileBadukFragment;
    ProfileLineFragment profileLineFragment;
    ProfileTagFragment profileTagFragment;
    FragmentManager fragmentManager;

    public static ProfileFragment newInstance() {

        // TODO Parameters

        ProfileFragment profileFragment = new ProfileFragment();
        return  profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tv_profile_change = (TextView) view.findViewById(R.id.tv_profile_change);
        button_baduk = (ImageView) view.findViewById(R.id.button_baduk);
        button_line = (ImageView) view.findViewById(R.id.button_line);
        button_tag = (ImageView) view.findViewById(R.id.button_tag);

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


        iv_profile_photo = (ImageView) view.findViewById(R.id.iv_profile_photo);


        iv_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Toast.makeText(getActivity(),"TODO : 프로필 사진 바꾸기", Toast.LENGTH_SHORT).show();
            }
        });

        tv_profile_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileChangeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
