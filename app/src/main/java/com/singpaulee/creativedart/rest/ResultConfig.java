package com.singpaulee.creativedart.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 16/12/2017.
 */

public class ResultConfig {
	private static final String ROOT_URL = "https://unproportioned-gara.000webhostapp.com/api/creative/image/";

	public ResultConfig() {
	}

	private static Retrofit getRetrofitClient(){
		return new Retrofit.Builder()
				.baseUrl(ROOT_URL)
				.addConverterFactory(GsonConverterFactory.create()).build();

	}

	public static ApiRequest getService(){
		return getRetrofitClient().create(ApiRequest.class);
	}
}
