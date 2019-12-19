package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity{
    private Button logbtn,regbtn;
    private EditText username, password;
    private String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_pwd);
        logbtn = findViewById(R.id.bt_login);
        regbtn = findViewById(R.id.bt_regist);
    }
    public void onLogin(View v)
    {
        final String name = username.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        if (name == null||name.equals("")||password==null||password.equals("")){
            Toast.makeText(this,"用户名密码不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    result = LoginByPost(name, pwd);
                    Log.d("登录状态：",result);
                    handler.sendEmptyMessage(0);
                }
            }).start();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    };
    public void onReg(View v)
    {
        final String name = username.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        new Thread(){
            public void run() {
                result = RegistByPost(name,pwd);
                Log.d("登录状态：",result);
                handler.sendEmptyMessage(0);
            };
        }.start();
    }
    public String LoginByPost(String name,String password) {
        String msg = "";
        String LOGIN_URL = "http://192.168.0.107:8080/aaa/Servlettest/LoginServlet";
        try {
            String data = LOGIN_URL + "?username=" + URLEncoder.encode(name, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8");
            Log.d("url:",data);
            HttpURLConnection conn = (HttpURLConnection) new URL(data).openConnection();
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            //我们请求的数据:
            conn.connect();
            //这里可以写一些请求头的东东...

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                msg = new String(message.toByteArray());
                //解析Json（反序列化Json）
                JSONObject jsonobject = new JSONObject(msg);
                //int code = jsonobject.getInt("code");
                //相当于切换到主线程
//               runOnUiThread{
//                  new Runnable(){
//                      public void run(){
//                          Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//                     }
//                  }
//               }
//
//                  if(code == 0){
//                        //销毁当前界面
//                       finish();
//                    }
//
                final String LoginMessage = jsonobject.getString("msg");
                return LoginMessage;

            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String RegistByPost(String name,String password) {
        String LOGIN_URL = "http://192.168.0.107:8080/aaa/Servlettest/RegistServlet";
        try {
            String data = LOGIN_URL + "?username=" + URLEncoder.encode(name, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8");
            Log.d("url:",data);
            HttpURLConnection conn = (HttpURLConnection) new URL(data).openConnection();
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            //我们请求的数据:
            conn.connect();
            //这里可以写一些请求头的东东...

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                String msg = new String(message.toByteArray());
                //解析Json（反序列化Json）
                JSONObject jsonobject = new JSONObject(msg);
                //int code = jsonobject.getInt("code");
                //相当于切换到主线程
//               runOnUiThread{
//                  new Runnable(){
//                      public void run(){
//                          Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//                     }
//                  }
//               }
//
//                  if(code == 0){
//                        //销毁当前界面
//                       finish();
//                    }
//
                final String RegistMessage = jsonobject.getString("msg");
                return RegistMessage;

            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
