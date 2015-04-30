package com.example.testandroiserver.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.testandroiserver.net.NanoHTTPD.Response.Status;


public class WebServer extends NanoHTTPD {
   
	private String hostname;
	private int port;
	public Response response;
	private String uri;
	String vid="test";
	String readtoken;String writetoken;String privatekey;String userid;
	public WebServer(String hostname,int port) {
		super(hostname,port);
		// TODO Auto-generated constructor stub
		this.hostname=hostname;
		this.port=port;
	}
	
	
	public void initSetting(String readtoken,String writetoken,String privatekey,String userid){
	   this.readtoken=readtoken;
	   this.writetoken=writetoken;
	   this.privatekey=privatekey;
	   this.userid=userid;
	}
	
	public InputStream download(String uri){
		HttpGet httpget = new HttpGet(uri);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpget.addHeader("User-Agent", "polyv-android-sdk");
		HttpResponse httpresponse = null;
		InputStream inStream=null;
		try {
			httpresponse = httpclient.execute(httpget);
			HttpEntity entity = httpresponse.getEntity();
			inStream = entity.getContent();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inStream;
	}
	
	//....
	/**
	 *vid -> http://localhost:10591/vid.m3u8
	 * **/
	@Override
	public Response serve(IHTTPSession session) {
		// TODO Auto-generated method stub
	
		String uri = session.getUri();
		
		if(isM3U8Indexmacth(uri)){
			System.out.println("M3U8Index match");
			System.out.println("Uri->"+uri);
			InputStream inStream= download("http://seg1.videocc.net/hls/sl8da4jjbx/0/sl8da4jjbx43fdf31ac49dab61b19aa0_1.m3u8");
			String content = stream2String(inStream, "UTF-8");
			System.out.println("content -->"+content);
			return new Response(content);
		}else{
			System.out.println("other");
			System.out.println("Uri->"+uri);
			InputStream inStream= download(uri);
			String content = stream2String(inStream, "UTF-8");
			System.out.println("content -->"+content);
			return new Response(content);
		}
		
	    /*Map<String, String> parms = session.getParms();
		if(isM3U8Indexmacth(uri)){
			System.out.println("M3U8Index match");
			System.out.println("Uri->"+uri);
			InputStream inStream= download(uri);
			String content = stream2String(inStream, "UTF-8");
			System.out.println("index -->"+content);
			return new Response(Status.OK, "text/html", inStream);
		}
	    if(isM3U8match(uri)){
			System.out.println("M3U8 match");
			InputStream inStream= download("http://demo.polyv.net/hlstest/test2.m3u8");
			String content = stream2String(inStream, "UTF-8");
			String newcontent = content.replaceAll("URI=\"key\"","/hls/"+vid+".key");
			System.out.println("m3u8 -->"+newcontent);
			return new Response(Status.OK,"text/html",inStream);
		}
	    
		if(isKeymatch(uri)){
			System.out.println("Key match");
			InputStream inStream = download("http://demo.polyv.net/hlstest/test.key");
			String content = stream2String(inStream, "UTF-8");
			System.out.println("key match->"+content);
//			return new Response(content);
			return new Response(Status.OK,"text/html",inStream);
		}*/
		
	  //  return new Response("");
	}
	
	public boolean isM3U8Indexmacth(String uri){
		Pattern p = Pattern.compile("^/hls/([0-9a-z_]{32})\\.m3u8$");
		Matcher m = p.matcher(uri);
		return m.matches();
	}
	
	public boolean isM3U8match(String uri){
		Pattern p = Pattern.compile("^/hlstest/([0-9a-z_]{5})\\.m3u8$");
		 Matcher m = p.matcher(uri);
		return m.matches();
	}
	
	public boolean isKeymatch(String uri){
		Pattern p = Pattern.compile("^/hls/([0-9a-z_]{32})_([0-9a-z]{1})\\.key$");
		 Matcher m = p.matcher(uri);
		return m.matches();
	}
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private static String stream2String(InputStream inputStream, String charset) {
		// TODO Auto-generated method stub
		try {
			if(inputStream == null){
				return "";
			}
			byte[] buffer  = new byte[1024];
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int len = 0;
			while((len = inputStream.read(buffer)) != -1){
				bout.write(buffer, 0, len);
			}
			String result = new String(bout.toByteArray(),charset);
			inputStream.close();
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public static void main(String[] args) {
//        ServerRunner.run(WebServer.class);
        WebServer server  = new WebServer("127.0.0.1",12111);
        ServerRunner.executeInstance(server);

    }
}
