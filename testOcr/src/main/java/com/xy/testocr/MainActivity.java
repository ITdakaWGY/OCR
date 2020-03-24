package com.xy.testocr;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.ocr.at.CaptureActivity;
import com.xy.ocr.at.OcrActivity;

import exocr.exocrengine.EXOCRDict;
import exocr.exocrengine.EXOCRModel;

/**
 * Autor: Administrator
 * CreatedTime: 2020/3/24 0024
 * UpdateTime:2020/3/24 0024 10:20
 * Des: true 扫描身份证正面 false 扫码身份证背面
 * jks:信息获取命令：keytool -list -v -keystore d:/tzzxxt.keystore
 * UpdateContent:
 **/
public class MainActivity extends OcrActivity {

    String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean succ = EXOCRDict.InitDict(this);
        if (!succ) return;
        /**
         * 判断哪些权限未授予
         * 以便必要的时候重新申请
         */
        ActivityCompat.requestPermissions(this, permissions, 1002);
        findViewById(R.id.card_front).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getApplicationContext(), CaptureActivity.class);
                scanIntent.putExtra(CaptureActivity.class.getSimpleName() + "front", true);
                startActivityForResult(scanIntent, CaptureActivity.class.hashCode());
            }
        });

        findViewById(R.id.card_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getApplicationContext(), CaptureActivity.class);
                scanIntent.putExtra(CaptureActivity.class.getSimpleName() + "front", false);
                startActivityForResult(scanIntent, CaptureActivity.class.hashCode());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("xy", "requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (requestCode == CaptureActivity.class.hashCode() && resultCode == CaptureActivity.class.hashCode() && null != data) {

            final Bundle extras = data.getExtras();
            final EXOCRModel result = extras.getParcelable(CaptureActivity.class.getSimpleName());
            Log.e("xy", "result = " + result.toString());

            final TextView name = findViewById(R.id.card_name);
            name.setText(result.name);

            final TextView sex = findViewById(R.id.card_sex);
            sex.setText(result.sex);

            final TextView nation = findViewById(R.id.card_nation);
            nation.setText(result.nation);

            final TextView birth = findViewById(R.id.card_birth);
            birth.setText(result.birth);

            final TextView local = findViewById(R.id.card_local);
            local.setText(result.address);

            final TextView number = findViewById(R.id.card_number);
            number.setText(result.cardnum);

            final ImageView self = findViewById(R.id.card_self);
            self.setImageBitmap(BitmapFactory.decodeFile(result.bitmapPath));

        } else if (requestCode == CaptureActivity.class.hashCode() && resultCode == CaptureActivity.class.hashCode() && null != data) {

            final Bundle extras = data.getExtras();
            final EXOCRModel result = extras.getParcelable(CaptureActivity.class.getSimpleName());
            Log.e("xy", "result = " + result.toString());

            final TextView office = findViewById(R.id.card_office);
            office.setText(result.office);

            final TextView date = findViewById(R.id.card_date);
            date.setText(result.validdate);

            final ImageView country = findViewById(R.id.card_country);
            country.setImageBitmap(BitmapFactory.decodeFile(result.bitmapPath));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
