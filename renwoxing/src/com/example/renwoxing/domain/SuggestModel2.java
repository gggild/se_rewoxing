package com.example.renwoxing.domain;

public class SuggestModel2 {
	private RestaurantModel restaurantModel;
	private HotelModel hotelModel;
	private ShopModel shopModel;
	private SpotModel spotModel;
	public SuggestModel2(SpotModel spotModel,RestaurantModel restaurantModel,HotelModel hotelModel,ShopModel shopModel) {
		// TODO Auto-generated constructor stub
		this.restaurantModel = restaurantModel;
		this.spotModel = spotModel;
		this.hotelModel = hotelModel;
		this.shopModel = shopModel;
	}
	
	public SuggestModel2() {
		
	}
	
	public RestaurantModel getRestaurantModel() {
		return restaurantModel;
	}
	public void setRestaurantModel(RestaurantModel restaurantModel) {
		this.restaurantModel = restaurantModel;
	}
	public HotelModel getHotelModel() {
		return hotelModel;
	}
	public void setHotelModel(HotelModel hotelModel) {
		this.hotelModel = hotelModel;
	}
	public ShopModel getShopModel() {
		return shopModel;
	}
	public void setShopModel(ShopModel shopModel) {
		this.shopModel = shopModel;
	}
	public SpotModel getSpotModel() {
		return spotModel;
	}
	public void setSpotModel(SpotModel spotModel) {
		this.spotModel = spotModel;
	}
}
