package com.example.renwoxing;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity {
	ImageView searchimage,location_iv;
	double latitude;// 纬度
	double longitude;// 经度
	String city="";//定位城市
	TextView tvLocationPoint;//定位点控件
	EditText search;//输入条件框
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;// 定位模式
	BitmapDescriptor mCurrentMarker;// Marker图标
//			Button send;
	private MapView mapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	private View pop;
	private TextView tv_title;//pop标题
	private String addr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchimage= (ImageView) findViewById(R.id.searchimage);
        location_iv= (ImageView) findViewById(R.id.location_iv);
        tvLocationPoint= (TextView) findViewById(R.id.my_location_point);
        search= (EditText) findViewById(R.id.search);
        mCurrentMode = LocationMode.NORMAL;// 设置定位模式为普通
		mCurrentMarker = BitmapDescriptorFactory// 构建mark图标
				.fromResource(R.drawable.icon_marka);
		// 地图初始化
		mapView = (MapView) findViewById(R.id.my_location_bmapView);
//		send = (Button) findViewById(R.id.send);

		mBaiduMap = mapView.getMap();
		// 开启定位图层
		// mBaiduMap.setMyLocationEnabled(true);
		// mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
		// mCurrentMode, true, null));
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);// 注册监听函数：

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocClient.setLocOption(option);
		mLocClient.start();
		if (city=="") {
			location_iv.setVisibility(View.VISIBLE);//图标可见
			tvLocationPoint.setVisibility(View.GONE);//textview隐藏
			
		} else {
			
		}
        searchimage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent =new Intent(MainActivity.this,GeoCoderActivity.class);
				Intent intent =new Intent(MainActivity.this,GeoCoderActivity.class);
				startActivity(intent);
			}
		});
        location_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(MainActivity.this,MyLocation.class);
				startActivity(intent);
			}
		});
        tvLocationPoint.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(MainActivity.this,MyLocation.class);
				startActivity(intent);
			}
		});
    }

    /**
	 * 在地图上添加Marker，并显示一个泡泡，显示文本信息
	 * @author DONGHUI
	 * @date 2017年6月27日 09:46:42
	 * @TODO
	 * @param longitude
	 * @param latitude
	 * @param bitmap
	 * @param baiduMap
	 */
	private void overlay(LatLng point, BitmapDescriptor bitmap,
			BaiduMap baiduMap) {
		// 构建MarkerOption，用于在地图上添加Marker
		MarkerOptions option=new MarkerOptions()
				.position(point)
				.icon(bitmap)
				.title(""+addr);
		
		// 在地图上添加Marker，并显示
		baiduMap.addOverlay(option);
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				if(pop==null){
					pop= View.inflate(MainActivity.this, R.layout.pop, null);
					tv_title= (TextView)pop.findViewById(R.id.tv_title);
					//显示一个泡泡
					mapView.addView(pop,createLayoutParams(marker.getPosition()));
				}else{
					mapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
				}
				tv_title.setText(marker.getTitle());
				
				return true;
			}

		});
	}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mapView == null)
				return;

			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				LatLng ll = new LatLng(latitude, longitude);
				// 设置缩放比例,更新地图状态
				float f = mBaiduMap.getMaxZoomLevel();// 19.0
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll,
						f - 2);
				mBaiduMap.animateMapStatus(u);
				city=location.getCity();
				addr = location.getAddress().address;
				tvLocationPoint.setVisibility(View.VISIBLE);//textview可见
				location_iv.setVisibility(View.GONE);//隐藏
				tvLocationPoint.setText(city);
				overlay(ll, mCurrentMarker, mBaiduMap);//在地图上添加Marker，并显示一个泡泡，显示文本信息
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		mapView = null;
		super.onDestroy();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
/**
 * 创建一个布局参数
 * @param position
 * @return
 */
	private LayoutParams createLayoutParams(LatLng position) {
		MapViewLayoutParams.Builder builder =new MapViewLayoutParams.Builder();
		builder.layoutMode(ELayoutMode.mapMode);//指定坐标类型为经纬度
		builder.position(position);//设置标志的位置
		LayoutParams params = builder.build();
		return params;
	}
    
}
