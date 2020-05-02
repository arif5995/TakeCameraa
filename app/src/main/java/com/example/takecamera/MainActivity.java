package com.example.takecamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.takecamera.ImagePicker.RxImageConverter;
import com.example.takecamera.ImagePicker.RxImagePicker;
import com.example.takecamera.ImagePicker.Sources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.example.takecamera.Utils.GlobalHelper.compressFoto;
import static com.example.takecamera.Utils.GlobalHelper.convertFileToContentUri;
import static com.example.takecamera.Utils.GlobalHelper.deleteRecursive;
import static com.example.takecamera.Utils.GlobalHelper.encodeFileBase64;
import static com.example.takecamera.Utils.GlobalHelper.getMimeTypeFromUri;
import static com.example.takecamera.Utils.GlobalHelper.getPath;
import static com.example.takecamera.Utils.GlobalVars.BASE_DIR;
import static com.example.takecamera.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.takecamera.Utils.GlobalVars.IMAGES_DIR;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private ImageView imgCamera, imgUser;
    private RadioGroup converterRadioGroup;
    private String id;
    private Uri photoUri;
    private File tempFile = null;
    private File compressedImage = null;
    private String photoExt = "";
    private String encodePhoto = "";
    private Uri finalPhotoUri = null;
    private Bitmap theBitmap = null;
    private static final int PICK_IMAGE_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCamera = findViewById(R.id.ivCamera);
        imgUser = findViewById(R.id.imgUser);

        //ini yang dikirim ke api
        String base64 = id+photoExt;

        //open camera
        converterRadioGroup = findViewById(R.id.radio_group);
        converterRadioGroup.check(R.id.radio_file);

        if (RxImagePicker.with(MainActivity.this).getActiveSubscription() != null) {
            RxImagePicker.with(MainActivity.this).getActiveSubscription().subscribe(this::onImagePicked);
        }
        id = valueOf(System.currentTimeMillis());

        //SetOnClick
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){
            photoUri = data.getData();
            Glide.with(this)
                    .load(photoUri)
                    .skipMemoryCache(true)
                    .into(imgUser);
        }
    }

    //PickImage
    private void pickImageFromSource(Sources source) {

        RxImagePicker.with(MainActivity.this).requestImage(source)
                .flatMap(uri -> {
                    switch (converterRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_file:
                            return RxImageConverter.uriToFile(MainActivity.this, uri, createTempFile());
                        case R.id.radio_bitmap:
                            return RxImageConverter.uriToBitmap(MainActivity.this, uri);
                        default:
                            return Observable.just(uri);
                    }
                })
                .subscribe(this::onImagePicked, throwable -> Toast.makeText(MainActivity.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }
    private File createTempFile() {
        return new File(BASE_DIR + EXTERNAL_DIR_FILES, id + ".jpeg");
    }

    private void onImagePicked(Object result) {
        if (result instanceof Bitmap) {
            //ivImage.setImageBitmap((Bitmap) result);
        }else{
            photoUri = Uri.parse(valueOf(result));

            tempFile = new File(valueOf(photoUri));

            compressedImage = compressFoto(MainActivity.this, tempFile);


            try {
                finalPhotoUri = convertFileToContentUri(MainActivity.this, compressedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

            photoExt = "."+getMimeTypeFromUri(MainActivity.this, finalPhotoUri);
            encodePhoto = encodeFileBase64(getPath(MainActivity.this, finalPhotoUri));

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                    try {
                        theBitmap = Glide
                                .with(MainActivity.this)
                                .load(getPath(MainActivity.this, finalPhotoUri))
                                .asBitmap()
                                .into(100, 100).get();

                    } catch (final ExecutionException e) {
                        Log.e("TAG","ExecutionException " + e.getMessage());
                    } catch (final InterruptedException e) {
                        Log.e("TAG","InterruptedException " + e.getMessage());
                    }
                    return null;
                }
                @SuppressLint("WrongThread")
                @Override
                protected void onPostExecute(Void dummy) {
                    if (null != theBitmap) {
                        // The full bitmap should be available here
                        //ivAvatar.setImageBitmap(theBitmap);

                        File mypath = new File(IMAGES_DIR,id+".jpeg");

                        ContextWrapper cw = new ContextWrapper(MainActivity.this);
                        // path to /data/data/yourapp/app_data/imageDir
                        // Create imageDir
                        //File mypath=new File(fotoPath,userId+".jpeg");

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(mypath);
                            // Use the compress method on the BitMap object to write image to the OutputStream
                            theBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Glide.with(getApplicationContext())
                                .load(mypath)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.ic_person)
                                .into(imgUser);

                        Log.d("TAG", "Image loaded");
                    };
                }
            }.execute();

            deleteRecursive(new File(valueOf(finalPhotoUri)));
            deleteRecursive(createTempFile());
            deleteRecursive(tempFile);

        }
    }

}
