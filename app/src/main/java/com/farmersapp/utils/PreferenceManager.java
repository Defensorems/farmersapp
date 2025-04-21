package com.farmersapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(Constants.PREF_USER_ID, "");
    }

    public void saveUserName(String userName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_USER_NAME, userName);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(Constants.PREF_USER_NAME, "");
    }

    public void saveFarmId(String farmId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_FARM_ID, farmId);
        editor.apply();
    }

    public String getFarmId() {
        return sharedPreferences.getString(Constants.PREF_FARM_ID, "");
    }

    public void saveFarmName(String farmName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_FARM_NAME, farmName);
        editor.apply();
    }

    public String getFarmName() {
        return sharedPreferences.getString(Constants.PREF_FARM_NAME, "");
    }

    public void saveLastSyncTime(long timestamp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.PREF_LAST_SYNC, timestamp);
        editor.apply();
    }

    public long getLastSyncTime() {
        return sharedPreferences.getLong(Constants.PREF_LAST_SYNC, 0);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}