package com.example.renwoxing;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.renwoxing.domain.GlobalParameter;
import com.example.renwoxing.util.WifiUtil;
public class LineActivity extends Activity {
//	private LineItemAdapter mAdapter;
	private Context context;
	private GlobalParameter global;
	private TextView tv_address,tv_spot,tv_hotel,tv_restaurant2;
//	private Button bt_s;
	String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line);
		tv_address=(TextView)findViewById(R.id.tv_restaurant);
		tv_spot=(TextView)findViewById(R.id.tv_spot);
		tv_hotel=(TextView)findViewById(R.id.tv_hotel);
		tv_restaurant2=(TextView)findViewById(R.id.tv_restaurant2);
//		Button btn_s=(Button)findViewById(R.id.btn_s);
		String gateway=WifiUtil.getGateWay(this);
		GlobalParameter.globalUrl="http://"+gateway+":8080/renwoxing";
		System.out.println(GlobalParameter.globalUrl + "/ShortPathServlet");
		Toast.makeText(getApplicationContext(), "服务器的IP地址："+GlobalParameter.globalUrl+"\n本机IP地址："+WifiUtil.getIp(this), Toast.LENGTH_LONG).show();
		try {
			new LoadData().execute("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "服务器访问失败",Toast.LENGTH_LONG ).show();
		}
//		try {
//			new LoadData().execute("");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Toast.makeText(getApplicationContext(), "服务器访问失败",Toast.LENGTH_LONG ).show();
//		}
		//mAdapter=new LineItemAdapter(this,R.layout.row_list_line);
		//ListView lv_line=(ListView)findViewById(R.id.lv_line);
		context = this;
//		String gateway=WifiUtil.getGateWay(this);
//		GlobalParameter.globalUrl="http://"+gateway+":8080/GLDShop/";
//		try {
//			global =((GlobalParameter) getApplicationContext());
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		address=(String)tv_address.getText();
//		tv_address.setClickable(true);
//		tv_address.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Bundle bundle=new Bundle();
//				bundle.putString("address", address);
////				bundle.putString("address_city",address_city);
////				Intent intent=new Intent(this.context,GoodsDetailActivity.class);
//				
////				this.context.startActivity(intent);
//				Intent intent=new Intent(LineActivity.this,GeoCoderDemo.class);
//				intent.putExtras(bundle);
//				startActivity(intent);
//			}
//			
//		});
//		btn_s.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				Bundle bundle=new Bundle();
////				bundle.putString("address", address);
////				bundle.putString("address_city",address_city);
////				Intent intent=new Intent(this.context,GoodsDetailActivity.class);
//				
////				this.context.startActivity(intent);
////				Intent intent=new Intent(LineActivity.this,GeoCoderDemo.class);
//////				intent.putExtras(bundle);
////				startActivity(intent);
////				new LoadData().execute("");
//			}
//			
//		});
	}
class LoadData extends AsyncTask<String, String, String>{
		
		ProgressDialog progressDialog;// 定义一个进度对话框

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
//			super.onPreExecute();
//			progressDialog = ProgressDialog.show(context, "加载中", "正在加载中，请稍后");
		}

		@Override
		protected String doInBackground(String... arg0) {
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
				
//				System.out.println(HttpStatus.SC_OK);
//				HttpEntity entity = httpResponse.getEntity();
//				response_string = EntityUtils.toString(entity, "utf-8");
//				System.out.println("response_string:===================="+response_string);
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
			return response_string;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result!=null){
				try {
					JSONObject json = JSON.parseObject(result);
					//路线1
					JSONObject rows = JSON.parseObject(json.getString("1"));
					if(rows!=null){
//						System.out.println("rows---"+rows);
						//上午
						JSONObject rows1 = JSON.parseObject(rows.getString("1"));
						if(rows1!=null){
//							System.out.println("rows1----"+rows1);
							//hotelModel
//							if(rows1.getString("hotelModel")!=null){
//								HotelModel hotelModel = JSON.parseObject(rows1.getString("hotelModel"), HotelModel.class);
//								System.out.println("hotelModel"+rows1.getString("hotelModel"));
//							}
							JSONObject restaurantModel = JSON.parseObject(rows1.getString("restaurantModel"));
							System.out.println(restaurantModel);
//							System.out.println(restaurantModel.getString("restaurantAddress"));
							JSONObject spotModel = JSON.parseObject(rows1.getString("spotModel"));
							tv_spot.setText("景点："+spotModel.getString("spotName"));
							tv_address.setText("餐厅："+restaurantModel.getString("restaurantName"));
							
							
//							RestaurantModel restaurantModel = JSON.parseObject(rows1.getString("restaurantModel"), RestaurantModel.class);
//							if(rows1.getString("restaurantModel")!=null){
//								RestaurantModel restaurantModel = JSON.parseObject(rows1.getString("restaurantModel"), RestaurantModel.class);
//								System.out.println("restaurantModel"+rows1.getString("restaurantModel"));
//							}
//							if(rows1.getString("shopModel")!=null){
//								ShopModel shopModel = JSON.parseObject(rows1.getString("shopModel"), ShopModel.class);
//								System.out.println("shopModel"+rows1.getString("restaurantModel"));
//							}
//							if(rows1.getString("spotModel")!=null){
//								SpotModel spotModel = JSON.parseObject(rows1.getString("spotModel"), SpotModel.class);
//								System.out.println("spotModel"+rows1.getString("spotModel"));
//							}
								
						}
						//下午
						JSONObject rows3 = JSON.parseObject(rows.getString("3"));
						System.out.println(rows3);
						if(rows3!=null){
							
							JSONObject restaurantModel = JSON.parseObject(rows3.getString("restaurantModel"));
							tv_restaurant2.setText("晚餐："+restaurantModel.getString("restaurantName"));
							JSONObject hotelModel = JSON.parseObject(rows3.getString("hotelModel"));
							tv_hotel.setText("住宿："+hotelModel.getString("hotelName"));
							
						}
						
					}else{
						Toast.makeText(getApplicationContext(), "rows为空",Toast.LENGTH_LONG ).show();
					}
					
					
					
					
					//restaurantModel
					//shopModel
					//spotModel
					
//					JSONObject restaurantModel = JSON.parseObject(rows1.getString("restaurantModel"));
//					System.out.println("restaurantModel---"+restaurantModel);
//					List<RestaurantModel> restaurantModels = JSON.parseArray(restaurantModel, RestaurantModel.class);
//					String rows4=json.getString("3");
//					System.out.println("4:======"+rows4);
//					int total = json.getIntValue("total");
//					String rows = json.getString("rows");
//					List<Goods> goods = JSON.parseArray(rows, Goods.class);
//					items = goods;
//					adapter = new GoodsItemAdapter(context, items);
//					listViewNews.setAdapter(adapter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Toast.makeText(getApplicationContext(), "连接服务器失败，返回数据为空 in Line",Toast.LENGTH_LONG ).show();
			}
//			progressDialog.dismiss();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.line, menu);
		return true;
	}

}
