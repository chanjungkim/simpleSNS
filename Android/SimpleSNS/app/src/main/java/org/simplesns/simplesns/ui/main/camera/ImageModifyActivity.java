package org.simplesns.simplesns.ui.main.camera;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.adapter.ImageModifyAdapter;
import org.simplesns.simplesns.ui.main.camera.customview.ImageModifyView;
import org.simplesns.simplesns.ui.main.camera.model.ImageModifyMenu;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;

import java.util.LinkedList;
import java.util.List;

public class ImageModifyActivity extends AppCompatActivity implements View.OnClickListener {
    String file_path;
    ImageView mainView;
    TabHost tabHost;
    TextView tv_next;
    ImageButton btn_back;
    RecyclerView rvImageModifyMenu;
    ImageModifyAdapter adapter;
    List <ImageModifyMenu> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_modify);
        initView();
    }

    public void initView () {
        mainView  = findViewById (R.id.image_modify_view);
        tabHost   = findViewById (R.id.image_modify_tabs);
        tv_next   = findViewById (R.id.tv_next);
        btn_back  = findViewById (R.id.btn_back);
        rvImageModifyMenu = findViewById(R.id.rv_image_modify);

        tabHost.setup();

        tv_next.setText(getString(R.string.menu_next));
        tv_next.setVisibility(View.VISIBLE);

        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.rv_image_modify) ;
        ts1.setIndicator(this.getString(R.string.image_modify_filter)) ;
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.shape_modify_menu_list) ;
        ts2.setIndicator(this.getString(R.string.image_modify_shape)) ;
        tabHost.addTab(ts2);

        // init main view
        mainView.setImageBitmap(ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()));

        getImageModfyMenu();

        // set recycler view
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(ImageModifyActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvImageModifyMenu.setLayoutManager(horizontalLayoutManager);
        adapter = new ImageModifyAdapter(this, list);
        rvImageModifyMenu.setAdapter(adapter);

        getImageModfyMenu();

        // set event
        findViewById(R.id.modify_bright).setOnClickListener(this);
        findViewById(R.id.modify_contrast).setOnClickListener(this);
        findViewById(R.id.modify_awb).setOnClickListener(this);
        findViewById(R.id.modify_saturation).setOnClickListener(this);
        findViewById(R.id.modify_color).setOnClickListener(this);
        findViewById(R.id.modify_cloudy).setOnClickListener(this);
        findViewById(R.id.modify_highlight).setOnClickListener(this);
        findViewById(R.id.modify_blur).setOnClickListener(this);
        findViewById(R.id.modify_miniature).setOnClickListener(this);
        findViewById(R.id.modify_structure).setOnClickListener(this);


        tv_next.setOnClickListener(v->{
            Intent image_post_intent = new Intent(ImageModifyActivity.this, ImagePostActivity.class);
            startActivity(image_post_intent);
        });

        btn_back.setOnClickListener(v->{
            finish();
        });
    }

    private void getImageModfyMenu () {
        list = new LinkedList<>();
        list.add(new ImageModifyMenu("Normal", ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()), 1));
        list.add(new ImageModifyMenu("Moon", ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()), 2));
        list.add(new ImageModifyMenu("Clarendon", ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()), 3));
        list.add(new ImageModifyMenu("Lark", ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()), 4));
        list.add(new ImageModifyMenu("Gingham", ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()), 5));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_bright: {
                Toast.makeText(this,"밝기",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_contrast: {
                Toast.makeText(this,"대비",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_awb: {
                Toast.makeText(this,"온도",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_saturation: {
                Toast.makeText(this,"채도",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_color: {
                Toast.makeText(this,"색깔",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_cloudy: {
                Toast.makeText(this,"배경 흐리게",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_highlight: {
                Toast.makeText(this,"강조",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_blur: {
                Toast.makeText(this,"흐리게",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_miniature: {
                Toast.makeText(this,"미니어쳐",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }
            case R.id.modify_structure: {
                Toast.makeText(this,"구조",Toast.LENGTH_SHORT).show();
                // todo delete message and add image process function
                break;
            }

        }
    }
}
