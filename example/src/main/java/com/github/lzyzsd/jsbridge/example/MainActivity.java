package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.github.lzyzsd.jsbridge.example.logger.AppLogger;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	//private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

	BridgeWebView webView;

	Button button1;
	Button button2;

	int RESULT_CODE = 0;

	ValueCallback<Uri> mUploadMessage;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AppLogger.info("hello world");

		webView = (BridgeWebView) findViewById(R.id.webView);

		button1 = (Button) findViewById(R.id.button1); // Java调用Web
		button2 = (Button) findViewById(R.id.button2); // 加入会议

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);

		webView.setDefaultHandler(new DefaultHandler());

		webView.setWebChromeClient(new WebChromeClient() {

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
				this.openFileChooser(uploadMsg);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
				this.openFileChooser(uploadMsg);
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				pickFile();
			}
		});

		webView.loadUrl("file:///android_asset/demo.html");

		webView.registerHandler("submitFromWeb", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				AppLogger.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
			}

		});

		webView.registerHandler("joinYmsMeeting", new BridgeHandler() {
			@Override
			public void handler(String data, CallBackFunction function) {
				AppLogger.i(TAG, "handler = joinYmsMeeting, data from web = " + data);
				if (!ApkUtils.checkApkInstalled(MainActivity.this, "com.yealink.vc.mobile.yms")) {
					AppLogger.info("apk not installed");
					function.onCallBack("apk not installed");
					return;
				}
				//ApkUtils.startApp(this, "com.yealink.vc.mobile", "com.yealink.main.StartActivity");
				boolean ret = ApkUtils.joinYmsMeeting(MainActivity.this);
				function.onCallBack("call yms ret = " + ret);
			}
		});

        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        webView.send("hello");

	}

	public void pickFile() {
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
		chooserIntent.setType("image/*");
		startActivityForResult(chooserIntent, RESULT_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == RESULT_CODE) {
			if (null == mUploadMessage){
				return;
			}
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}

	@Override
	public void onClick(View v) {
		AppLogger.info("onClick {} / {}", 1, 2);
		AppLogger.d(TAG, "onClick now");
		if (button1.equals(v)) {
			AppLogger.info("button1 onClick");
            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {

				@Override
				public void onCallBack(String data) {
					// TODO Auto-generated method stub
					AppLogger.i(TAG, "reponse data from js " + data);
				}

			});
		}

		if (button2.equals(v)) {
			AppLogger.info("button2 onClick");
			for (int i = 0; i < 100; i++) {
				AppLogger.info("message loop for xxxsdddddddddddddddddddddddddddd index {}", i);
			}
			if (!ApkUtils.checkApkInstalled(this, "com.yealink.vc.mobile.yms")) {
				AppLogger.info("apk not installed");
				return;
			}
			//ApkUtils.startApp(this, "com.yealink.vc.mobile", "com.yealink.main.StartActivity");
			ApkUtils.joinYmsMeeting(this);
		}
	}

}
