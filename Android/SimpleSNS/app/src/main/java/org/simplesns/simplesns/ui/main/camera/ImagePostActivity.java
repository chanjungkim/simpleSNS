package org.simplesns.simplesns.ui.main.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.MainActivity;
import org.simplesns.simplesns.ui.main.camera.model.ImagePostResult;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;

import java.io.File;
import java.util.List;
import java.util.Locale;

// reference
// https://blog.naver.com/nakim02/221409751574
public class ImagePostActivity extends AppCompatActivity implements LocationListener {
    String TAG = "ImagePostActivity";

    public static final int REQUEST_LOCATION = 1;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 10;
    private LocationManager mLocationManager = null;

    String gps1 = "777";
    String gps2 = "777";
    String gps3 = "777";

    TextView tv_location;
    TextView tv_next;
    ImageButton btn_back;
    ImageView imageViewThumbnail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post);

        initView ();

        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission dialog
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            // permission granted
            mLocationManager = (LocationManager) getSystemService (Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this
                    , Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG,"위치정보가 허용되지 않습니다");
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }

        tv_next.setOnClickListener(v->{
            sendFiles ();
        });

        btn_back.setOnClickListener(v->{
            finish();
        });

    }

    // multipart
    private void sendFiles () {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), ImageUtil.pFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", ImageUtil.pFile.getName(), requestFile);
        Call<ImagePostResult> call = remoteService.uploadFeedImage(body);
        // todo add user id (uid)
        // todo add file type (image, video)
        // todo add location string
        // todo add title string
        // todo add image size ???

        try {
            call.enqueue(new Callback<ImagePostResult>() {
                @Override
                public void onResponse(Call<ImagePostResult> call, Response<ImagePostResult> response) {
                    ImagePostResult imagePostResult = response.body();
                    if (imagePostResult != null) {
                        switch (imagePostResult.code) {
                            case 100:   // 성공
                                String image_url = imagePostResult.result;
                                Toast toast = Toast.makeText(ImagePostActivity.this, image_url, Toast.LENGTH_SHORT); // todo change comment
                                toast.setGravity(Gravity.TOP, 0, 120);
                                toast.show();
                                break;
                            case 400 :  // 실패
                                break;
                            default:
                                Log.d(TAG, imagePostResult.code + "");
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ImagePostResult> call, Throwable t) {
                    Toast.makeText(ImagePostActivity.this, "fail", Toast.LENGTH_SHORT).show(); // todo change comment
                }
            });
        } catch (Exception e) {

        } finally {
            goToMainActivity ();
        }
    }

    private void goToMainActivity () {
        Intent home_intent = new Intent(ImagePostActivity.this, MainActivity.class);
        home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   // activity stack clear
        startActivity(home_intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
    }

    private void initView () {
        tv_location = findViewById (R.id.tv_location);
        tv_next     = findViewById (R.id.tv_next);
        btn_back    = findViewById (R.id.btn_back);
        imageViewThumbnail = findViewById (R.id.iv_thumbnail);

        tv_next.setText(getString(R.string.menu_share));
        tv_next.setVisibility(View.VISIBLE);

        imageViewThumbnail.setImageBitmap(
                ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged" + location.toString());

        double lat = location.getLatitude();    // 위도
        double lng = location.getLongitude();   // 경도

        gps1 = String.valueOf(lat);
        gps2 = String.valueOf(lng);
        gps3 = getAddress(lat, lng);
        Toast.makeText(this, gps3+"", Toast.LENGTH_SHORT).show(); // 코린이 - issue : location manager 가 불렸다가 안불렀다함
        tv_location.setText(gps3);
    }

    public String getAddress (double lat, double lng) {
        String address = null;
        Geocoder geocoder = new Geocoder (this, Locale.getDefault());

        List<Address> list = null;

        try {
            list = geocoder.getFromLocation (lat, lng, 1);
        } catch (Exception e) {
            Log.e(TAG,"location list error");
        }

        if (list == null) {
            Log.e(TAG,"location list null");
            return null;
        }

        if (list.size() > 0) {
           Address addr = list.get(0);
           if (addr.getCountryName().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*]") || addr.getCountryName().matches("[]\\u31C0-\\u31EF]")) {
               address = addr.getCountryName() + " " + addr.getLocality() + " "
                       + addr.getSubLocality() + " " + addr.getThoroughfare() + " ";
           } else {
               address = addr.getThoroughfare() + ", " + addr.getSubLocality() + ", "
                       + addr.getLocality() + ", " + addr.getCountryName();
           }
        }
        return address;
    }
}
