package org.simplesns.simplesns.ui.main.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.ChangeProfileItem;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServerResponse;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.MainActivity;
import org.simplesns.simplesns.ui.main.camera.ImagePostActivity;
import org.simplesns.simplesns.ui.main.camera.model.ImagePostResult;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;
import org.simplesns.simplesns.ui.main.profile.model.CheckUsernameResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileChangeResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;
import org.simplesns.simplesns.ui.sign.FirstActivity;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

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
    private static final int REQUEST_PICK_PHOTO = 2;

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

    private String postPath;
    private String mediaPath;
    private static final int CAMERA_PIC_REQUEST = 1;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    private String mImageFileLocation = "";
    private Uri fileUri;
    ProgressDialog pDialog;

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

            new MaterialDialog.Builder(this)          //appdino
                    .title(R.string.uploadImages)
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback((dialog, view, which, text) -> {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_IMAGE_CAPTURE);

                                break;
                            case 1:
                                captureImage();
                                break;
                            case 2:
                                ivProfilePhoto.setImageResource(R.drawable.ic_profile_samplephoto);
                                break;
                        }
                    })
                    .show();
        });

        etUsername = findViewById(R.id.et_username);
        etIntroduction = findViewById(R.id.et_introduction);
        etEmail = findViewById(R.id.et_email);

        TextView tvLogout = findViewById(R.id.tv_logout);

        tvLogout.setOnClickListener(v->{
            GlobalUser.getInstance().logOut(this, FirstActivity.class);
        });

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

                setUserProfileFromServer(GlobalUser.getInstance().getMyId(), newUsername, newIntroduction);
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

                            Log.d("CCCCC", memberItem.getPhoto_url()); //  memberItem.getPhoto_url() =>   /img/profile/goni0211.jpg
                            Log.d("CCCCC", profilePhotoUrl);  //  profilePhotoUrl => http://XXXX.XXXX.XXXX.XXXX:3000/img/profile/goni0211.jpg

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

    private void setUserProfileFromServer(String username, String newUsername, String introduction) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        ChangeProfileItem changeProfileItem = new ChangeProfileItem(username, newUsername, introduction);
        Call<ProfileChangeResult> call = remoteService.setUserProfile(changeProfileItem);

        try {
            call.enqueue(new Callback<ProfileChangeResult>() {
                @Override
                public void onResponse(Call<ProfileChangeResult> call, Response<ProfileChangeResult> response) {
                    ProfileChangeResult profileChangeResult = response.body();

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
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    // appdino

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

                Uri selectedImage = data.getData();    // selectedImage => content://media/external/images/media/19052

               Log.d("GGGGG", String.valueOf(selectedImage));

                String[] filePathColumn = {MediaStore.Images.Media.DATA}; // filePathColumn => [Ljava.lang.String;@9f41f5

                // ContentResolver : query(uri, projection, selection, selectionArgs, sortOrder)
                //- uri : content://scheme 방식의 원하는 데이터를 가져오기 위한 정해진 주소
                //- projection : 가져올 컬럼 이름 목록, null이면 모든 컬럼
                //- selection : where 절에 해당하는 내용
                //- selectionArgs : selection에서 ?로 표시한 곳에 들어갈 데이터
                //- sortOrder : 정렬을 위한 order by 구문
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  //  columnIndex => 0
                mediaPath = cursor.getString(columnIndex);  //  mediaPath => /storage/emulated/0/DCIM/Camera/20190317_110012.jpg

                // Set the Image in ImageView for Previewing the Media
                ivProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(mediaPath)); // 압축되어 있는 jpg 파일을 풀고 bitmap 으로 변환
                cursor.close();

//                postPath = mediaPath;
                uploadFile(mediaPath);

        } else if (requestCode == CAMERA_PIC_REQUEST) {
            if (Build.VERSION.SDK_INT > 21) {

                Glide.with(this).load(mImageFileLocation).into(ivProfilePhoto);
                postPath = mImageFileLocation;

            } else {
                Glide.with(this).load(fileUri).into(ivProfilePhoto);
                postPath = fileUri.getPath();

            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(ACTION_IMAGE_CAPTURE);  // ACTION_IMAGE_CAPTURE => android.media.action.IMAGE_CAPTURE

            File photoFile = null;

            try {
                photoFile = createImageFile();

                Log.d("AAAAA", String.valueOf(photoFile));

            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {

            Intent intent = new Intent(ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }
    }

    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }

    // Uploading Image/Video
    private void uploadFile(String postPath) {

        if (TextUtils.isEmpty(postPath)) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        }else {
            final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
            File file = new File(postPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);   // Parsing any Media type
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestBody);

            Call<Integer> req1;
            try {
                req1 = remoteService.uploadProfilePhoto(body);
                req1.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer result = response.body();
                        if (response.isSuccessful() && result != null) {
                            if (result == 1) {
                                Toast.makeText(ProfileChangeActivity.this, "사진저장완료", Toast.LENGTH_SHORT).show();


                                Call<ProfileChangeResult> req2;
                                try {
                                    ChangeProfileItem changeProfileItem = new ChangeProfileItem(GlobalUser.getInstance().getMyId(), file.getName());
                                    req2 = remoteService.setUserProfile(changeProfileItem);
                                    req2.enqueue(new Callback<ProfileChangeResult>() {
                                        @Override
                                        public void onResponse(Call<ProfileChangeResult> call, Response<ProfileChangeResult> response) {
                                            ProfileChangeResult profileChangeResult = response.body();

                                            switch (profileChangeResult.code) {
                                                case 200:
                                                    Log.d(TAG, profileChangeResult.result);
                                                    Toast.makeText(ProfileChangeActivity.this, "DB에 url반영", Toast.LENGTH_SHORT).show();
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

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                Log.d(TAG, "Failed");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}