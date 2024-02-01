package com.seres.base.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：liyu
 * @date ：2023/3/29 11:05
 */
@Slf4j
public class IPUtil {

	/**
	 * 判断是否为私有IP的正则表达式
	 */
	private static String regexPrivateIp = "^(127[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|(172[.]((1[6-9])|(2\\d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|(192[.]168[.]\\d{1,3}[.]\\d{1,3})$";

	/**
	 * 判断是否为有效IP的正则表达式
	 */
	private static String regexIp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	/**
	 * 判断IP是否为有效的ipv6
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isIpV6(String ip) {
		try {
			return InetAddress.getByName(ip).getAddress().length == 16;
		} catch (UnknownHostException var2) {
			return false;
		}
	}

	/**
	 * 判断IP是否为有效的ipv4
	 *
	 * @param ip
	 * @return true为有效，false为无效
	 */
	public static boolean isIpV4(String ip) {
		if (null == ip) {
			return false;
		}
		return ip.matches(regexIp);
	}

	/**
	 * 判断是否为私有ipv4
	 *
	 * @param ip
	 * @return true为私有，false为非私有
	 */
	public static boolean isPrivateIpV4(String ip) {
		if (null == ip) {
			return false;
		}
		return ip.matches(regexPrivateIp);
	}

	/**
	 * 获取本服务器ip列表（v4+v6）
	 *
	 * @return
	 */
	public static List<String> getLocalIps() {
		List<String> ips = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();
				if (ni.isLoopback() || !ni.isUp()) {
					continue;
				}
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					InetAddress ia = ias.nextElement();
					if (!ia.isLinkLocalAddress() &&
							(ia instanceof Inet4Address || ia instanceof Inet6Address)) {
						ips.add(ia.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			log.error("本地IP地址获取失败", e);
		}
		return ips;
	}

	/**
	 * 获取本服务器ipv4列表
	 *
	 * @return
	 */
	public static List<String> getLocalIpV4s() {
		return getLocalIps().stream().filter(IPUtil::isIpV4).collect(Collectors.toList());
	}

	/**
	 * 获取本服务器ipv6列表
	 *
	 * @return
	 */
	public static List<String> getLocalIpV6s() {
		return getLocalIps().stream().filter(IPUtil::isIpV6).collect(Collectors.toList());
	}

	/**
	 * 获取http客户端IP链（clientIp+代理链）
	 *
	 * @param request
	 * @return [122.1.35.5, 192.168.5.94]
	 */
	public static List<String> getClientChain(HttpServletRequest request) {
		String pcs = getProxyChain(request);
		if(StringUtil.isEmpty(pcs)){
			pcs = request.getRemoteAddr();
		}
		if(StringUtil.isEmpty(pcs)){
			return new ArrayList<>();
		}
		pcs = pcs.replace(" ", "");
		return Arrays.asList(pcs.split(",", -1));
	}

	/**
	 * 获取http客户端IP代理链
	 * @param request
	 * @return 逗号分割的ip链，122.1.35.5,192.168.5.94
	 */
	public static String getProxyChain(HttpServletRequest request) {
		//X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");
		if (noUnknown(ipAddresses)) {
			return ipAddresses;
		}

		//Proxy-Client-IP：apache 服务代理
		ipAddresses = request.getHeader("Proxy-Client-IP");
		if (noUnknown(ipAddresses)) {
			return ipAddresses;
		}

		//WL-Proxy-Client-IP：weblogic 服务代理
		ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		if (noUnknown(ipAddresses)) {
			return ipAddresses;
		}

		//HTTP_CLIENT_IP：有些代理服务器
		ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		if (noUnknown(ipAddresses)) {
			return ipAddresses;
		}

		//X-Real-IP：nginx服务代理
		ipAddresses = request.getHeader("X-Real-IP");
		if (noUnknown(ipAddresses)) {
			return ipAddresses;
		}
		return "";
	}

	/**
	 * 获取http客户端IP，如果有代理链，则返回第一个IP（一般为客户端的真实IP）
	 *
	 * @param request
	 * @return 122.1.35.5
	 */
	public static String getClientIp(HttpServletRequest request) {
		List<String> fs = getClientChain(request);
		if (null != fs && !fs.isEmpty()) {
			return fs.get(0);
		}
		return "";
	}

	/**
	 * 获取http直接调用方IP，有代理服务器时一般为最后一个代理服务器的IP
	 *
	 * @param request
	 * @return 192.168.5.94
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * 是否为空或unknown
	 *
	 * @param ip
	 * @return
	 */
	private static boolean noUnknown(String ip) {
		return StringUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
	}

}
