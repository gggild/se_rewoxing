package com.example.renwoxing;

import java.util.ArrayList;
import java.util.Map;

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
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.renwoxing.domain.GlobalParameter;
import com.example.renwoxing.util.WifiUtil;

/**
 * ��demo����չʾ��ν��е�������������õ�ַ�������꣩����������������������������ַ��
 */
public class GeoCoderActivity extends Activity implements
OnGetGeoCoderResultListener {
	private Context context;
	private GlobalParameter global;
	GeoCoder mSearch1 = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	GeoCoder mSearch2 = null;
	GeoCoder mSearch3 = null;
	GeoCoder mSearch4 = null;
	GeoCoder mSearch5 = null;
	
	ArrayList<GeoCoder> mSearch = new ArrayList<GeoCoder>();		//��̬����ģ������
	
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	LatLng stLatlng;
	LatLng enLatlng;
	static LatLng latlng;
	Bundle bundle1;
	// ��ͨ���ߣ����ʱ�ı���
	Polyline mPolyline;
	InfoWindow mInfoWindow;
	// ��ʼ��ȫ�� bitmap ��Ϣ������ʱ��ʱ recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);
	BitmapDescriptor bdB = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markb);
	BitmapDescriptor bdC = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markc);
	BitmapDescriptor bdD = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markd);
	private View pop;
	private String restaurant1,restaurant2,spot,hotel,shop,spot1;
	private TextView tv_title,tv_desc;//pop����
	MarkerOptions option;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geocoder);
		CharSequence titleLable = "������ַ";
		setTitle(titleLable);
		context = this;
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapGeocoderView);
		mBaiduMap = mMapView.getMap();
		
		// ��ʼ������ģ�飬ע���¼�����
		mSearch1 = GeoCoder.newInstance();
		mSearch2 = GeoCoder.newInstance();
		mSearch3 = GeoCoder.newInstance();
		mSearch4 = GeoCoder.newInstance();
		mSearch5 = GeoCoder.newInstance();

		mSearch1.setOnGetGeoCodeResultListener(this);
		mSearch2.setOnGetGeoCodeResultListener(this);
		mSearch3.setOnGetGeoCodeResultListener(this);
		mSearch4.setOnGetGeoCodeResultListener(this);
		mSearch5.setOnGetGeoCodeResultListener(this);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		
		//���ӷ����������ص�ַ
		String gateway=WifiUtil.getGateWay(this);
		GlobalParameter.globalUrl="http://"+gateway+":8080/renwoxing";
		Toast.makeText(getApplicationContext(), "��ַ��http://"+gateway+":8080/renwoxing",Toast.LENGTH_LONG ).show();
		try {
			new LoadData().execute("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "����������ʧ��",Toast.LENGTH_LONG ).show();
		}

	}
class LoadData extends AsyncTask<String, String, String>{
		
		ProgressDialog progressDialog;// ����һ�����ȶԻ���

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			String response_string = null;
			// TODO Auto-generated method stub
			try {
				HttpPost httpPost = new HttpPost(GlobalParameter.globalUrl + "/ShortPathServlet");
						
				// ����HttpClient��������
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// ͨ��httpClientִ��HttpPost�ķ���
				httpClient.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, 15 * 1000);
				// ����POST������
//				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//				nvps.add(new BasicNameValuePair("page", "1"));
//				nvps.add(new BasicNameValuePair("rows", "10"));
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				System.out.println(httpPost);
				// ִ��HTTP����
				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// ��ȡ��Ӧ��ʵ��
					HttpEntity entity = httpResponse.getEntity();
					response_string = EntityUtils.toString(entity,"utf-8");
				}else{
					Toast.makeText(getApplicationContext(), "�������ʧ��",Toast.LENGTH_LONG ).show();
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

			/*
			if(result!=null){
				try {
					JSONObject json = JSON.parseObject(result);
					//·��1
					JSONObject rows = JSON.parseObject(json.getString("1"));
					if(rows!=null){
//						System.out.println("rows---"+rows);
						//����
						JSONObject rows1 = JSON.parseObject(rows.getString("1"));
//						String rows1 = rows.getString("1");
						
						if(rows1!=null){
//							List<SuggestModel> suggestModel = JSON.parseArray(rows1, SuggestModel.class);
//							System.out.println("rows1----"+rows1);
							//hotelModel
//							if(rows1.getString("hotelModel")!=null){
//								HotelModel hotelModel = JSON.parseObject(rows1.getString("hotelModel"), HotelModel.class);
//								System.out.println("hotelModel"+rows1.getString("hotelModel"));
//							}
							JSONObject restaurantModel = JSON.parseObject(rows1.getString("restaurantModel"));
							mSearch1.geocode(new GeoCodeOption().city("����").address(restaurantModel.getString("restaurantName")));
							restaurant1= restaurantModel.getString("restaurantName");
							JSONObject spotModel = JSON.parseObject(rows1.getString("spotModel"));
							spot= spotModel.getString("spotName");
//							System.out.println(spotModel.getString("�ķ���"));
							mSearch2.geocode(new GeoCodeOption().city("����").address(spotModel.getString("spotName")));
//							tv_spot.setText("���㣺"+spotModel.getString("spotName"));
//							tv_address.setText("������"+restaurantModel.getString("restaurantName"));
							
							
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
						//����
						JSONObject rows3 = JSON.parseObject(rows.getString("3"));
//						System.out.println(rows3);
						if(rows3!=null){
							
							JSONObject restaurantModel = JSON.parseObject(rows3.getString("restaurantModel"));
//							tv_restaurant2.setText("��ͣ�"+restaurantModel.getString("restaurantName"));
							restaurant2=restaurantModel.getString("restaurantName");
							mSearch3.geocode(new GeoCodeOption().city("����").address(restaurantModel.getString("restaurantName")));
//							bundle1=new Bundle();
//							bundle1.putString("restaurantAddress", restaurantModel.getString("restaurantAddress"));
//							bundle1.putString("restaurantDescribe", restaurantModel.getString("restaurantDescribe"));
//							System.out.println(restaurantModel.getString("restaurantAddress"));
							JSONObject hotelModel = JSON.parseObject(rows3.getString("hotelModel"));
							hotel=hotelModel.getString("hotelName");
							mSearch4.geocode(new GeoCodeOption().city("����").address(hotelModel.getString("hotelName")));
//							System.out.println(hotelModel.getString("hotelAddress"));
//							tv_hotel.setText("ס�ޣ�"+hotelModel.getString("hotelName"));
							JSONObject shopModel = JSON.parseObject(rows3.getString("shopModel"));
							shop=shopModel.getString("shopName");
							mSearch5.geocode(new GeoCodeOption().city("����").address(shopModel.getString("shopName")));
							JSONObject spotModel = JSON.parseObject(rows3.getString("spotModel"));
							spot1=spotModel.getString("spotName");
							mSearch5.geocode(new GeoCodeOption().city("����").address(spotModel.getString("spotName")));
						}
						
					}else{
						Toast.makeText(getApplicationContext(), "rowsΪ��",Toast.LENGTH_LONG ).show();
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
				Toast.makeText(getApplicationContext(), "���ӷ�����ʧ�ܣ���������Ϊ��",Toast.LENGTH_LONG ).show();
			}
			*/
			
//			Toast.makeText(getApplicationContext(), "׼����������",Toast.LENGTH_LONG ).show();
			if(result != null){
				try{
					
					/*=====================================================================================================*/
					/*=====================================================================================================*/
					/*=========================================��ʼ����json����=============================================*/
					/*=====================================================================================================*/
					/*=====================================================================================================*/
						
					Toast.makeText(getApplicationContext(), "���ݣ�" + result,Toast.LENGTH_LONG ).show();
					
					
					
						
						
						JSONObject jsonObject2 = JSON.parseObject(result);
						Map<String, Object> jsmap = jsonObject2.getInnerMap();
						
						/*
						System.out.println("========================================��ʼ����ͼ��������==============================================");
						
						
						
						
						for(Map.Entry<String, Object> entry : jsmap.entrySet()){
							//��ȡjson��һ�����ݣ���·��
							System.out.println("Key = " + entry.getKey());//��ʾ�ǵڼ���·�߼����ܵĻ���
							
							//��ȡ�ڶ������ݣ���������suggestmodel��map
							JSONArray spotlist = JSONObject.parseArray(entry.getValue().toString());
							
							int days = 1;//����suggestmap���������key����13��ʱ��˵������Ҫ��1
							System.out.println("��" + days + "��:");
							System.out.println("����:");
							
							
//							System.out.println(spotlist.size());
							for(int i = 0; i < spotlist.size(); ++i){
//								System.out.println(spotlist.get(i));
								//��ȡ���������ݣ���suggestmodel
								Map<String, Object> suggestMap = (Map<String, Object>) spotlist.get(i);
								
								for(Map.Entry<String, Object> entry2 : suggestMap.entrySet()){
									
//									System.out.println("Key = " + entry2.getKey() + ", Value = " + entry2.getValue());\\
//									System.out.println("Key = " + entry2.getKey());
									
									
									
									//��ȡsuggest�еĸ���model����
									JSONObject map_3 = JSONObject.parseObject(JSONObject.toJSONString(entry2.getValue()));
									
									JSONObject map_spot = JSONObject.parseObject(JSONObject.toJSONString(map_3.get("spotModel")));
									if(map_spot != null){
										
										
										
										
										
										
										
										
										
										GeoCoder temp;
										mSearch1 = GeoCoder.newInstance();
										

										mSearch1.setOnGetGeoCodeResultListener(this);
										
										mSearch1.geocode(new GeoCodeOption().city("����").address(map_restaurant.getString("restaurantName")));
										
										
										
										
										
										
										System.out.println("spotSuggestTime:" + map_spot.get("spotSuggestTime"));
										System.out.println("spotPrice:" + map_spot.get("spotPrice"));
										System.out.println("spotScore:" + map_spot.get("spotScore"));
										System.out.println("spotTime:" + map_spot.get("spotTime"));
										System.out.println("spotDescribe:" + map_spot.get("spotDescribe"));
										System.out.println("spotName:" + map_spot.get("spotName"));
										System.out.println("\n");
									}
										
////									HotelModel hotel = (HotelModel)map_3.get("hotelModel");
//									if(entry2.getKey().toString() == "13" && i != (spotlist.size() - 1)){
//										//����13������������ľ�����Ѿ��������ˣ�������Ҫ+1
//										days++;
//										System.out.println("��" + days + "��:");
//										System.out.println("����:");
//									
//									}else if(entry2.getKey().toString() == "12" && i != (spotlist.size() - 1)){
//										//����12������������ľ�����Ѿ��������ˣ��������ľ���
//										System.out.println("����:");
//									}
									
								}//for(Map.Entry<String, Object> entry2 : suggestMap.entrySet())
							
							System.out.println("\n\n");
							
								
							}//for(int i = 0; i < spotlist.size(); ++i)
						}//for(Map.Entry<String, Object> entry : jsmap.entrySet())
						
						
						*/
						
						
						
						System.out.println("==================================����====================================================");
						
						for(Map.Entry<String, Object> entry : jsmap.entrySet()){
							//��ȡjson��һ�����ݣ���·��
							System.out.println("Key = " + entry.getKey());//��ʾ�ǵڼ���·�߼����ܵĻ���
							
							//��ȡ�ڶ������ݣ���������suggestmodel��map
							JSONArray spotlist = JSONObject.parseArray(entry.getValue().toString());
							
							int days = 1;//����suggestmap���������key����13��ʱ��˵������Ҫ��1
							System.out.println("��" + days + "��:");
							System.out.println("����:");
							
							
//							System.out.println(spotlist.size());
							for(int i = 0; i < spotlist.size(); ++i){
//								System.out.println(spotlist.get(i));
								//��ȡ���������ݣ���suggestmodel
								Map<String, Object> suggestMap = (Map<String, Object>) spotlist.get(i);
								
								for(Map.Entry<String, Object> entry2 : suggestMap.entrySet()){
									
//									System.out.println("Key = " + entry2.getKey() + ", Value = " + entry2.getValue());\\
//									System.out.println("Key = " + entry2.getKey());
									
									
									
									//��ȡsuggest�еĸ���model����
									JSONObject map_3 = JSONObject.parseObject(JSONObject.toJSONString(entry2.getValue()));
									
//									System.out.println("hotelModel:"+map_3.get("hotelModel"));
//									System.out.println("restaurantModel:"+map_3.get("restaurantModel"));
//									System.out.println("shopModel:"+map_3.get("shopModel"));
//									System.out.println("spotModel:"+map_3.get("spotModel"));
									
									JSONObject map_hotel = JSONObject.parseObject(JSONObject.toJSONString(map_3.get("hotelModel")));
									JSONObject map_restaurant = JSONObject.parseObject(JSONObject.toJSONString(map_3.get("restaurantModel")));
									JSONObject map_shop = JSONObject.parseObject(JSONObject.toJSONString(map_3.get("shopModel")));
									JSONObject map_spot = JSONObject.parseObject(JSONObject.toJSONString(map_3.get("spotModel")));
									if(map_hotel != null){
										System.out.println("entertainment_distance:" + map_hotel.get("entertainment_distance"));
										System.out.println("spot_distance:" + map_hotel.get("spot_distance"));
										System.out.println("hotelAddress:" + map_hotel.get("hotelAddress"));
										System.out.println("hotelName:" + map_hotel.get("hotelName"));
										System.out.println("ave_price:" + map_hotel.get("ave_price"));
										System.out.println("hotelScore:" + map_hotel.get("hotelScore"));
										System.out.println("hotelLevel:" + map_hotel.get("hotelLevel"));
										System.out.println("hotelDescribe:" + map_hotel.get("hotelDescribe"));
										System.out.println("\n");
									}
									if(map_restaurant != null){
										mSearch1.geocode(new GeoCodeOption().city("����").address(map_restaurant.getString("restaurantName")));
										System.out.println("restaurantScore:" + map_restaurant.get("restaurantScore"));
										System.out.println("spot_distance:" + map_restaurant.get("spot_distance"));
										System.out.println("restaurantDescribe:" + map_restaurant.get("restaurantDescribe"));
										System.out.println("restaurantAddress:" + map_restaurant.get("restaurantAddress"));
										System.out.println("restaurantName:" + map_restaurant.get("restaurantName"));
										System.out.println("restaurantType:" + map_restaurant.get("restaurantType"));
										System.out.println("agr_price:" + map_restaurant.get("agr_price"));
										System.out.println("\n");
									}
									if(map_shop != null){
										System.out.println("shopScore:" + map_shop.get("shopScore"));
										System.out.println("shopDescribe:" + map_shop.get("shopDescribe"));
										System.out.println("shopTime:" + map_shop.get("shopTime"));
										System.out.println("shopName:" + map_shop.get("shopName"));
										System.out.println("spot_distance:" + map_shop.get("spot_distance"));
										System.out.println("shopType:" + map_shop.get("shopType"));
										System.out.println("\n");
									}
									if(map_spot != null){
										
										System.out.println("spotSuggestTime:" + map_spot.get("spotSuggestTime"));
										System.out.println("spotPrice:" + map_spot.get("spotPrice"));
										System.out.println("spotScore:" + map_spot.get("spotScore"));
										System.out.println("spotTime:" + map_spot.get("spotTime"));
										System.out.println("spotDescribe:" + map_spot.get("spotDescribe"));
										System.out.println("spotName:" + map_spot.get("spotName"));
										System.out.println("\n");
									}
										
//									HotelModel hotel = (HotelModel)map_3.get("hotelModel");
									if(entry2.getKey().toString() == "13" && i != (spotlist.size() - 1)){
										//����13������������ľ�����Ѿ��������ˣ�������Ҫ+1
										days++;
										System.out.println("��" + days + "��:");
										System.out.println("����:");
									
									}else if(entry2.getKey().toString() == "12" && i != (spotlist.size() - 1)){
										//����12������������ľ�����Ѿ��������ˣ��������ľ���
										System.out.println("����:");
									}
									
								}//for(Map.Entry<String, Object> entry2 : suggestMap.entrySet())
							
							System.out.println("\n\n");
							
								
							}//for(int i = 0; i < spotlist.size(); ++i)
						}//for(Map.Entry<String, Object> entry : jsmap.entrySet())
						
						
						
					/*=====================================================================================================*/
					/*=====================================================================================================*/
					/*=========================================������� ====================================================*/
					/*=====================================================================================================*/
					/*=====================================================================================================*/
					
						
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}else{
				Toast.makeText(getApplicationContext(), "���ӷ�����ʧ�ܣ���������Ϊ�� in Geo",Toast.LENGTH_LONG ).show();
				
			}
			
			
			
			
			
			
			
//			progressDialog.dismiss();
		}
		
	}
	
//	public void initOverlay(){
//		OverlayOptions ooa,oob,ooc,ood; 
//		ooa = new MarkerOptions().icon(bdA).title("1:"+restaurant1).position(latlng1).draggable(true).zIndex(9);
//		mMarkerA=(Marker)(mBaiduMap.addOverlay(ooa));
//		oob = new MarkerOptions().icon(bdB).title("2:"+spot).position(latlng2).draggable(true);
//		mMarkerB=(Marker)(mBaiduMap.addOverlay(oob));
//		ooc = new MarkerOptions().icon(bdC).title("3:"+restaurant2).position(latlng3).draggable(true);
//		mMarkerC=(Marker)(mBaiduMap.addOverlay(ooc));
//		ood = new MarkerOptions().icon(bdD).title("4:"+hotel).position(latlng4).draggable(true);
//		mMarkerD=(Marker)(mBaiduMap.addOverlay(ood));
//		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//		giflist.add(bdA);
//		giflist.add(bdB);
//		giflist.add(bdC);
//		giflist.add(bdD);
//		// add ground overlay
//		LatLng southwest = new LatLng(latlng1.latitude, latlng1.longitude);
//		LatLng northeast = new LatLng(latlng1.latitude, latlng1.longitude);
//		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
//				.include(southwest).build();
//
//		MapStatusUpdate u = MapStatusUpdateFactory
//				.newLatLng(bounds.getCenter());
//		mBaiduMap.setMapStatus(u);
//	}
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(GeoCoderActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG).show();
			return ;
		}
		latlng=new LatLng(result.getLocation().latitude,result.getLocation().longitude);
		option= new MarkerOptions()
					.position(latlng)
					.icon(bdA)
					.title(result.getAddress()).extraInfo(bundle1);
				
		option.animateType(MarkerAnimateType.drop);
		mBaiduMap.addOverlay(option);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				//��ʾһ������
				// ��marker��������ʱ����ʾһ������
				if (pop == null) { 	// popΪ�����ʼ��һ������
					initPop(marker);
				} else {			// pop��Ϊ�ո�������λ��
					updatePopPosition(marker.getPosition());
				}
				tv_title.setText(marker.getTitle());	
				
				return true;
			}
			
		});
		// add ground overlay
		LatLng southwest = new LatLng(latlng.latitude, latlng.longitude);
		LatLng northeast = new LatLng(latlng.latitude, latlng.longitude);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
				.include(southwest).build();

		MapStatusUpdate u = MapStatusUpdateFactory
				.newLatLng(bounds.getCenter());
		mBaiduMap.setMapStatus(u);
//		new MarkerOptions().position(new LatLng(result.getLocation().latitude,result.getLocation().longitude)).icon(bdA);
		
//		System.out.println("latlng1:"+latlng1);
//		latlng2 = new LatLng(result.getLocation().latitude,
//				result.getLocation().longitude);
////		System.out.println("latlng2:"+latlng2);
//		latlng3 = new LatLng(result.getLocation().latitude,
//				result.getLocation().longitude);
////		System.out.println("latlng3:"+latlng3);
//		latlng4 = new LatLng(result.getLocation().latitude,
//				result.getLocation().longitude);
//		System.out.println("latlng4:"+latlng4);
		

//		// ���¶���
//		ooa.animateType(MarkerAnimateType.drop);
//		oob.animateType(MarkerAnimateType.drop);
//		ooc.animateType(MarkerAnimateType.drop);
//		ood.animateType(MarkerAnimateType.drop);
		
//		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//			
//			@Override
//			public boolean onMarkerClick(Marker marker) {
//				if(pop==null){
//					pop= View.inflate(GeoCoderActivity.this, R.layout.pop, null);
//					tv_title= (TextView)pop.findViewById(R.id.tv_title);
//					//��ʾһ������
//					mMapView.addView(pop,createLayoutParams(marker.getPosition()));
//				}else{
//					mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
//				}
//				tv_title.setText(marker.getTitle());
//				
//				return true;
//			}
//		});
		// add marker overlay

//		MarkerOptions ooA = new MarkerOptions().position(latlng1).icon(bdA)
//				.zIndex(9).draggable(true).title("��һ���ط�");
//		
//		// ���¶���
//		ooA.animateType(MarkerAnimateType.drop);
//		mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//
//		 MarkerOptions ooB = new MarkerOptions().position(latlng2).icon(bdB)
//		 .zIndex(5);
//		 // ���¶���
//		 ooB.animateType(MarkerAnimateType.drop);
//		mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
//		
//		
//		MarkerOptions ooC = new MarkerOptions().position(latlng3).icon(bdC)
//				.zIndex(9).draggable(true);
//		ooC.animateType(MarkerAnimateType.drop);
//		mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//		
//		 MarkerOptions ooD = new MarkerOptions().position(latlng4).icon(bdD)
//		 .zIndex(5).draggable(true);
//		 ooD.animateType(MarkerAnimateType.drop);
//		 mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

//		llA = returnLat1();
//		llB = returnLat2();
//		llC = returnLat3();
//		llD = returnLat4();
		
		//TextView textView1 = (TextView) findViewById(R.id.textview1);
		//textView1.setText("1:" + Double.toString(latlng1.latitude) + " "
		//		+ Double.toString(latlng1.longitude) + "\n" + "2:"
		//		+ Double.toString(latlng1.latitude) + " "
		//		+ Double.toString(latlng1.longitude) + "\n" + "3:"
		//		+ Double.toString(latlng1.latitude) + " "
		//		+ Double.toString(latlng1.longitude) + "\n" + "4:"
		//		+ Double.toString(latlng1.latitude) + " "
		//		+ Double.toString(latlng1.longitude) + "\n******");
		
		// // �������ʱ��ӻ���ͼ��
//		// addCustomElementsDemo();
//		LatLng p1 = new LatLng(39.963175, 116.400244);
//		LatLng p2 = new LatLng(39.942821, 116.369199);
//		LatLng p3 = new LatLng(39.939723, 116.425541);
//		LatLng p4 = new LatLng(39.906965, 116.401394);
//		List<LatLng> points = new ArrayList<LatLng>();
//		points.add(p1);
//		points.add(p2);
//		points.add(p3);
//		points.add(p4);
//		OverlayOptions ooPolyline = new PolylineOptions().width(10)
//				.color(0xAAFF0000).points(points);
//		mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);

	}

//	public LatLng returnLat1() {
//
//		MarkerOptions ooA = new MarkerOptions().position(latlng1).icon(bdA)
//				.zIndex(9).draggable(true).title(restaurant1);
//		// ���¶���
//		ooA.animateType(MarkerAnimateType.drop);
//		mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//
//		LatLng oneLat = latlng2;
//		return oneLat;
//	}
////
//	public LatLng returnLat2() {
//
//		MarkerOptions ooB = new MarkerOptions().position(latlng2).icon(bdB)
//				.zIndex(5).title(spot);
//		// ���¶���
//		ooB.animateType(MarkerAnimateType.drop);
//		mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
//		LatLng oneLat = latlng3;
//		return oneLat;
//	}
//
//	public LatLng returnLat3() {
//		MarkerOptions ooC = new MarkerOptions().position(latlng3).icon(bdC)
//				.zIndex(9).draggable(true).title(restaurant2);
//		ooC.animateType(MarkerAnimateType.drop);
//		mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//		LatLng oneLat = latlng4;
//		return oneLat;
//	}
//
//	public LatLng returnLat4() {
//
//		MarkerOptions ooD = new MarkerOptions().position(latlng4).icon(bdD)
//				.zIndex(5).draggable(true).title(hotel);
//		ooD.animateType(MarkerAnimateType.drop);
//		mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
//		LatLng oneLat = latlng1;
//		return oneLat;
//	}

//	public void addCustomElementsDemo() {
//		// �����ͨ���߻���
//		LatLng p1 = new LatLng(llA.latitude, llA.longitude);
//		LatLng p2 = new LatLng(llB.latitude, llB.longitude);
//		LatLng p3 = new LatLng(llC.latitude, llC.longitude);
//		LatLng p4 = new LatLng(llD.latitude, llD.longitude);
//		List<LatLng> points = new ArrayList<LatLng>();
//		points.add(p1);
//		points.add(p2);
//		points.add(p3);
//		points.add(p4);
//		OverlayOptions ooPolyline = new PolylineOptions().width(10)
//				.color(0xAAFF0000).points(points);
//		mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
//	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(GeoCoderActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
			.show();
			return;
		}
	}
	/**
	 * ����һ�����ֲ���
	 * @param position
	 * @return
	 *//*
		private LayoutParams createLayoutParams(LatLng position) {
			MapViewLayoutParams.Builder builder =new MapViewLayoutParams.Builder();
			builder.layoutMode(ELayoutMode.mapMode);//ָ����������Ϊ��γ��
			builder.position(position);//���ñ�־��λ��
			LayoutParams params = builder.build();
			return params;
		}*/
		/** ����һ��MapView�Ĳ��ֲ������� */
		private LayoutParams createLayoutParams(LatLng position) {
			ELayoutMode mapmode = ELayoutMode.mapMode;	// ָ���Ծ�γ��Ϊ���������
			return new MapViewLayoutParams.Builder().layoutMode(mapmode)// �������� 
													.position(position)	// λ��
													.yOffset(-25)		// y����ƫ����
													.build();
		}
		
		
		private void initPop(Marker marker) {
			pop = View.inflate(GeoCoderActivity.this, R.layout.pop, null);
			tv_title = (TextView) pop.findViewById(R.id.tv_title);
			mMapView.addView(pop, createLayoutParams(marker.getPosition()));
		}

		protected void updatePopPosition(LatLng position) {
			mMapView.updateViewLayout(pop, createLayoutParams(position));
		}
		
}
