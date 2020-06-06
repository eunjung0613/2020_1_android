package net.skhu.e05photo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo1Activity extends AppCompatActivity {
    static final int RC_PICK_FROM_GALLERY = 1;
    static final int RC_TAKE_PHOTO = 2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo1);
        imageView = findViewById(R.id.imageView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo1, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_pick_from_gallery) {
            startActivity_pickFromGallery();
            return true;
        } else if (id == R.id.action_take_photo) {
            startActivity_takePhoto();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void startActivity_pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_PICK_FROM_GALLERY);
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String imageFileName = "PHOTO" + timeStamp + ".jpg";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(directory, imageFileName);
    }

    private String cameraFilePath;

    private void startActivity_takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) == null) return;
            File file = createImageFile();
            cameraFilePath = "file://" + file.getAbsolutePath();
            String authorities = "net.skhu.e05photo.fileProvider";
            Uri cameraFileUri = FileProvider.getUriForFile(this, authorities, file);
            Log.e("내태그", cameraFileUri.toString());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
            startActivityForResult(intent, RC_TAKE_PHOTO);
        } catch (IOException ex) {
            Log.e("내태그", "에러", ex);
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode,int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case RC_PICK_FROM_GALLERY:
                Uri imageUri = intent.getData();
                Log.e("내태그", imageUri.toString());
                imageView.setImageURI(imageUri);
                break;
            case RC_TAKE_PHOTO:
                Log.e("내태그", Uri.parse(cameraFilePath).toString());
                imageView.setImageURI(Uri.parse(cameraFilePath));
                break;

        }
    }
}
