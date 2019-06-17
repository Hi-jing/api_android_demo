package com.dhj.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.dhj.demo.dto.ResponseDTO;
import com.dhj.demo.entity.Course;
import com.dhj.demo.util.BaseRequestURL;
import com.dhj.demo.util.GsonUtil;
import okhttp3.*;

import java.io.IOException;

/**
 * 课程详情activity
 *
 * @author denghaijing
 */
public class DetailActivity extends AppCompatActivity {

    private Button toAddBtn;
    private TextView nameTxt;
    private TextView teacherTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        toAddBtn = (Button) findViewById(R.id.to_add_btn);
        nameTxt = (TextView) findViewById(R.id.name_txt);
        teacherTxt = (TextView) findViewById(R.id.teacher_txt);

        //异步请求获取课程详情
        getCourseDetail();
        toAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 异步get请求获取课程详情
     */
    private void getCourseDetail() {
        Intent intent = getIntent();
        String courseId = intent.getStringExtra("id");

        String requestUrl = BaseRequestURL.url + "/course/detail.do?id=" + courseId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure: ", e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseDTO result = GsonUtil.json2Object(response.body().string(), ResponseDTO.class);
                Course course = GsonUtil.json2Object(result.getData().toString(), Course.class);
                //对业务请求判断是否成功处理
                if (result.getCode() == 0) {
                    nameTxt.setText(course.getName());
                    teacherTxt.setText(course.getTeacher());
                }
            }
        });

    }


}
