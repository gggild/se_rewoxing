package com.example.renwoxing.domain;

public class SuggestModel {
	private SuggestModel1 suggestModel1;
	private SuggestModel2 suggestModel2;
	public SuggestModel(SuggestModel1 suggestModel1,SuggestModel2 suggestModel2) {
		// TODO Auto-generated constructor stub
		this.suggestModel1 = suggestModel1;
		this.suggestModel2 = suggestModel2;
	}
	
	public SuggestModel() {
		
	}
	
	public SuggestModel1 getSuggestModel1() {
		return suggestModel1;
	}

	public void setSuggestModel1(SuggestModel1 suggestModel1) {
		this.suggestModel1 = suggestModel1;
	}

	public SuggestModel2 getSuggestModel2() {
		return suggestModel2;
	}

	public void setSuggestModel2(SuggestModel2 suggestModel2) {
		this.suggestModel2 = suggestModel2;
	}

	
}
