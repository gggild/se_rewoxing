package com.example.renwoxing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends Activity {
	TextView tvLocationPoint;
	double latitude;// 纬度
	double longitude;// 经度
	Button location_btn;//定位
	String city;//定位城市
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		tvLocationPoint = (TextView) findViewById(R.id.my_location_point);
		location_btn = (Button) findViewById(R.id.location_btn);
		Intent intent = getIntent();
		if (intent.hasExtra("x") && intent.hasExtra("y")&& intent.hasExtra("addressStr")) {
			Bundle bundle = intent.getExtras();
			latitude = bundle.getDouble("x");
			longitude = bundle.getDouble("y");
			city = bundle.getString("addressStr");
			tvLocationPoint.setText("纬度:" + latitude + "经度:" + longitude+"城市："+city);
		} else {
			tvLocationPoint.setText("纬度:无" + "经度:无");
		}
		location_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LocationActivity.this, MyLocation.class); 
				startActivity(intent);
			}
			
		});
	}
//	public void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.location_btn:
//			startActivity(new Intent(this, MyLocation.class));
//			break;
//		case R.id.show_my_location_btn:
//			Intent intent = new Intent(this, ShowMyLocation.class);
//			Bundle bundle = new Bundle();
//			bundle.putDouble("x", latitude);
//			bundle.putDouble("y", longitude);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			break;
//		}
//	}

	
}
