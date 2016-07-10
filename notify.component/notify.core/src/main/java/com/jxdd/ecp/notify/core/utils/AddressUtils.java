package com.jxdd.ecp.notify.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.TreeMap;

public class AddressUtils {

	
	public static TreeMap getLocalAddress() throws Exception{
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		TreeMap< String, String> tm =null;
		InetAddress ip = null;
		StringBuffer sbt =  new StringBuffer();
		boolean flag = true;
		@SuppressWarnings("unused")
		NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
		while (allNetInterfaces.hasMoreElements()&& flag) {
			NetworkInterface netInterface1 = (NetworkInterface) allNetInterfaces
					.nextElement();
			Enumeration<InetAddress> addresses = netInterface1.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					tm = new TreeMap<String, String>();
					tm.put("hostName", ip.getHostName());
					tm.put("hostAddress", ip.getHostAddress());
					flag =false;
					break;
				}
			}
		}
		return tm;
	}
}
