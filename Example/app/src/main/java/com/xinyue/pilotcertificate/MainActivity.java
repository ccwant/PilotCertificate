package com.xinyue.pilotcertificate;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
/**
 * Created by cc_want on 2017/7/6.
 */
public class MainActivity extends AppCompatActivity implements Synthesizing.OnSynthListener {

    private Synthesizing mSynthesizing;

    private EditText mEdtName;
    private EditText mEdtNationality;
    private EditText mEdtType;
    private Button mBtnAssemble;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSynthesizing();
    }
    private void initViews(){
        mEdtName = (EditText)findViewById(R.id.edt_name);
        mEdtNationality = (EditText)findViewById(R.id.edt_nationality);
        mEdtType = (EditText)findViewById(R.id.edt_type);
        mBtnAssemble = (Button)findViewById(R.id.btn_assemble);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
    }
    private void initSynthesizing(){
        mSynthesizing = new Synthesizing(this);
        mSynthesizing.setOutputDir(Constants.outputDir);
        mSynthesizing.setOnSynthListener(this);
    }
    public void onAssemble(View v){
        final String name = mEdtName.getText().toString();
        final String nationality = mEdtNationality.getText().toString();
        final String type = mEdtType.getText().toString();
        mProgressBar.setProgress(0);
        mBtnAssemble.setEnabled(false);
        try{
            mSynthesizing.create(name,nationality,type);
        }catch (Exception e){
            e.printStackTrace();
            mBtnAssemble.setEnabled(true);
            mProgressBar.setProgress(0);
        }
    }
    @Override
    public void onComplete() {
        mBtnAssemble.setEnabled(true);
        Intent intent = new Intent(this,ShareActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgress(int progress) {
        mProgressBar.setProgress(progress);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.action_settings:
                clear();
                break;
            default:
                break;
        }
        return true;
    }
    private void clear(){
        File file = new File(Constants.outputPath);
        if(file != null && file.exists()){
            file.delete();
            Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "未发现缓存数据", Toast.LENGTH_SHORT).show();
        }
    }
}
