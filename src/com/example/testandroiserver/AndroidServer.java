package com.example.testandroiserver;

import com.example.testandroiserver.net.ServerRunner;
import com.example.testandroiserver.net.WebServer;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AndroidServer extends IntentService {
    private WebServer server;
	private static final String TAG="AndroidServer";
	//无参数构造函数，调用父类的super(String name)
	public AndroidServer() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.i("TAG","service started");
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("TAG","service onStartCommand");
		flags= START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(getApplicationContext(), "server start", 1).show();
		Log.i("TAG","service created");
		server = new WebServer("127.0.0.1", 1234);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("TAG","service onHandleIntent");
		boolean open=intent.getBooleanExtra("isOpen", false);
		if(open){
			Log.i("TAG","server run");
			ServerRunner.executeInstance(server);
		}else {
			
		}
	}
     
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "server destory", 1).show();
		Log.i(TAG, "server destory");
			}
}
