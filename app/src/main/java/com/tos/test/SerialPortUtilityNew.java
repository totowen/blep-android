package com.tos.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortUtilityNew implements SerialPortEventListener {

	// 检测系统中可用的通讯端口类
	private CommPortIdentifier portId;
	// Enumeration 为枚举型类,在util中
	private Enumeration<CommPortIdentifier> portList;

	// 输入输出流
	private InputStream inputStream;
	private OutputStream outputStream;

	// RS-232的串行口
	private SerialPort serialPort;
	public String test = "";// 保存串口返回信息
	
	public boolean open = false;

	private Map<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
	
	/**
	 * 获得所有可用串口名称，并将可用串口名称与串口对应
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPortNames(){
		List<String> names = new ArrayList<String>();
		
		// 获取系统中所有的通讯端口
		portList = CommPortIdentifier.getPortIdentifiers();
		System.out.println("是否存在串口："+portList.hasMoreElements());
		// 用循环结构找出串口
		while (portList.hasMoreElements()) {
			// 强制转换为通讯端口类型
			portId = (CommPortIdentifier) portList.nextElement();
			System.out.println(portId.getName());
			// 判断是否为串口
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				names.add(portId.getName());
				portMap.put(portId.getName(), portId);
			}
		}
		return names;
	}
	
	/**
	 * 获得Linux系统下的串口信息
	 * @param portName
	 */
	public boolean getLinuxPort(String portName){
		try {
			System.out.println("查找串口"+portName);
			CommPortIdentifier linuxPort = CommPortIdentifier.getPortIdentifier(portName);
			portMap.put(portName, linuxPort);
			return true;
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 *  初始化串口(打开串口)
	 */
	public boolean init(String comName) {
		// 比较串口名称是否为“COM3”
		if (portMap.containsKey(comName)) {
			portId = portMap.get(comName);
			System.out.println("找到"+comName);
			// 打开串口
			try {
				// open:（应用程序名【随意命名】，阻塞时等待的毫秒数）
				serialPort = (SerialPort) portId.open(
						"GSMModemTest2.0", 2000);
				// 设置串口监听
				serialPort.addEventListener(this);
				// 设置可监听
				serialPort.notifyOnDataAvailable(true);

				/* 设置串口通讯参数 */
				// 波特率，数据位，停止位和校验方式
				// 波特率2400,偶校验
				serialPort.setSerialPortParams(115200,
						SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				test = "";
				outputStream = serialPort.getOutputStream();
				inputStream = serialPort.getInputStream();
				open = true;
				return open;
			} catch (PortInUseException e) {
				e.printStackTrace();
			} catch (TooManyListenersException e) {
				e.printStackTrace();
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return open;
	}

	/**
	 * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {

		switch (event.getEventType()) {
		case SerialPortEvent.BI:
			System.out.println("通讯中断");
			break;
		case SerialPortEvent.OE:
			System.out.println("溢位错误");
			break;
		case SerialPortEvent.FE:
			System.out.println("帧错误");
			break;
		case SerialPortEvent.PE:
			System.out.println("奇偶校验错误");
			break;
		case SerialPortEvent.CD:
			System.out.println("载波检测");
			break;
		case SerialPortEvent.CTS:
			System.out.println("清除发送");
			break;
		case SerialPortEvent.DSR:
			System.out.println("数据设备准备好");
			break;
		case SerialPortEvent.RI:
			System.out.println("振铃指示");
			break;
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			System.out.println("输出缓冲区已清空");
			break;
		case SerialPortEvent.DATA_AVAILABLE:// 获取到串口返回信息
			System.out.println("有数据送达");
			readComm();
			break;
		default:
			break;
		}
	}

	// 读取串口返回信息
	public void readComm() {
		byte[] readBuffer = new byte[1024];

		try {
			// 从线路上读取数据流
			int len = 0;
			while ((len = inputStream.read(readBuffer)) != -1) {
				System.out.println("实时反馈："
						+ new String(readBuffer, 0, len).trim() + "  " + new Date());
				test += new String(readBuffer, 0, len).trim();
//				String msg = new String(readBuffer, 0, len).trim();
//				this.forms.showBackMsg(msg);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭串口
	 */
	public void closeSerialPort() {
		if(serialPort != null){
			try {
				serialPort.notifyOnDataAvailable(false);  
				serialPort.removeEventListener();  
				inputStream.close();
				outputStream.close();
				serialPort.close();  
				serialPort = null;
				open = false;
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}

	// 向串口发送信息方法
	public void sendMsg(String msg) {
		String information = msg;// 要发送的内容
		try {
			outputStream.write(information.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
