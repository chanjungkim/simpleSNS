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
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.profile.model.CheckUsernameResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileChangeResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;
import org.simplesns.simplesns.ui.sign.FirstActivity;

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
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static final int CAMERA_PIC_REQUEST = 1111;
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

            new MaterialDialog.Builder(this)
                    .title(R.string.uploadImages)
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
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
                        }
                    })
                    .show();


//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

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
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                ivProfilePhoto.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

                postPath = mediaPath;   ///storage/emulated/0/DCIM/Camera/20190317_110012.jpg

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
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
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

    protected void showpDialog() {
        if (!pDialog.isShowing()) pDialog.show();
        }
}