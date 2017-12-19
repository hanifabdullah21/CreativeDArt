package com.singpaulee.creativedart.rest.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by ASUS on 16/12/2017.
 */

public class PermissionChecker {
	private final Context context;

	public PermissionChecker(Context context) {
		this.context = context;
	}

	public boolean lacksPermissions(String... permissions){
		for (String permission : permissions){
			if (lackPermission(permission)){
				return true;
			}
		}
		return false;
	}
	private boolean lackPermission(String per){
		return ContextCompat.checkSelfPermission(context, per) == PackageManager.PERMISSION_DENIED;
	}
}
