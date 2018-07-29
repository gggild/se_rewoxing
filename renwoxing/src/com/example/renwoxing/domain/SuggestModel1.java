package com.example.renwoxing.domain;

public class SuggestModel1 {
	private RestaurantModel restaurantModel;
	private SpotModel spotModel;
	public SuggestModel1(SpotModel spotModel,RestaurantModel restaurantModel,HotelModel hotelModel,ShopModel shopModel) {
		// TODO Auto-generated constructor stub
		this.restaurantModel = restaurantModel;
		this.spotModel = spotModel;
	}
	
	public SuggestModel1() {
		
	}
	
	public RestaurantModel getRestaurantModel() {
		return restaurantModel;
	}
	public void setRestaurantModel(RestaurantModel restaurantModel) {
		this.restaurantModel = restaurantModel;
	}
	public SpotModel getSpotModel() {
		return spotModel;
	}
	public void setSpotModel(SpotModel spotModel) {
		this.spotModel = spotModel;
	}
}
