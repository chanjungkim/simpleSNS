package org.simplesns.simplesns.main.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.R;

public class ProfileFragment extends Fragment {
    TextView tv_profile;
    ImageView iv_profile_photo;
    public static ProfileFragment newInstance() {

        // TODO Parameters

        ProfileFragment profileFragment = new ProfileFragment();
        return  profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tv_profile = (TextView) view.findViewById(R.id.tv_profile);

        // make a round shape profile photo
        iv_profile_photo = (ImageView) view.findViewById(R.id.iv_profile_photo);
        if(Build.VERSION.SDK_INT >= 21) {
            iv_profile_photo.setClipToOutline(true);
        }

        iv_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Toast.makeText(getActivity(),"TODO : 프로필 사진 바꾸기", Toast.LENGTH_SHORT).show();
            }
        });

        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Toast.makeText(getActivity(),"TODO : 회원정보 바꾸기 화면 구성", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
