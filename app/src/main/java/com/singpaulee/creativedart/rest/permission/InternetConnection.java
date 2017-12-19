package com.singpaulee.creativedart.rest.permission;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ASUS on 16/12/2017.
 */

public class InternetConnection {
	public static boolean checkConnection(Context context){

		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
}
