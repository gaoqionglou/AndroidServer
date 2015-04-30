package com.example.testandroiserver;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
   private Button startServer,connect,videoplay;
   AndroidConnection connection= new AndroidConnection();
   private String vid="sl8da4jjbx0bbe98bc3edfd2307fdbde";
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	startServer =(Button)findViewById(R.id.startServer);
    	connect=(Button)findViewById(R.id.connect);
    	videoplay =(Button)findViewById(R.id.videoplay);
    	startServer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//¿ªÆôservice
				Intent service  = new Intent(MainActivity.this,AndroidServer.class);
				service.putExtra("isOpen", true);
				bindService(service, connection, BIND_AUTO_CREATE);
				startService(service);
			}
		});
    	
    	connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new TestConnect().execute();
			}
		});
     
    	videoplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,IjkVideoActicity.class);
				startActivity(intent);
			}
		});
    }
	
	class TestConnect extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				HttpGet httpget = new HttpGet("http://127.0.0.1:1234/hls/"+"sl8da4jjbx0bbe98bc3edfd2307fdbde"+".m3u8");
				httpget.addHeader("User-Agent", "polyv-android-sdk");
				HttpResponse response = httpclient.execute(httpget);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}
		
	}
	
	
	class AndroidConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
