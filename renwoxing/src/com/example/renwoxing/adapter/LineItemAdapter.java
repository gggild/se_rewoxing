package com.example.renwoxing.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renwoxing.R;
import com.example.renwoxing.domain.GlobalParameter;
import com.example.renwoxing.domain.SuggestModel;

public class LineItemAdapter extends BaseAdapter{
	private Context context;
	private GlobalParameter global;
	private LayoutInflater inflater;
	private List<SuggestModel> items;
	public final class ListViewItem {
		public TextView restaurantName,spotName,noonRestaurantName,shopName,spotName2,hotelName;
		public RelativeLayout list1;
	}
	public LineItemAdapter(Context context, List<SuggestModel> items){
		super();
		this.context = context;
		
		try {
			global =((GlobalParameter) this.context.getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.items = items;
		inflater = LayoutInflater.from(context);
	}
	
	public void showDetailInfo(int index){
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListViewItem listViewItems = null;
		final int index=position;
		if(listViewItems==null){
			listViewItems=new ListViewItem();
			convertView=inflater.inflate(R.layout.row_list_line, null);
			listViewItems.restaurantName=(TextView)convertView.findViewById(R.id.restaurantName);
			listViewItems.spotName=(TextView)convertView.findViewById(R.id.spotName);
			listViewItems.noonRestaurantName=(TextView)convertView.findViewById(R.id.noonRestaurantName);
			listViewItems.shopName=(TextView)convertView.findViewById(R.id.shopName);
			listViewItems.spotName2=(TextView)convertView.findViewById(R.id.spotName2);
			listViewItems.hotelName=(TextView)convertView.findViewById(R.id.hotelName);
			convertView.setTag(listViewItems);
		}else{
			listViewItems=(ListViewItem)convertView.getTag();
		}
		
		listViewItems.restaurantName.setText(items.get(position).getSuggestModel1().getRestaurantModel().getRestaurantName());
		listViewItems.spotName.setText(items.get(position).getSuggestModel1().getSpotModel().getSpotName());
		listViewItems.noonRestaurantName.setText(items.get(position).getSuggestModel2().getRestaurantModel().getRestaurantName());
		listViewItems.shopName.setText(items.get(position).getSuggestModel2().getShopModel().getShopName());
		listViewItems.spotName2.setText(items.get(position).getSuggestModel2().getSpotModel().getSpotName());
		listViewItems.hotelName.setText(items.get(position).getSuggestModel2().getHotelModel().getHotelName());
		
		
		return convertView;
	}

}
