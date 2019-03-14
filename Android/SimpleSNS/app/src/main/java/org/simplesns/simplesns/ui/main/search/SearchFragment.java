package org.simplesns.simplesns.ui.main.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.BaseFragment;

public class SearchFragment extends BaseFragment {

    public static SearchFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RelativeLayout toolbar = view.findViewById(R.id.tb_search);
        EditText etSearch = view.findViewById(R.id.et_search);
        ImageView ivBackButton = view.findViewById(R.id.iv_back_button);

        ivBackButton.setOnClickListener((v) -> {
            getActivity().onBackPressed();
        });

        etSearch.post(new Runnable() {
            @Override
            public void run() {
                etSearch.setFocusableInTouchMode(true);
                etSearch.requestFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.showSoftInput(etSearch, 0);

            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CharSequence keyword = etSearch.getText();
                if (TextUtils.isEmpty(keyword)) {
                    getSearchResult(keyword.toString());
                }
                return true;
            }
            return false;
        });

        return view;
    }

    public void getSearchResult(String keyword) {
        // 구현
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
