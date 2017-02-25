package com.romerock.modules.android.languagedetection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.romerock.modules.android.languagedetection.Adapters.RecyclerViewAdapter;
import com.romerock.modules.android.languagedetection.Helpers.LocaleHelper;
import com.romerock.modules.android.languagedetection.Interfaces.ItemClickInterface;
import com.romerock.modules.android.languagedetection.Model.ItemSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSettings extends AppCompatActivity {
    @BindView(R.id.recyclerColors)
    RecyclerView recyclerColors;
    @BindView(R.id.toolbarback)
    Toolbar toolbarback;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.relLenguage)
    RelativeLayout relLenguage;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Language();
    }

    private void Language(){
        setContentView(R.layout.activity_languaje_settings);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerColors);
        ButterKnife.bind(this);
        sharedPrefs = getSharedPreferences(getString(R.string.preferences_name), MODE_PRIVATE);
        Window window = this.getWindow();
        List<ItemSettings> itemsData = new ArrayList<ItemSettings>(3);
        itemsData.add(new ItemSettings("ENGLISH  # ", -10, true, sharedPrefs.getString(getString(R.string.preferences_schema_language_settings),"").contains("en")?true:false));
        itemsData.add(new ItemSettings("ESPAÑOL  # ", -10, true, sharedPrefs.getString(getString(R.string.preferences_schema_language_settings),"").contains("es")?true:false));
        itemsData.add(new ItemSettings("FRANÇAIS  # ", -10, true, sharedPrefs.getString(getString(R.string.preferences_schema_language_settings),"").contains("fr")?true:false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(itemsData, new ItemClickInterface() {
            @Override
            public void onItemClicked(View view, int position, String code) {
                SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.preferences_name), MODE_PRIVATE);
                SharedPreferences.Editor ed;
                ed = sharedPrefs.edit();
                switch (position) {
                    default:
                    case 0:
                        ed.putString(getString(R.string.preferences_schema_language_settings), "en");
                        LocaleHelper.setLocale(LanguageSettings.this, "en");
                        ed.putString(getString(R.string.preferences_schema_language_settings), "en");
                        Toast.makeText(LanguageSettings.this, getString(R.string.settings_option_language_change, "English"), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        ed.putString(getString(R.string.preferences_schema_language_settings), "es");
                        LocaleHelper.setLocale(LanguageSettings.this, "es");
                        Toast.makeText(LanguageSettings.this, getString(R.string.settings_option_language_change, "Español"), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        ed.putString(getString(R.string.preferences_schema_language_settings), "fr");
                        LocaleHelper.setLocale(LanguageSettings.this, "fr");
                        Toast.makeText(LanguageSettings.this, getString(R.string.settings_option_language_change, "Français"), Toast.LENGTH_SHORT).show();
                        break;
                }
                ed.commit();
                tittle.setText(getString(R.string.settings_option_select_lenguage));
                finish();
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick(R.id.toolbarback)
    public void onClick() {
        finish();
    }

}
