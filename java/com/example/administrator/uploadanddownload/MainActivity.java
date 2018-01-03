package com.example.administrator.uploadanddownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {
    private JCVideoPlayerStandard mJcVideoPlayerStandard;
    private Button download,upload;
    private String filepath="http://192.168.8.187:8080/download1/2.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        download=(Button)findViewById(R.id.download);
        upload=(Button)findViewById(R.id.upload);

        mJcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        String localfilepath= Environment.getExternalStorageDirectory().toString() + "/shidoe/"+getName(filepath);
        mJcVideoPlayerStandard.setUp(localfilepath
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");

        download.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                download.setText("下载中");
                downLoad(filepath);
                download.setText("下载完成");
                String localfilepath= Environment.getExternalStorageDirectory().toString() + "/shidoe/"+getName(filepath);
                mJcVideoPlayerStandard.setUp(localfilepath
                        , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频播放");
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload.setText("上传中");
            }
        });
    }



    public static void downLoad(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(getName(path)));//指定文件保存路径，代码看下一步
                            byte[] buf = new byte[1024];
                            int ch;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static String getName(String s){
        int i = s.lastIndexOf("/");
        String FileName= s.substring(i + 1);
        return FileName;
    }

}
