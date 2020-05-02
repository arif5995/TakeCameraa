package com.example.takecamera.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Spinner;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;

import static com.example.takecamera.Utils.GlobalVars.BASE_DIR;
import static com.example.takecamera.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.takecamera.Utils.GlobalVars.IMAGES_DIR;
import static com.example.takecamera.Utils.GlobalVars.PICTURES_DIR_FILES;


public class GlobalHelper {


    public static final String SELECT_USER = "selectUser";

    public static String getVersion(Context context){
        PackageManager manager = context.getPackageManager();
        String version = "";
        try {
            PackageInfo info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "V"+ version;
    }

    public static String convertDate(String currentDate, String currentDateFormat, String newDateFormar) {
        String strCurrentDate = currentDate;
        SimpleDateFormat format = new SimpleDateFormat(currentDateFormat);
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat(newDateFormar);
        String date = format.format(newDate);

        return date;
    }

    public static Date convertStringToDateFormat(String dateString, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convertedDate;
    }


    public static void createFolder() {
        File folder = new File(BASE_DIR + EXTERNAL_DIR_FILES);
        File pictures = new File(BASE_DIR + PICTURES_DIR_FILES);
        File imageFolder = new File(IMAGES_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }

        if (!pictures.exists()) {
            pictures.mkdirs();
        }
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public static File compressFoto(Context context, File actualImage) {
        final String path = IMAGES_DIR;

        File compressedImage = new Compressor.Builder(context)
                .setMaxWidth(1280)
                .setMaxHeight(1024)
                .setQuality(85)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(path)
                .build()
                .compressToFile(actualImage);

        deleteRecursive(actualImage);

        return compressedImage;
    }

    public static Uri convertFileToContentUri(Context context, File file) throws Exception {
        //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri
        ContentResolver cr = context.getContentResolver();
        String imagePath = file.getAbsolutePath();
        String imageName = null;
        String imageDescription = null;
        String uriString = MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription);
        return Uri.parse(uriString);
    }

    public static String getMimeTypeFromUri(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));

        return type;
    }

    public static String encodeFileBase64(String filePath) {
        File file = new File(filePath);  //file Path
        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int j = 0; j < b.length; j++) {
                System.out.print((char) b[j]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            //System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }

        byte[] byteFileArray = new byte[0];
        try {
            byteFileArray = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String base64String = "";
        if (byteFileArray.length > 0) {
            base64String = Base64.encodeToString(byteFileArray, Base64.NO_WRAP);
            //Log.i("File Base64 string", "IMAGE PARSE ==>" + base64String);
        }

        return base64String;
    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private static Writer writer;

    public static void write(Context context, String fileName, String data) {
        File root = Environment.getExternalStorageDirectory();
        File outDir = new File(root.getAbsolutePath() + File.separator + "AAAA");
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        try {
            if (!outDir.isDirectory()) {
                throw new IOException(
                        "Unable to create directory. Maybe the SD card is mounted?");
            }
            File outputFile = new File(outDir, fileName);
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(data);
//            Toast.makeText(context.getApplicationContext(),
//                    "Report successfully saved to: " + outputFile.getAbsolutePath(),
//                    Toast.LENGTH_LONG).show();
            writer.close();
        } catch (IOException e) {
            Log.w("eztt", e.getMessage(), e);
//            Toast.makeText(context, e.getMessage() + " Unable to write to external storage.",
//                    Toast.LENGTH_LONG).show();
        }

    }

    public static SoundPool playSound(Context context, int soundInRaw){
        SoundPool soundPool;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
        soundPool.load(context, soundInRaw, 1);


        return soundPool;
    }

//    public static boolean isLoggedIn(){
//        List<User> list = new ArrayList<>();
//        list = userHash.getAllValues();
//
//        if (list != null && !list.isEmpty()){
//            for (User user:list){
//                if (!user.getUser_id().equals("") && user.getUser_id()!=null){
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

//    public static String getRole(){
//        List<User> list = new ArrayList<>();
//        list = userHash.getAllValues();
//
//        if (!list.isEmpty()){
//            for (User user:list){
//                if (!user.getUser_id().equals("") && user.getUser_id()!=null){
//                    return user.getRole();
//                }
//            }
//        }
//
//        return "";
//    }


//    public static String getUserId(){
//        List<User> list = new ArrayList<>();
//        list = userHash.getAllValues();
//
//        if (!list.isEmpty()){
//            for (User user:list){
//                if (!user.getUser_id().equals("") && user.getUser_id()!=null){
//                    return user.getUser_id();
//                }
//            }
//        }
//
//        return "";
//    }

//    public static String getUserEmail(){
//        List<User> list = new ArrayList<>();
//        list = userHash.getAllValues();
//
//        if (!list.isEmpty()){
//            for (User user:list){
//                if (!user..equals("") && user.getEmail()!=null){
//                    return user.getEmail();
//                }
//            }
//        }
//
//        return "";
//    }

//    public static String getAddress(){
//        List<User> list = new ArrayList<>();
//        list = userHash.getAllValues();
//
//        if (!list.isEmpty()){
//            for (User user:list){
//                if (!user.get.equals("") && user.getAddress()!=null){
//                    return user.getAddress();
//                }
//            }
//        }
//
//        return "";
//    }

    public static String getPriceInIdr(String price){
        Locale localeID = new Locale("in", "ID");

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return formatRupiah.format(Double.parseDouble(price));
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

//    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {
//
//        BadgeDrawable badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgeDrawable) {
//            badge = (BadgeDrawable) reuse;
//        } else {
//            badge = new BadgeDrawable(context);
//        }
//
//        badge.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_badge, badge);
//    }

    public static String getTransactionId(String transcationId, String date){
        String id = "SA"+transcationId+date.replaceAll("[^\\d]", "");
        return id;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
}
