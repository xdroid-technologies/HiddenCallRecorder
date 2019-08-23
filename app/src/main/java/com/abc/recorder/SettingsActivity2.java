package com.abc.recorder;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.abc.recorder.base.BaseActivityUpEnableWithMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wang.avi.AVLoadingIndicatorView;

public class SettingsActivity2 extends BaseActivityUpEnableWithMenu implements View.OnClickListener {

    public SettingsActivity2() {
        super(R.string.action_settings, R.menu.menu_resourse_file);

    }

    @Override
    protected void init() {
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_settings);

        Toolbar toolbar=findViewById(R.id.action_bar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        startAnim();
        admobbanner();
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_settings, new SettingsActivity())
                .commit();

    }


    @Override
    protected void initEvent() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }


    }

    void startAnim(){
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToShow();
    }

    private void admobbanner(){

        if (Internetconnection.checkConnection(this)) {

            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdMobAdView.loadAd(adRequest);
            final InterstitialAd mInterstitial = new InterstitialAd(this);
            mInterstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_ali));
            mInterstitial.loadAd(new AdRequest.Builder().build());
            mInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    super.onAdLoaded();
                    if (mInterstitial.isLoaded()) {
                        mInterstitial.show();
                    }
                }
            });

        } else {
            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            mAdMobAdView.setVisibility(View.GONE);
        }

    }
}
