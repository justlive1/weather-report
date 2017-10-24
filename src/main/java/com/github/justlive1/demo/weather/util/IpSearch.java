package com.github.justlive1.demo.weather.util;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * ip解析
 * 
 * @author wubo
 *
 */
public class IpSearch {

	private int offset;
	private int[] index = new int[65536];
	private ByteBuffer dataBuffer;
	private ByteBuffer indexBuffer;
	private ReentrantLock lock = new ReentrantLock();
	private String url = null;
	private File local;
	private int loadCount = 0;
	private static final ScheduledExecutorService EXE = Executors.newScheduledThreadPool(1);

	public static IpSearch local(String path) {
		IpSearch ip = new IpSearch();
		File file = new File(path);
		ip.local = file;
		ip.load0();
		return ip;
	}

	public static IpSearch local(File file) {
		IpSearch ip = new IpSearch();
		ip.local = file;
		ip.load0();
		return ip;
	}

	public static IpSearch of(String url) {
		return of(url, null);
	}

	public static IpSearch of(String url, File file) {
		IpSearch ip = new IpSearch();
		ip.url = url;
		ip.local = file;
		ip.load0();
		return ip;
	}

	public String[] find(String ip) {
		String[] ips = ip.split("\\.");
		int prefixValue = (Integer.valueOf(ips[0]) * 256 + Integer.valueOf(ips[1]));
		long ip2longValue = ip2long(ip);
		int start = index[prefixValue];
		int maxCompLen = offset - 262144 - 4;
		long tmpInt;
		long indexOffset = -1;
		int indexLength = -1;
		byte b = 0;
		for (start = start * 9 + 262144; start < maxCompLen; start += 9) {
			tmpInt = int2long(indexBuffer.getInt(start));
			if (tmpInt >= ip2longValue) {
				indexOffset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
				indexLength = (0xFF & indexBuffer.get(start + 7) << 8) + (0xFF & indexBuffer.get(start + 8));
				break;
			}
		}

		byte[] areaBytes;

		lock.lock();
		try {
			dataBuffer.position(offset + (int) indexOffset - 262144);
			areaBytes = new byte[indexLength];
			dataBuffer.get(areaBytes, 0, indexLength);
		} finally {
			lock.unlock();
		}

		return new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
	}

	public String findCity(String ip) {

		String[] arr = this.find(ip);
		if (arr != null && arr.length > 2) {
			return arr[2];
		}

		return null;
	}

	private IpSearch() {

	}

	private void load0() {

		load();
		if (url != null) {
			watch();
		}
	}

	private void watch() {
		EXE.scheduleAtFixedRate(() -> load(), 1, 1, TimeUnit.DAYS);
	}

	private void load() {

		byte[] bytes = null;
		try {
			if (loadCount == 0 && local != null && local.exists() && local.isFile()) {
				bytes = Files.toByteArray(local);
			} else {
				bytes = Resources.asByteSource(new URL(url)).read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (bytes == null) {
			return;
		}

		lock.lock();
		try {

			dataBuffer = ByteBuffer.wrap(bytes);
			dataBuffer.position(0);
			offset = dataBuffer.getInt();
			byte[] indexBytes = new byte[offset];
			dataBuffer.get(indexBytes, 0, offset - 4);
			indexBuffer = ByteBuffer.wrap(indexBytes);
			indexBuffer.order(ByteOrder.LITTLE_ENDIAN);

			for (int i = 0; i < 256; i++) {
				for (int j = 0; j < 256; j++) {
					index[i * 256 + j] = indexBuffer.getInt();
				}
			}
			indexBuffer.order(ByteOrder.BIG_ENDIAN);
		} finally {
			lock.unlock();
		}
		loadCount++;
	}

	private long bytesToLong(byte a, byte b, byte c, byte d) {
		return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
	}

	private int str2Ip(String ip) {
		String[] ss = ip.split("\\.");
		int a, b, c, d;
		a = Integer.parseInt(ss[0]);
		b = Integer.parseInt(ss[1]);
		c = Integer.parseInt(ss[2]);
		d = Integer.parseInt(ss[3]);
		return (a << 24) | (b << 16) | (c << 8) | d;
	}

	private long ip2long(String ip) {
		return int2long(str2Ip(ip));
	}

	private long int2long(int i) {
		long l = i & 0x7fffffffL;
		if (i < 0) {
			l |= 0x080000000L;
		}
		return l;
	}
}
