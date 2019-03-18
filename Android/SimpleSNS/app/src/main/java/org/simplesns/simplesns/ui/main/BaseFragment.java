package org.simplesns.simplesns.ui.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.simplesns.simplesns.R;

public class BaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.simpleSNS.simpleSNS.argsInstance";
    public static final String TAG = BaseFragment.class.getSimpleName();

    Button btn;
    public FragmentNavigation mFragmentNavigation;
    public int mInt = 0;
    private View cachedView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, container.getId() + "");
        if (cachedView == null) {
            cachedView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        return cachedView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }
}
