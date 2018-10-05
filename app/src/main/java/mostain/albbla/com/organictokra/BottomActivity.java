package mostain.albbla.com.organictokra;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class BottomActivity extends AppCompatActivity {
    //ca-app-pub-4777408170279028~2293102950
    private InterstitialAd mInterstitialAd;
    private AlertDialog.Builder ab;
    private AlertDialog alertDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            MobileAds.initialize(getBaseContext(), "ca-app-pub-6649809173384029~5750086521");
//
//            mInterstitialAd = new InterstitialAd(getBaseContext());
//            mInterstitialAd.setAdUnitId("ca-app-pub-6649809173384029/9010674689");
            MobileAds.initialize(BottomActivity.this,
                    "ca-app-pub-4777408170279028~2293102950");

            mInterstitialAd = new InterstitialAd(BottomActivity.this);
            mInterstitialAd.setAdUnitId("ca-app-pub-4777408170279028/3071832155");

            mInterstitialAd.loadAd(new AdRequest.Builder().build());






            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = WebFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomcontent, selectedFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                    Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));

                    }
                    return true;
                case R.id.navigation_notifications:
                    selectedFragment = AboutUsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.bottomcontent, selectedFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bottom);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomcontent, WebFragment.newInstance()).commit();

    }
    @Override
    public void onBackPressed() {
            ab = new AlertDialog.Builder(BottomActivity.this);
            ab.setTitle("Confirm").
                    setMessage("Are you sure you want to exit").
                    setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                }
            });
            alertDialog = ab.create();
            alertDialog.show();
    }

}
