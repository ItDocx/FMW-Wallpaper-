package com.brainsMedia.FMW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brainsMedia.FMW.Fragments.AsianCategory;
import com.brainsMedia.FMW.Fragments.IndianCategory;
import com.brainsMedia.FMW.Fragments.SpanishCategory;
import com.brainsMedia.FMW.Fragments.USACategory;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd dashBoardInter;
    private Boolean onBackPressed = false;
    private Toolbar toolbar;
    private Dialog dashBoardDialog;
    private RelativeLayout dashBannerCont;
    private CardView indianCard,asianCrd,usaCard,spanishCard;


    private static final int pos_close = 0;
    private static final int pos_DashBoard = 1;
    private static final int pos_Indian = 2;
    private static final int pos_Asian = 3;
    private static final int pos_Spanish = 4;
    private static final int pos_Usa = 5;
    private static final int pos_Aboutus = 6;
    private static final int pos_Logout = 7;

    private String[] screenTitle;
    private Drawable[] screenIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indianCard = findViewById(R.id.indian_main_btn);
        usaCard = findViewById(R.id.usa_main_btn);
        asianCrd = findViewById(R.id.asian_main_btn);
        spanishCard = findViewById(R.id.spanish_main_btn);
        dashBannerCont = findViewById(R.id.dashboard_Banner_Container);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);



        // Initialize Ads
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {


            }
        });
        // Implement Bannner Ad
        dashBoardAds("https://female-models-wallpapers-default-rtdb.firebaseio.com/dashBoardBanner");
        // Implement Nature Interstitial Ad
        getIndianInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/indianInter");
        //Implement Artistic Interstetial
        getUsaInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/usaInter");
        //Implement Anonymous Interstetial
        getAsianInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/asianInter");
        //Implement Gaming Interstetial
        getSpanishInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/spanishInter");


        // Adding Dialog
        dashBoardDialog = new Dialog(MainActivity.this);
        dashBoardDialog.setContentView(R.layout.custom_dialoge);

        // Toolbar Listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog();
                dashBoardDialog.show();

            }
        });



        // Nature Listener
        indianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dashBoardInter != null){


                    dashBoardInter.show(MainActivity.this);
                    dashBoardInter.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            // Calling Nature Fragment
                            FragmentManager indianFragManager = getSupportFragmentManager();
                            IndianCategory indianFrag = new IndianCategory();
                            indianFragManager.beginTransaction().replace(R.id.main_frame_forFrags,indianFrag).addToBackStack(null).commit();

                            onBackPressed = true;

                            // Implement Nature Interstitial Ad
                            getIndianInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/indianInter");

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            Toast.makeText(MainActivity.this, "Error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });

        // Artistic Listener
        usaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dashBoardInter != null){

                    dashBoardInter.show(MainActivity.this);
                    dashBoardInter.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            FragmentManager usaFragManager = getSupportFragmentManager();
                            usaFragManager.beginTransaction().replace(R.id.main_frame_forFrags,new USACategory(),null).addToBackStack(null).commit();
                            //Implement Artistic Interstetial
                            getUsaInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/usaInter");
                            super.onAdDismissedFullScreenContent();

                            onBackPressed = true;
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            Toast.makeText(MainActivity.this, "Error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }



            }
        });

        // Anonymous Listener
        asianCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dashBoardInter != null){

                    dashBoardInter.show(MainActivity.this);
                    dashBoardInter.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            FragmentManager asianFragManager = getSupportFragmentManager();
                            asianFragManager.beginTransaction().replace(R.id.main_frame_forFrags,new AsianCategory(),null).addToBackStack(null).commit();
                            //Implement Anonymous Interstetial
                            getAsianInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/asianInter");
                            super.onAdDismissedFullScreenContent();

                            onBackPressed = true;
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            Toast.makeText(MainActivity.this, "Error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }



            }
        });

        // Gaming Listener
        spanishCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dashBoardInter != null){

                    dashBoardInter.show(MainActivity.this);
                    dashBoardInter.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            FragmentManager spanishFragManager = getSupportFragmentManager();
                            spanishFragManager.beginTransaction().replace(R.id.main_frame_forFrags,new SpanishCategory(),null).addToBackStack(null).commit();
                            //Implement Gaming Interstetial
                            getSpanishInter("https://female-models-wallpapers-default-rtdb.firebaseio.com/spanishInter");
                            super.onAdDismissedFullScreenContent();

                            onBackPressed = true;

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                            Toast.makeText(MainActivity.this, "Error: "+adError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }



            }
        });




    }


    private void dashBoardAds(String bannerId) {
        Firebase.setAndroidContext(MainActivity.this);

        Firebase firebase = new Firebase(bannerId);

        firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                String data = dataSnapshot.getValue(String.class);

                AdView bannerAd = new AdView(MainActivity.this);
                bannerAd.setAdUnitId(data);
                bannerAd.setAdSize(AdSize.SMART_BANNER);
                dashBannerCont.addView(bannerAd);
                AdRequest adRequest = new AdRequest.Builder().build();
                bannerAd.loadAd(adRequest);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    // Initializing Dialogue
    private void getDialog() {


        dashBoardDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        dashBoardDialog.setCancelable(false);

        ImageButton share_btn = dashBoardDialog.findViewById(R.id.share_btn);
        Button close_btn = dashBoardDialog.findViewById(R.id.close_btn);
        TextView contact_txt = dashBoardDialog.findViewById(R.id.email);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashBoardDialog.dismiss();
            }
        });

    }

    // Initialize Share Ap Function
    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String link = "http://play.google.com";
        String desc = "Download the App";
        shareIntent.putExtra(Intent.EXTRA_TEXT,desc);
        shareIntent.putExtra(Intent.EXTRA_TEXT,link);
        startActivity(Intent.createChooser(shareIntent,"Share Via"));

    }



    // Implement Double Back Pressed
    @Override
    public void onBackPressed() {

        if (onBackPressed){
            super.onBackPressed();
        }
        else {
            onBackPressed = true;
            Toast.makeText(this, "Press Again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed = false;
                }
            },3000);
        }



    }

    // Adding Interstitial Ad
    private void getIndianInter(String IndianAdId){

        Firebase.setAndroidContext(MainActivity.this);
        Firebase interFirebase = new Firebase(IndianAdId);

        interFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String IndianinterData = dataSnapshot.getValue(String.class);
                setNatureInterstetial(IndianinterData);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                Toast.makeText(MainActivity.this, "Error: "+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    // Set Nature Interstitial Ad
    private void setNatureInterstetial(String IndianinterstetialId) {

        AdRequest adRequest = new AdRequest.Builder().build();

        dashBoardInter.load(this, IndianinterstetialId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                dashBoardInter = null;
                Toast.makeText(MainActivity.this, "Error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                dashBoardInter = interstitialAd;

            }
        });

    }


    // Get Artistic Interstetial
    private void getUsaInter(String UsaAdId) {

        Firebase.setAndroidContext(MainActivity.this);
        Firebase interFirebase = new Firebase(UsaAdId);

        interFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String UsainterData = dataSnapshot.getValue(String.class);
                setUsaInterstetial(UsainterData);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                Toast.makeText(MainActivity.this, "Error: "+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Set Artistic Interstetial Ad
    private void setUsaInterstetial(String usainterstetialId) {

        AdRequest adRequest = new AdRequest.Builder().build();

        dashBoardInter.load(this, usainterstetialId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                dashBoardInter = null;
                Toast.makeText(MainActivity.this, "Error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                dashBoardInter = interstitialAd;

            }
        });

    }


    // Get Anonymous Interstetial
    private void getAsianInter(String AsianAdId) {

        Firebase.setAndroidContext(MainActivity.this);
        Firebase interFirebase = new Firebase(AsianAdId);

        interFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String asianInterData = dataSnapshot.getValue(String.class);
                setAsianInterstetial(asianInterData);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                Toast.makeText(MainActivity.this, "Error: "+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Set Anonymous Interstetial Ad
    private void setAsianInterstetial(String AsianinterstetialId) {

        AdRequest adRequest = new AdRequest.Builder().build();

        dashBoardInter.load(MainActivity.this, AsianinterstetialId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                dashBoardInter = null;
                Toast.makeText(MainActivity.this, "Error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                dashBoardInter = interstitialAd;

            }
        });

    }



    // Get Gaming Interstetial
    private void getSpanishInter(String SpanishAdId) {

        Firebase.setAndroidContext(MainActivity.this);
        Firebase interFirebase = new Firebase(SpanishAdId);

        interFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String spanishInterData = dataSnapshot.getValue(String.class);
                setSpanishInterstetial(spanishInterData);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                Toast.makeText(MainActivity.this, "Error: "+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Set Gaming Interstetial Ad
    private void setSpanishInterstetial(String SpanishinterstetialId) {

        AdRequest adRequest = new AdRequest.Builder().build();

        dashBoardInter.load(this, SpanishinterstetialId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                dashBoardInter = null;
                Toast.makeText(MainActivity.this, "Error: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                dashBoardInter = interstitialAd;

            }
        });

    }


    }