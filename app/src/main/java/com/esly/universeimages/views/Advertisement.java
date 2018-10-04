package com.esly.universeimages.views;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Advertisement {

	/* Ids */
	private String bannerId = "ca-app-pub-7852958376083269/2194370431";
	private String interstitialId = "ca-app-pub-7852958376083269/3671103634";
	
	/* System */
	private Activity activity;
	private InterstitialAd interstitial;
	private InterstitialAd exitInterstitialAd;
	
	public Advertisement(Activity activity) {
		this.activity = activity;
	}
	
	public void loadBanner(LinearLayout layout) {
		AdView adView = new AdView(activity);
	    adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId(bannerId);
	    layout.addView(adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
    }
	
	public void loadInterstitial()
	{
		interstitial = new InterstitialAd(activity);
		interstitial.setAdUnitId(interstitialId);
		
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		
		interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitial.show();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
            	Log.e("Ad Error", "Error cargando interstitial - error code:" + errorCode);
            }
        });
		
		interstitial.loadAd(adRequestBuilder.build());
	}
	
	public void loadExitInterstitialAd()
	{
		exitInterstitialAd = new InterstitialAd(this.activity);
		exitInterstitialAd.setAdUnitId(interstitialId);
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		exitInterstitialAd.loadAd(adRequestBuilder.build());
	}
	
	public void showExitAd()
	{
		if(this.exitInterstitialAd.isLoaded())
		{
			this.exitInterstitialAd.show();
			
		}
	}
	
}
