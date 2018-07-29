package com.example.renwoxing.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class WifiUtil {
	private static WifiManager wifiManager;
	private static DhcpInfo dhcpInfo;
	private static WifiInfo wifiInfo;

	// ip获取
	public static String getIp(Context context) {
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		dhcpInfo = wifiManager.getDhcpInfo();
		wifiInfo = wifiManager.getConnectionInfo();
		// wifiInfo返回当前的Wi-Fi连接的动态信�?
		int ip = wifiInfo.getIpAddress();
		return "" + FormatIP(ip);
	}

	// 网关获取
	public static String getGateWay(Context context) {
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		dhcpInfo = wifiManager.getDhcpInfo();

//		return "dh_ip:" + FormatIP(dhcpInfo.ipAddress) + "\n" + "dh_gateway"
//				+ FormatIP(dhcpInfo.gateway);
		return FormatIP(dhcpInfo.gateway);
	}

	// IP地址转化为字符串格式,将二进制数据（IP地址）转换成十进�?
	public static String FormatIP(int IpAddress) {
		return Formatter.formatIpAddress(IpAddress);
	}

}
