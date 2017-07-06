package com.xinyue.pilotcertificate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
/**
 * Created by cc_want on 2017/7/6.
 */
public class ShareActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        setTitle("分享");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initVies();
        loadImageView();
    }
    private void initVies(){
        mImageView = (ImageView)findViewById(R.id.imageView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // 处理返回逻辑
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadImageView(){
        File file = new File(Constants.outputPath);
        if(file != null && file.exists()){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            mImageView.setImageBitmap(bitmap);
        }else{
            finish();
        }
    }
    public void onShare(View v){
        File file = new File(Constants.outputPath);
        if(file != null && file.exists()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpg");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            startActivity(Intent.createChooser(intent, "Share"));
        }else{
            Toast.makeText(this, "未发现图片数据", Toast.LENGTH_SHORT).show();
        }
    }

}
