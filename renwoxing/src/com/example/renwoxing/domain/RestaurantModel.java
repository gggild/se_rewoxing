package com.example.renwoxing.domain;

import java.util.List;

public class RestaurantModel {
	private float agr_price;//Æ½¾ù¼Û¸ñ
	private int entertainent_id;//ÓéÀÖÇøid
	private float entertainment_distance;//¾àÀë
	private List<HotelModel> hotelModels;//±ö¹İÀà
	private float hotel_distance;//Óë±ö¹İµÄ¾àÀë
	private int hotel_id;//±ö¹İid
	private int id;//²Í¹İid
	private String restaurantAddress;//²Í¹İµØÖ·
	private String restaurantDescribe;//²Í¹İÃèÊö
	private String restaurantLevel;//²Í¹İ¼¶±ğ
	private String restaurantName;//²Í¹İÃû×Ö
	private String restaurantScore;//²Í¹İÆÀ·Ö
	private String restaurantTime;//ÓÃ²ÍÊ±¼ä
	private String restaurantType;//²Í¹İÀàĞÍ
	private List<ShopModel> shopModels;//ÉÌµêÀà
	private float spot_distance;//Óë¾°µã¾àÀë
	private int shop_id;//µêÆÌid
	private float shop_distance;//ÓëµêÆÌ¾àÀë
	private int spot_id;//¾°µãid
	public float getAgr_price() {
		return agr_price;
	}
	public void setAgr_price(float agr_price) {
		this.agr_price = agr_price;
	}
	public int getEntertainent_id() {
		return entertainent_id;
	}
	public void setEntertainent_id(int entertainent_id) {
		this.entertainent_id = entertainent_id;
	}
	public float getEntertainment_distance() {
		return entertainment_distance;
	}
	public void setEntertainment_distance(float entertainment_distance) {
		this.entertainment_distance = entertainment_distance;
	}
	public List<HotelModel> getHotelModels() {
		return hotelModels;
	}
//	public void setHotelModels(List<HotelModel> hotelModels) {
//		this.hotelModels = hotelModels;
//	}
	public float getHotel_distance() {
		return hotel_distance;
	}
	public void setHotel_distance(float hotel_distance) {
		this.hotel_distance = hotel_distance;
	}
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public String getRestaurantDescribe() {
		return restaurantDescribe;
	}
	public void setRestaurantDescribe(String restaurantDescribe) {
		this.restaurantDescribe = restaurantDescribe;
	}
	public String getRestaurantLevel() {
		return restaurantLevel;
	}
	public void setRestaurantLevel(String restaurantLevel) {
		this.restaurantLevel = restaurantLevel;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantScore() {
		return restaurantScore;
	}
	public void setRestaurantScore(String restaurantScore) {
		this.restaurantScore = restaurantScore;
	}
	public String getRestaurantTime() {
		return restaurantTime;
	}
	public void setRestaurantTime(String restaurantTime) {
		this.restaurantTime = restaurantTime;
	}
	public String getRestaurantType() {
		return restaurantType;
	}
	public void setRestaurantType(String restaurantType) {
		this.restaurantType = restaurantType;
	}
	public List<ShopModel> getShopModels() {
		return shopModels;
	}
//	public void setShopModels(List<ShopModel> shopModels) {
//		this.shopModels = shopModels;
//	}
	public float getSpot_distance() {
		return spot_distance;
	}
	public void setSpot_distance(float spot_distance) {
		this.spot_distance = spot_distance;
	}
	public int getShop_id() {
		return shop_id;
	}
	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}
	public float getShop_distance() {
		return shop_distance;
	}
	public void setShop_distance(float shop_distance) {
		this.shop_distance = shop_distance;
	}
	public int getSpot_id() {
		return spot_id;
	}
	public void setSpot_id(int spot_id) {
		this.spot_id = spot_id;
	}
	
	
	
}
