package com.tos.blepdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by admin on 2016/4/12.
 */
public class LogTest extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
            TextView tv = (TextView)findViewById(R.id.textView1);
            tv.setText(log.toString());
        } catch (IOException e) {
        }
//        TextView tv = (TextView)findViewById(R.id.textView1);
//        tv.setText(getMac());
    }

//    private String getMac()
//    {
//        String macSerial = null;
//        String str = "";
//
//        try
//        {
//            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
//            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//
//            for (; null != str;)
//            {
//                str = input.readLine();
//                if (str != null)
//                {
//                    macSerial = str.trim();// ȥ�ո�
//                    break;
//                }
//            }
//        } catch (IOException ex) {
//            // ����Ĭ��ֵ
//            ex.printStackTrace();
//        }
//        return macSerial;
//    }

}
