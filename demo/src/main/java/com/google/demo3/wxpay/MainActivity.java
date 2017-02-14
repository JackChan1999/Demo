package com.google.demo3.wxpay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.demo3.R;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		api = WXAPIFactory.createWXAPI(this, "wxb4ba3c02aa476ea1");
	}
	
	public void checkWeixinInstall(View v) {
		boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
		Toast.makeText(this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
	}

	public void weixinPay(View v) {
		String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
		v.setEnabled(false);
		Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();
		
		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				weixinPay(response);
			}
		};
		ErrorListener errorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("PAY_GET", "异常："+error.getMessage());
	        	Toast.makeText(MainActivity.this, "异常："+error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		};
		Request<String> request = new StringRequest(url, listener, errorListener);
		Volley.newRequestQueue(this).add(request);
        v.setEnabled(true);
	}

	protected void weixinPay(String content) {
		Log.e("get server pay params:",content);
    	JSONObject json;
		try {
			json = new JSONObject(content);
			if(null != json && !json.has("retcode") ){
				PayReq req = new PayReq();
//				req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
				req.appId			= json.getString("appid");
				req.partnerId		= json.getString("partnerid");
				req.prepayId		= json.getString("prepayid");
				req.nonceStr		= json.getString("noncestr");
				req.timeStamp		= json.getString("timestamp");
				req.packageValue	= json.getString("package");
				req.sign			= json.getString("sign");
				req.extData			= "app data"; // optional
				System.out.println("packageValue = " + req.packageValue);
				Toast.makeText(this, "正常调起支付", Toast.LENGTH_SHORT).show();
				// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
				api.sendReq(req);
			}else{
	        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
	        	Toast.makeText(this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			Log.d("PAY_GET", "服务器请求错误");
        	Toast.makeText(MainActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
		} 
	}

}
