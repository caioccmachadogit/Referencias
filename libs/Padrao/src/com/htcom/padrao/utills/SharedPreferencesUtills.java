package com.htcom.padrao.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtills {
	public static void savePreferences(String key, boolean value, Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static void savePreferences(String key, String value, Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit().putString(key, value).commit();
	}

	public static boolean loadSavedPreferencesBoolean(String key, Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, false);
	}
	
	public static String loadSavedPreferencesString(String key, Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key, "");
	}
	
	public static void deletePreferences(String key, Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit().remove(key).commit();
	}

}
