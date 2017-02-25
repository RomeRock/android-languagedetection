package com.romerock.modules.android.languagedetection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img_logo_romerock)
    ImageView img_logo_romerock;
    @BindView(R.id.follow_twitter)
    ImageView followTwitter;
    @BindView(R.id.follow_gitHub)
    ImageView followGitHub;
    @BindView(R.id.follow_facebook)
    ImageView followFacebook;
    @BindView(R.id.languageDetect)
    TextView languageDetect;
    @BindView(R.id.textLanguage)
    TextView textLanguage;
    @BindView(R.id.btn_detect)
    Button btnDetect;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.currencyLanguage)
    TextView currencyLanguage;
    @BindView(R.id.relContent)
    RelativeLayout relContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private AlertDialog alertDialog;
    private SharedPreferences sharedPref;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPref = getSharedPreferences(getString(R.string.preferences_name), MODE_PRIVATE);
        boolean firstTime=sharedPref.getBoolean("firstTimeOpen",false);
        if(firstTime){
            sharedPref.edit().putBoolean("firstTimeOpen",false);
            sharedPref.edit().commit();
            //open popup for first time open
            AlertDialog.Builder builder;
            LayoutInflater inflater;
            builder = new AlertDialog.Builder(this);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            builder.setCancelable(true);
            View view = inflater.inflate(R.layout.pop_up, null);
            view.findViewById(R.id.popUpOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            view.findViewById(R.id.popUpChange).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    Intent i = new Intent(MainActivity.this, LanguageSettings.class);
                    startActivity(i);
                }
            });
            builder.setView(view);
            builder.create();
            alertDialog = builder.show();
        }
        language = sharedPref.getString(getString(R.string.preferences_schema_language_settings), "en").toString();
        WebView view = new WebView(this);
        view.setVerticalScrollBarEnabled(false);
        view.setBackgroundColor(getResources().getColor(R.color.drawable));
        ((RelativeLayout) findViewById(R.id.relContent)).addView(view);
        view.loadData(getString(R.string.thank_you), "text/html; charset=utf-8", "utf-8");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textLanguage.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        languageDetect.setTypeface(font);
        currencyLanguage.setTypeface(font);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @OnClick({R.id.img_logo_romerock, R.id.follow_twitter, R.id.follow_gitHub, R.id.follow_facebook, R.id.btn_detect})
    public void onClick(View view) {
        String url = "";
        switch (view.getId()) {
            case R.id.img_logo_romerock:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.romerock_page))));
                break;
            case R.id.follow_facebook:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_facebook_profile)));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_facebook))));
                }
                break;
            case R.id.follow_gitHub:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_git_hub))));
                break;
            case R.id.follow_twitter:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_twitter_profile)));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_twitter))));
                }
                break;
            case R.id.btn_detect:
                Intent i = new Intent(MainActivity.this, LanguageSettings.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if change language
        if (!language.equals(sharedPref.getString(getString(R.string.preferences_schema_language_settings), "en"))) {
            language = sharedPref.getString(getString(R.string.preferences_schema_language_settings), "en");
            recreate();
        }
    }
}
