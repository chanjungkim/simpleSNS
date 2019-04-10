package org.simplesns.simplesns.ui.main.search;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;
import com.baoyz.widget.PullRefreshLayout;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.item.FeedResult;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.search.adapter.CategoryAdapter;
import org.simplesns.simplesns.ui.main.search.adapter.RecommendAdapter;
import org.simplesns.simplesns.ui.main.search.model.CategoryItem;
import org.simplesns.simplesns.ui.main.search.model.GridRecommendResult;

import java.util.ArrayList;

public class RecommendFragment extends BaseFragment {
    private RecyclerView rvRecommend;
    private PullRefreshLayout prlRefresh;
    private RecyclerView rvCategory;
    private RecommendAdapter recommendAdapter;
    private CategoryAdapter categoryAdapter;

    long lastFeedNum = -1;
    SpannedGridLayoutManager spannedGridLayoutManager;

    public static RecommendFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
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

        hideKeyboard(getActivity());

        View view = inflater.inflate(R.layout.fragment_recommend, container, false);

        RelativeLayout toolbar = view.findViewById(R.id.tb_recommend);
        rvRecommend = view.findViewById(R.id.rv_recommend);
        prlRefresh = view.findViewById(R.id.prl_refresh);
        rvCategory = view.findViewById(R.id.rv_category);

        categoryAdapter = new CategoryAdapter(getActivity());
        recommendAdapter = new RecommendAdapter(getActivity(), this);

        toolbar.setOnClickListener((v) -> {
            if (mFragmentNavigation != null) {
                mFragmentNavigation.pushFragment(SearchFragment.newInstance(mInt + 1));
            }
        });

        getCategoryItems();
        getRecommendItems(lastFeedNum);
        spannedGridLayoutManager = new SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 3);
        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                if (position % 7 == 1) {
                    return new SpanSize(2, 2);
                } else {
                    return new SpanSize(1, 1);
                }
            }
        }));

        prlRefresh.setOnRefreshListener(() -> {
            prlRefresh.postDelayed(() -> {
                recommendAdapter.removeList();
                getRecommendItems(lastFeedNum);
                prlRefresh.setRefreshing(false);
            }, 1000);
        });

        return view;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create profile_a new one, just so we can grab profile_a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getRecommendItems(long lastFeedNum) {
        Timber.d("getFeedItems()");

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<GridRecommendResult> call = remoteService.getRecommendItemsFromServer(GlobalUser.getInstance().getMyId(), lastFeedNum);

        call.enqueue(new Callback<GridRecommendResult>() {
            @Override
            public void onResponse(Call<GridRecommendResult> call, Response<GridRecommendResult> response) {
                GridRecommendResult result = response.body();

                switch (result.code) {
                    case 200:
                        Timber.d(result.message);
                        ArrayList<FeedItem> feedList = result.data;
                        rvRecommend.setAdapter(recommendAdapter);
                        rvRecommend.setLayoutManager(spannedGridLayoutManager);
                        recommendAdapter.setItemList(feedList);
                        break;
                    default:
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GridRecommendResult> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getCategoryItems() {
        Timber.d("getCategoryItems");
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        // 파팅 - 카테고리별 최신 이미지로 가져와야함.
        ArrayList<CategoryItem> categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("추천", getActivity().getResources().getDrawable(R.drawable.profile_a), true));
        categoryList.add(new CategoryItem("스타일", getActivity().getResources().getDrawable(R.drawable.profile_b), false));
        categoryList.add(new CategoryItem("피트니스", getActivity().getResources().getDrawable(R.drawable.profile_c), false));
        categoryList.add(new CategoryItem("만화", getActivity().getResources().getDrawable(R.drawable.profile_a), false));
        categoryList.add(new CategoryItem("스포츠", getActivity().getResources().getDrawable(R.drawable.profile_b), false));
        categoryList.add(new CategoryItem("TV 및 영화", getActivity().getResources().getDrawable(R.drawable.profile_c), false));
        categoryList.add(new CategoryItem("예술", getActivity().getResources().getDrawable(R.drawable.profile_a), false));
        categoryList.add(new CategoryItem("미용", getActivity().getResources().getDrawable(R.drawable.profile_b), false));
        categoryList.add(new CategoryItem("음악", getActivity().getResources().getDrawable(R.drawable.profile_c), false));
        categoryList.add(new CategoryItem("장식", getActivity().getResources().getDrawable(R.drawable.profile_a), false));
        categoryList.add(new CategoryItem("DIY", getActivity().getResources().getDrawable(R.drawable.profile_b), false));
        categoryList.add(new CategoryItem("동물", getActivity().getResources().getDrawable(R.drawable.profile_c), false));
        categoryList.add(new CategoryItem("음식", getActivity().getResources().getDrawable(R.drawable.profile_a), false));
        categoryList.add(new CategoryItem("쇼핑", getActivity().getResources().getDrawable(R.drawable.profile_b), false));

        categoryAdapter.setItemList(categoryList);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false))
        ;
        rvCategory.setAdapter(categoryAdapter);
    }
}