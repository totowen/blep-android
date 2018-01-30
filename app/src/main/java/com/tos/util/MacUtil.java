package com.tos.util;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.net.SocketException;
/**
 * Created by admin on 2016/3/30.
 */

public class MacUtil {
    //根据IP获取本地Mac (linux底层信息)
    public static String getMacAddress() {
        String macSerial = null;
        String str = "";
        try
        {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;)
            {
                str = input.readLine();
                if (str != null)
                {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
//        return "94:a1:a2:ec:5b:f6";
    }


    public static String getMaccAddress()
    {

        String strMacAddr = "";
        byte[] b;
        try
        {
            NetworkInterface NIC = NetworkInterface.getByName("eth0");
            b = NIC.getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++)
            {
//                if (i != 0)
//                {
//                    buffer.append(':');
//                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
//        Log.i("Mac Address", "--- DVB Mac Address : " + strMacAddr);

//        return "525400123457";
        return strMacAddr;
    }

}
