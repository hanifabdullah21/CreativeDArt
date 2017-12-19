package com.singpaulee.creativedart.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUS on 16/12/2017.
 */

public class SharedPrefManager {
	public static final String shared_user = "DataUser";

	public static final String ID = "ID";
	public static final String FIRST_NAME = "firstname";
	public static final String LAST_NAME = "lastname";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String STATUS = "status";
	public static final String PHOTO = "photo";

	public static final String LOGIN = "login";

	SharedPreferences sp;
	SharedPreferences.Editor editor;

	public SharedPrefManager(Context context) {
		sp = context.getSharedPreferences(shared_user, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void deletePref(){
		editor.remove(shared_user);
		editor.remove(ID);
		editor.remove(FIRST_NAME);
		editor.remove(LAST_NAME);
		editor.remove(USERNAME);
		editor.remove(PASSWORD);
		editor.remove(EMAIL);
		editor.remove(STATUS);
		editor.remove(PHOTO);
		editor.clear();
		editor.commit();
	}

	public void savePref(String keySP, String value){
		editor.putString(keySP, value);
		editor.commit();
	}

	public void savePrefBoolean(String keySP, boolean value){
		editor.putBoolean(keySP, value);
		editor.commit();
	}

	public String getID(){
		return sp.getString(ID,"null");
	}
	public String getFirstNama(){
		return sp.getString(FIRST_NAME, "");
	}
	public String getLastName(){
		return sp.getString(LAST_NAME, "");
	}
	public String getUsername(){
		return sp.getString(USERNAME, "");
	}
	public String getEmail(){
		return sp.getString(EMAIL, "");
	}
	public String getPassword(){
		return sp.getString(PASSWORD, "");
	}
	public String getStatus(){
		return sp.getString(STATUS, "");
	}
	public String getPhoto(){
		return sp.getString(PHOTO, "");
	}

	public Boolean getSudahLogin(){
		return sp.getBoolean(LOGIN, false);
	}
}
