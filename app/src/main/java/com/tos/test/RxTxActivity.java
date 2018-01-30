package com.tos.test;

import android.app.Activity;
import android.os.Bundle;

import com.tos.blepdemo.R;

/**
 * Created by hasaa on 2016/4/27.
 */
public class RxTxActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxtx);
        main();
    }

    private void main() {
        // 判断系统环境
        if (SystemUtil.systemIsLinux()) {
            // 初始化端口和相关方法
            SerialPortUtilityNew utilityNew = new SerialPortUtilityNew();
            // 获取端口名称
            utilityNew.getPortNames();
            // 设置需要监测的端口
            String portName = "COM1";
            if (utilityNew.getLinuxPort(portName)) {
                utilityNew.init(portName);
                String result = "M1";
                System.out.println("send " + result);
                utilityNew.sendMsg(result + "\r\n");
            }
            //开启线程
            MyThread t = new MyThread("M0Thread");
            t.start();
            try {
                Thread.sleep(2000);//睡眠两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.interrupt();//设置线程停止状态
            if (t.isInterrupted()) {//获取当前线程
                String result = "M0";
                System.out.println("send " + result);
                utilityNew.sendMsg(result + "\r\n");
            }
            System.out.println("system end");
            utilityNew.closeSerialPort();
            return;

        }
    }

    class MyThread extends Thread {
        int i = 0;

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                // System.out.println(getName() + getId() + " 执行了 " + ++i + "次");
            }
        }

    }
}


