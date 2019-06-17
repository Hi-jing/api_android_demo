package com.dhj.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dhj.demo.dto.ResponseDTO;
import com.dhj.demo.entity.Course;
import com.dhj.demo.util.BaseRequestURL;
import com.dhj.demo.util.GsonUtil;
import okhttp3.*;

import java.io.IOException;

/**
 * 更新课程activity
 *
 * @author denghaijing
 */
public class UpdateActivity extends AppCompatActivity {

    private EditText nameTxt, teacherTxt;

    private Button updateBtn;

    private String courseId;

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Intent intent = getIntent();
        courseId = intent.getStringExtra("id");

        nameTxt = (EditText) findViewById(R.id.name_txt);
        teacherTxt = (EditText) findViewById(R.id.teacher_txt);
        updateBtn = (Button) findViewById(R.id.update_btn);

        //渲染数据
        initData();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse();
            }
        });

    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                nameTxt.setText(course.getName());
                teacherTxt.setText(course.getTeacher());
            }
        }
    };

    private void initData() {
        String requestUrl = BaseRequestURL.url + "/course/detail.do?id=" + courseId;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    ResponseDTO result = GsonUtil.json2Object(response.body().string(), ResponseDTO.class);
                    course = GsonUtil.json2Object(result.getData().toString(), Course.class);
                    //对业务请求判断是否成功处理
                    if (result.getCode() == 0) {
                        handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    Log.d("OkHttpR: ", e.toString());
                }
            }
        }).start();

    }


    /**
     * 异步put请求更新课程
     */
    private void updateCourse() {

        String requestUrl = BaseRequestURL.url + "/course/update.do?id=" + courseId;
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Course course = new Course();
        course.setName(nameTxt.getText().toString().trim());
        course.setTeacher(teacherTxt.getText().toString().trim());

        RequestBody requestBody = RequestBody.create(mediaType, GsonUtil.object2Json(course));
        Request request = new Request.Builder()
                .url(requestUrl)
                .put(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure: ", e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseDTO result = GsonUtil.json2Object(response.body().string(), ResponseDTO.class);
                if (result.getCode() == 0) {
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                Looper.prepare();
                Toast.makeText(UpdateActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }


}
