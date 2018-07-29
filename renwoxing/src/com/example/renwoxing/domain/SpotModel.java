package com.example.renwoxing.domain;

import java.util.List;

public class SpotModel {
	private List<HotelModel> hotelModels;//酒店类
	private int id;//景点id
	private List<RestaurantModel> restaurantModels;//餐馆类
	private List<ShopModel> shopModels;//店铺类
	private String spotDescribe;//景点描述
	private String spotName;//景点名称
	private String spotScore;//景点评分
	private String spotSuggestTime;//景点建议游玩时间
	private String spotTime;//景点游玩时间
	public List<HotelModel> getHotelModels() {
		return hotelModels;
	}
	public void setHotelModels(List<HotelModel> hotelModels) {
		this.hotelModels = hotelModels;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<RestaurantModel> getRestaurantModels() {
		return restaurantModels;
	}
	public void setRestaurantModels(List<RestaurantModel> restaurantModels) {
		this.restaurantModels = restaurantModels;
	}
	public List<ShopModel> getShopModels() {
		return shopModels;
	}
	public void setShopModels(List<ShopModel> shopModels) {
		this.shopModels = shopModels;
	}
	public String getSpotDescribe() {
		return spotDescribe;
	}
	public void setSpotDescribe(String spotDescribe) {
		this.spotDescribe = spotDescribe;
	}
	public String getSpotName() {
		return spotName;
	}
	public void setSpotName(String spotName) {
		this.spotName = spotName;
	}
	public String getSpotScore() {
		return spotScore;
	}
	public void setSpotScore(String spotScore) {
		this.spotScore = spotScore;
	}
	public String getSpotSuggestTime() {
		return spotSuggestTime;
	}
	public void setSpotSuggestTime(String spotSuggestTime) {
		this.spotSuggestTime = spotSuggestTime;
	}
	public String getSpotTime() {
		return spotTime;
	}
	public void setSpotTime(String spotTime) {
		this.spotTime = spotTime;
	}
	
	
	

}
