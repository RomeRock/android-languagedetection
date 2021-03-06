package com.romerock.modules.android.languagedetection;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.romerock.modules.android.languagedetection.Helpers.LocaleHelper;

import java.util.Locale;


public class SplashScreen extends Activity {
	protected int _splashTime = 5000;

	private Thread splashTread;
	private boolean isAllShoppingsIntetn = false;
	private String action = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Detect language
		SharedPreferences sharedPrefs= getSharedPreferences(getString(R.string.preferences_name), MODE_PRIVATE);
		SharedPreferences.Editor ed = sharedPrefs.edit();
		Locale current = getResources().getConfiguration().locale;
		if(current.getLanguage().toLowerCase().equals("es")){
			ed.putString(getString(R.string.preferences_schema_language_settings), "es");
			LocaleHelper.setLocale(this, "es");
		}else{
			if(current.getLanguage().toLowerCase().equals("fr")){
				ed.putString(getString(R.string.preferences_schema_language_settings), "fr");
				LocaleHelper.setLocale(this, "fr");
			}else{
				ed.putString(getString(R.string.preferences_schema_language_settings), "en");
				LocaleHelper.setLocale(this, "en");
			}
		}
		ed.putBoolean("firstTimeOpen",true);
		ed.commit();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		final SplashScreen sPlashScreen = this;
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent();
					intent.setClass(sPlashScreen, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		splashTread.start();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
	}

	// Function that will handle the touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		}
		return true;
	}

	@Override
	public void onBackPressed() {
	}

}