package mostain.albbla.com.organictokra;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tuyenmonkey.mkloader.MKLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {
    private InterstitialAd mInterstitialAd;
    private WebView mWebView;
    private MKLoader mProgressBar;
    private ImageView imageView;
    private TextView textView;

    public WebFragment() {
        // Required empty public constructor
    }
    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        MobileAds.initialize(getContext(),
                "ca-app-pub-4777408170279028~2293102950");

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-4777408170279028/8160507309");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        View v = inflater.inflate(R.layout.fragment_web, container, false);
        mProgressBar = v.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView = (WebView) v.findViewById(R.id.webView);
        //imageView = (ImageView) v.findViewById(R.id.problemImage);
        textView = (TextView) v.findViewById(R.id.problemText);
        Typeface CustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PatchyRobots.ttf");
        textView.setTypeface(CustomFont);

        if(isNetworkConnected()){
            textView.setVisibility(View.GONE);
            mWebView.loadUrl("https://www.zonepopular.com/");
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
            webSettings.setAppCachePath( getActivity().getApplicationContext().getCacheDir().getAbsolutePath() );
            webSettings.setAllowFileAccess( true );
            webSettings.setAppCacheEnabled( true );
            webSettings.setCacheMode( WebSettings.LOAD_DEFAULT );
            webSettings.setJavaScriptEnabled(true);
            mWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }else
        {
            textView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }








        mWebView.setWebViewClient(new myWebClient());









        mWebView.canGoBack();
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebView.canGoBack()) {
                    mWebView.goBack();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                    return true;
                }
                return false;

            }
        });


        return v;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }










    public class myWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

}
