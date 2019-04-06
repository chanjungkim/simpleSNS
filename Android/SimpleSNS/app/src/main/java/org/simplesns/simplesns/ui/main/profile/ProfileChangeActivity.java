package org.simplesns.simplesns.ui.main.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.ChangeProfileItem;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.profile.model.CheckUsernameResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileChangeResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;
import org.simplesns.simplesns.ui.sign.FirstActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
폴더를 어떻게 구성하는건지 몰라서 일단 밖으로 빼놓고 구현했습니다.
문제가 있을 경우 조언 부탁드립니다.

파팅 - 괜찮아 보입니다. 수정 버튼을 누르고 나서 EditText 때문에 소프트키보드가 나오는데, 안 나오게 하는 게 좋을 거 같습니다.
*/

public class ProfileChangeActivity extends AppCompatActivity {
    public static final String TAG = ProfileChangeActivity.class.getSimpleName();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView btnClose;
    ImageView btnSave;

    CircleImageView ivProfilePhoto;
    LinearLayout llProfilePhotoChange;
    //    EditText etName;
    EditText etUsername;
    EditText etIntroduction;
    EditText etEmail;
    //    EditText etPhone;
    String newUsername;
    String newIntroduction;


    boolean isUsernameOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        isUsernameOK = true;
        getUserProfileFromServer(GlobalUser.getInstance().getMyId());

//        체크나 X 버튼을 누르면 ProfileFragment.java(fragment_profile.xml) 파일로 돌아감
        btnClose = findViewById(R.id.iv_close);
        btnClose.setOnClickListener(v -> finish());

        ivProfilePhoto = findViewById(R.id.civ_profile_photo);
        llProfilePhotoChange = findViewById(R.id.ll_profile_photo_change);
        llProfilePhotoChange.setOnClickListener(v -> {

//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, 1);

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        });

        etUsername = findViewById(R.id.et_username);
        etIntroduction = findViewById(R.id.et_introduction);
        etEmail = findViewById(R.id.et_email);

        TextView tvLogout = findViewById(R.id.tv_logout);

        tvLogout.setOnClickListener(v->{
            GlobalUser.getInstance().logOut(this, FirstActivity.class);
        });

//        LinearLayout rlProfilePhotoContainer = findViewById(R.id.ll_profile_photo_change);
//        rlProfilePhotoContainer.setOnClickListener(v -> {
//            dispatchTakePictureIntent();
//        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                newUsername = etUsername.getText().toString();
                if (newUsername.equals(GlobalUser.getInstance().getMyId())) {

                } else {
                    checkUsernameFromServer(newUsername);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            if (isUsernameOK) {
                newUsername = etUsername.getText().toString();
                newIntroduction = etIntroduction.getText().toString();

                setUserProfileFromServer(GlobalUser.getInstance().getMyId(), newUsername, newIntroduction, null);
                finish();
            } else {
                Toast toast = Toast.makeText(this, "사용할 수 없는 사용자 이름입니다.\n  다른 사용자 이름을 사용하세요", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 120);
                toast.show();
            }
        });

    }

    private void getUserProfileFromServer(String username) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ProfileResult> call = remoteService.getUserProfile(username);

        try {
            call.enqueue(new Callback<ProfileResult>() {
                @Override
                public void onResponse(Call<ProfileResult> call, Response<ProfileResult> response) {
                    ProfileResult profileResult = response.body();

                    switch (profileResult.code) {
                        case 200:
                            MemberItem memberItem = profileResult.data;
                            etUsername.setText(memberItem.getUsername());
                            etEmail.setText(memberItem.getEmail());

                            String profilePhotoUrl = "http://ec2-13-124-229-143.ap-northeast-2.compute.amazonaws.com:3000" + memberItem.getPhoto_url();
                            Glide.with(getApplicationContext())
                                    .load(profilePhotoUrl)
                                    .into(ivProfilePhoto);

                            if (memberItem.getIntroduction() != null) {
                                etIntroduction.setText(memberItem.getIntroduction() + "");
                            }

                            Log.d(TAG, memberItem.toString());
                            break;
                        default:
                            Log.d(TAG, profileResult.code + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ProfileResult> call, Throwable throwable) {
                    Toast.makeText(ProfileChangeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUsernameFromServer(String newUsername) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<CheckUsernameResult> call = remoteService.checkUsername(newUsername);
        try {
            call.enqueue(new Callback<CheckUsernameResult>() {
                @Override
                public void onResponse(Call<CheckUsernameResult> call, Response<CheckUsernameResult> response) {
                    CheckUsernameResult checkUsernameResult = response.body();
                    Log.d(TAG, checkUsernameResult.toString());

                    switch (checkUsernameResult.code) {
                        case 200:
                            boolean doesUsernameExist = checkUsernameResult.result;
                            if (doesUsernameExist) {
                                Toast toast = Toast.makeText(ProfileChangeActivity.this, "사용자 이름 " + newUsername + "을(를) 사용할 수 없습니다.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 120);
                                toast.show();
                                isUsernameOK = false;
                            }
                            break;
                        default:
                            Log.d(TAG, checkUsernameResult.code + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<CheckUsernameResult> call, Throwable throwable) {
                    Toast.makeText(ProfileChangeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserProfileFromServer(String username, String newUsername, String introduction, String photo_url) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        ChangeProfileItem changeProfileItem = new ChangeProfileItem(username, newUsername, introduction, photo_url);
        Call<ProfileChangeResult> call = remoteService.setUserProfile(changeProfileItem);

        try {
            call.enqueue(new Callback<ProfileChangeResult>() {
                @Override
                public void onResponse(Call<ProfileChangeResult> call, Response<ProfileChangeResult> response) {
                    ProfileChangeResult profileChangeResult = response.body();
                    Log.d(TAG, profileChangeResult.toString());

                    switch (profileChangeResult.code) {
                        case 200:
                            Log.d(TAG, profileChangeResult.result);
                            break;
                        default:
                            Log.d(TAG, profileChangeResult.code + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ProfileChangeResult> call, Throwable throwable) {
                    Toast.makeText(ProfileChangeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            try {

                //Uri에서 이미지 이름을 얻어온다.
                String name_Str = getImageNameToUri(data.getData());

                Log.d("AAAAA", name_Str);  // 20190317_110012.jpg

                Uri selPhotoUri = data.getData();

                Log.d("AAAAA", String.valueOf(selPhotoUri)); //  content://media/external/images/media/18606

                //절대경로 획득**
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()),
                        null, null, null, null);

                Log.d("AAAAA", String.valueOf(c));  //  android.content.ContentResolver$CursorWrapperInner@dbbd835

                c.moveToNext();
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));

                Log.d("AAAAA", absolutePath); //   /storage/emulated/0/DCIM/Camera/20190317_110012.jpg

                //이미지 데이터를 비트맵으로 받아옴
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                Log.d("AAAAA", String.valueOf(image_bitmap));  //   android.graphics.Bitmap@2d964ca

                ///리사이징
                int height = image_bitmap.getHeight();   //  2448
                int width = image_bitmap.getWidth();   // 3264

                Log.d("AAAAA", String.valueOf(height));
                Log.d("AAAAA", String.valueOf(width));

                Bitmap src = BitmapFactory.decodeFile(absolutePath);

                Log.d("AAAAA", String.valueOf(src));  //  android.graphics.Bitmap@e6b8f3d


                Bitmap resized = Bitmap.createScaledBitmap( src, width/4, height/4, true );

                saveBitmaptoJpeg(resized, "seatdot", name_Str);

                //배치해놓은 ImageView에 set
                ivProfilePhoto.setImageBitmap(resized);
                ivProfilePhoto.setTag("exist");





            }catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }


//        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ivProfilePhoto.setImageBitmap(imageBitmap);

//        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                 Log.d("CCCCC", String.valueOf(uri));
//
//                ivProfilePhoto.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /////   Uri 에서 파일명을 추출하는 로직
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    //비트맵을 jpg로
    public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+foler_name;
        String UploadImgPath = string_path+file_name;


        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }
}