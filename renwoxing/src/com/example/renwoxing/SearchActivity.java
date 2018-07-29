package com.example.renwoxing;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.renwoxing.adapter.LineItemAdapter;
import com.example.renwoxing.domain.GlobalParameter;
import com.example.renwoxing.domain.SuggestModel;
import com.example.renwoxing.domain.SuggestModel1;
import com.example.renwoxing.domain.SuggestModel2;
import com.example.renwoxing.util.WifiUtil;

public class SearchActivity extends Activity {
	private LineItemAdapter adapter;
	private List<SuggestModel> items;
	private ListView listViewLines;
	private Context context;
	private GlobalParameter global;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		context = this;
		listViewLines=(ListView)findViewById(R.id.listViewLines);
		//连接服务器，网关地址
		String gateway=WifiUtil.getGateWay(this);
		GlobalParameter.globalUrl="http://"+gateway+":8080/renwoxing";
//		Toast.makeText(getApplicationContext(), GlobalParameter.globalUrl,Toast.LENGTH_LONG).show();
//		System.out.println(GlobalParameter.globalUrl);
		try {
			new LoadData().execute("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "服务器访问失败",Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	class LoadData extends AsyncTask<String, String, String>{
		ProgressDialog progressDialog;// 定义一个进度对话框

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "加载中", "正在加载中，请稍后");
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String response_string = null;
			// TODO Auto-generated method stub
			try {
				HttpPost httpPost = new HttpPost(GlobalParameter.globalUrl + "/ShortPathServlet");
				// 建立HttpClient访问网络
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// 通过httpClient执行HttpPost的访问
				httpClient.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, 15 * 1000);
				// 设置POST表单参数
//				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//				nvps.add(new BasicNameValuePair("page", "1"));
//				nvps.add(new BasicNameValuePair("rows", "10"));
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				System.out.println(httpPost);
				// 执行HTTP请求
				HttpResponse httpResponse = httpClient.execute(httpPost);
//				Toast.makeText(getApplicationContext(),httpResponse.getStatusLine().getStatusCode() ,Toast.LENGTH_LONG).show();
//				System.out.println(httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 获取响应的实体
					HttpEntity entity = httpResponse.getEntity();
					response_string = EntityUtils.toString(entity,"utf-8");
				}else{
					Toast.makeText(getApplicationContext(), "网络访问失败",Toast.LENGTH_LONG ).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(response_string);
			return response_string;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					
					Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG ).show();
					System.out.println(result);
					JSONObject json = JSON.parseObject(result);
					JSONObject rows1=JSON.parseObject(json.getString("1"));
					JSONObject rows2=JSON.parseObject(json.getString("2"));
					
					if(rows1!=null){
						String rows1_1=rows1.getString("1");
						List<SuggestModel1> suggestModel1_1=JSON.parseArray(rows1_1,SuggestModel1.class);
//						SuggestModel1 suggestModel1_1=(SuggestModel1)JSONObject.toJavaObject(JSON.toJSONString(rows1_1), SuggestModel1.class);
//						SuggestModel1 suggestModel1_1 = (SuggestModel1) JSON.parseArray(rows1_1,SuggestModel1.class);
						String rows1_3=rows1.getString("3");
						SuggestModel2 suggestModel1_3=(SuggestModel2) JSON.parseArray(rows1_3,SuggestModel2.class);
//						SuggestModel suggestModel1=new SuggestModel(suggestModel1_1,suggestModel1_3);
//						items = (List<SuggestModel>) suggestModel1;
					}
					if(rows2!=null){
						String rows2_1=rows2.getString("1");
						
						SuggestModel1 suggestModel2_1=JSONObject.toJavaObject(JSON.parseObject(rows2_1), SuggestModel1.class);
						String rows2_3=rows1.getString("3");
						SuggestModel2 suggestModel2_3=(SuggestModel2) JSON.parseArray(rows2_3,SuggestModel2.class);
						SuggestModel suggestModel2=new SuggestModel(suggestModel2_1,suggestModel2_3);
						items.add(suggestModel2);
					}
					adapter = new LineItemAdapter(context, items);
					listViewLines.setAdapter(adapter);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "连接服务器失败，返回数据为空",Toast.LENGTH_LONG ).show();
				}
				}
			
			progressDialog.dismiss();
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
