package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f);
        getPdf("https://kdocs.cn/l/cf7jY3szTd2F?f=201[文件]关键接口清单(1)(1).docx");
    }

    //调用getPdf传入要展示的地址即可实现
    private void getPdf(String url) {
//        View pdfView = findViewById(R.id.pdf_view);
//        final InputStream[] input = new InputStream[1];
//        new AsyncTask<Void, Void, Void>() {
//            @SuppressLint({"WrongThread", "StaticFieldLeak"})
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    input[0] = new URL(url).openStream();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                pdfView.fromStream(input[0])
//                        .enableAnnotationRendering(true)
//                        .load();
//            }
//        }.execute();
    }
}