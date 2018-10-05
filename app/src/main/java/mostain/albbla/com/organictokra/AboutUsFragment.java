package mostain.albbla.com.organictokra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    private InterstitialAd mInterstitialAd;

    public AboutUsFragment() {
        // Required empty public constructor
    }
    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment1 = new AboutUsFragment();
        return fragment1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MobileAds.initialize(getContext(),
                "ca-app-pub-4777408170279028~2293102950");

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-4777408170279028/5751650595");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        View v = inflater.inflate(R.layout.fragment_about_us, container, false);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        return  v;
    }

}
