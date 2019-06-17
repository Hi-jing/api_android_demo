package com.dhj.demo;

import android.content.Intent;
import android.os.Bundle;
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
 * 添加课程activity
 *
 * @author denghaijing
 */
public class AddActivity extends AppCompatActivity {

    private EditText name, teacher;

    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        name = (EditText) findViewById(R.id.name_txt);
        teacher = (EditText) findViewById(R.id.teacher_txt);
        addBtn = (Button) findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse();
            }
        });

    }

    /**
     * 异步保存课程信息
     */
    private void saveCourse() {
        String requestUrl = BaseRequestURL.url + "/course/save.do";
        OkHttpClient okHttpClient = new OkHttpClient();
        //数据类型指定为JSON
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        Course course = new Course();
        course.setName(name.getText().toString().trim());
        course.setTeacher(teacher.getText().toString().trim());

        RequestBody requestBody = RequestBody.create(mediaType, GsonUtil.object2Json(course));
        Request request = new Request.Builder()
                //指定请求url
                .url(requestUrl)
                //指定post
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //失败回调
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure: ", e.toString());
            }

            //成功回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseDTO result = GsonUtil.json2Object(response.body().string(), ResponseDTO.class);
                //业务处理判断
                if (result.getCode() == 0) {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                Looper.prepare();
                Toast.makeText(AddActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

}
