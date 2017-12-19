package com.singpaulee.creativedart.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 13/12/2017.
 */

public class Config {
	private static final String base_url= "https://unproportioned-gara.000webhostapp.com/api/creative/";

	public static Retrofit getRetrofit(){
		Gson gson = new GsonBuilder()
				.setLenient()
				.create();


		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(base_url)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();

		return retrofit;
	}
}
