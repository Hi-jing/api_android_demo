package com.dhj.demo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dhj.demo.dto.ResponseDTO;
import com.dhj.demo.entity.Course;
import com.dhj.demo.util.BaseRequestURL;
import com.dhj.demo.util.GsonUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * 主Activity
 *
 * @author denghaijing
 */
public class MainActivity extends ListActivity {

    //封装ListView的数据对象
    private List<Course> showList;
    //自定义的Adapter
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    //使用Handler消息传递机制,创建handler
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                adapter = new MyAdapter(MainActivity.this);
                //设置Adapter
                setListAdapter(adapter);
            }
        }
    };

    /**
     * 同步get请求初始化页面数据
     */
    private void initData() {
        String requestUrl = BaseRequestURL.url + "/course/list.do";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        final Call call = okHttpClient.newCall(request);
        //子线程执行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    ResponseDTO data = GsonUtil.json2Object(response.body().string(), ResponseDTO.class);
                    List<Course> list = GsonUtil.jsonArr2ObjectList(data.getData().toString(), Course.class);
                    showList = list;
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    Log.d("OkHttpR: ", e.toString());
                }
            }
        }).start();

    }

    /**
     * 自定义的Adapter类
     */
    public final class MyAdapter extends BaseAdapter {
        //实例化布局对象---用于实例化每行的布局->View对象
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        //获取ListView的总行数
        @Override
        public int getCount() {
            return showList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        //ListView中一行对应的对象组合--容器类
        //使用ViewHolder可以减少findViewById()的使用频率,方便数据访问
        public final class ViewHolder {
            public TextView name;
            public TextView teacher;
            public Button deleteBtn;
            public Button editBtn;
        }


        /**
         * @param position    显示的数据的位置
         * @param convertView
         * @param parent      父控件(上层的ListView)
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //本行对应的容器对象
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();

                //实例化ListView的一行, root参数为空说明此View的父控件默认为为上层的ListView
                convertView = mInflater.inflate(R.layout.main, null);
                //获取内部的各个控件对象, 保存到容器对象中
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.teacher = (TextView) convertView.findViewById(R.id.teacher);
                holder.deleteBtn = (Button) convertView.findViewById(R.id.delete_btn);
                holder.editBtn = (Button) convertView.findViewById(R.id.edit_btn);

                //设置容器对象为ListView当前行的Tag--建立容器类对象与ListView当前行的联系
                convertView.setTag(holder);
            } else {
                //如果该行的View已经存在,则通过标记获取该行对应的对象
                holder = (ViewHolder) convertView.getTag();
            }

            //设置该行内的控件对象对应的属性
            holder.name.setText(showList.get(position).getName());
            holder.teacher.setText(showList.get(position).getTeacher());

            //绑定该行中的Button对象的监听器
            //创建监听器对象时, 用参数传递当前的行号
            //每行中的Button建一个监听器对象,不同对象的position值不同
            holder.deleteBtn.setOnClickListener(new ViewButtonClickListener(position));
            holder.editBtn.setOnClickListener(new ViewButtonClickListener(position));

            //返回当前行对应的View对象
            return convertView;
        }
    }

    //点击ListView一行时的回调函数,跳转详情页面
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //获取该行的指定属性的值
        String courseId = String.valueOf(showList.get(position).getId());
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    /**
     * 使用内部类实现ListView的每行中按钮的监听函数
     * 该监听器类会为ListView的每一行提供一个监听器对象,用来监听该行中按钮的点击事件
     */
    class ViewButtonClickListener implements View.OnClickListener {
        //记录按钮所在的行号
        int position;

        public ViewButtonClickListener(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_btn:
                    deleteCourse();
                    break;
                case R.id.edit_btn:
                    go2EditPage();
                    break;
                default:
                    break;
            }

        }

        /**
         * 异步请求删除课程
         */
        private void deleteCourse() {
            String requestUrl = BaseRequestURL.url + "/course/delete.do?id=" + showList.get(position).getId();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .delete()
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
                    //对业务请求判断是否成功处理
                    if (result.getCode() == 0) {
                        //从数据源data中删除数据
                        showList.remove(showList.get(position));
                        handler.sendEmptyMessage(0);
                    }
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });

        }

        /**
         * 跳转编辑页面
         */
        private void go2EditPage() {
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            String courseId = String.valueOf(showList.get(position).getId());
            intent.putExtra("id", courseId);
            startActivity(intent);
        }

    }


}