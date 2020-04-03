package com.hummingbird.maaccount.util.huiTongBusinessUtil.socketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SocketBean {
    
    private static final Log    log    = LogFactory.getLog(SocketBean.class);

	private Socket socket;  //socket对象

	private BufferedReader bf;// 输入流

	private PrintWriter printWrite;// 输出流

	// 创建socket连接
	private SocketBean(String url, int port, int soTimeout,String inputCharset,boolean openSSL) throws Exception {

		if (soTimeout == -1) {
			soTimeout = 8000;
		}
		try {
			
			initSocket(url, port, openSSL);

			socket.setSoTimeout(soTimeout);

			printWrite = new PrintWriter(socket.getOutputStream(), true);

			bf = new BufferedReader(new InputStreamReader(socket.getInputStream(),inputCharset));
		} catch (Exception e) {
			closeSocket();
			throw e;
		}
	}

	public static SocketBean create(String url, int port,String inputCharset,boolean openSSL) throws Exception {

		return new SocketBean(url, port, -1,inputCharset,openSSL);

	}

	public static SocketBean create(String url, int port, int soTimeout,String inputCharset,boolean openSSL)
			throws Exception {

		return new SocketBean(url, port, soTimeout,inputCharset,openSSL);

	}

	// 获取一行数据，返回的数据必须有回车换行符号，否则会卡死
	public String readLine() throws IOException {
		return bf.readLine();
	}
	
	//根据传入的size大小获取数据
	public byte[]  read(int size) throws IOException {
		byte[] buff=new byte[size];
		socket.getInputStream().read(buff);
		return buff;
	}

	//向远程服务器发送数据
	public void write(String text) {
		printWrite.println(text);
		printWrite.flush();
	}

	//关闭资源
	public void closeSocket() {
	    log.info("socket正在关闭资源。。。");
		if (printWrite != null) {
			printWrite.close();
		}
		if (bf != null) {
			try {
				bf.close();
			} catch (IOException e) {

			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {

			}
		}

	}

	//初始化SSL
	private SSLSocketFactory initSSLSocketFactory()
			throws NoSuchAlgorithmException, KeyManagementException {
		// 初始化
		SSLContext s = SSLContext.getInstance("SSL");
		// 信任所有
		s.init(null, new TrustManager[] { getX509TrustManager() },
				new java.security.SecureRandom());

		return s.getSocketFactory();
	}
	
	//信任所有
	private X509TrustManager getX509TrustManager(){
		
		    X509TrustManager xtm = new X509TrustManager() { // ����TrustManager

			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		
		return xtm;
	}
	
	//初始化socket对象
	private void initSocket(String url, int port,boolean openSSL) throws Exception{
		
		if(openSSL){
			
			SSLSocketFactory factory = initSSLSocketFactory();

			socket = (SSLSocket) factory.createSocket(url, port);
			
		}else{
			socket=new Socket(url, port);
		}
		log.info("socket初始化成功。。。");
	}

	public Socket getSocket() {
		return socket;
	}
	
	


}
