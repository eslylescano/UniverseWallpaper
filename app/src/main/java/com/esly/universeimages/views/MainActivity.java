package com.esly.universeimages.views;

import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.appbrain.AdService;
import com.appbrain.AppBrain;
import com.esly.universeimages.R;
import com.esly.universeimages.animations.AccordionTransformer;
import com.esly.universeimages.animations.BackgroundToForegroundTransformer;
import com.esly.universeimages.animations.CubeInTransformer;
import com.esly.universeimages.animations.CubeOutTransformer;
import com.esly.universeimages.animations.DefaultTransformer;
import com.esly.universeimages.animations.DepthPageTransformer;
import com.esly.universeimages.animations.FlipHorizontalTransformer;
import com.esly.universeimages.animations.FlipVerticalTransformer;
import com.esly.universeimages.animations.ForegroundToBackgroundTransformer;
import com.esly.universeimages.animations.RotateDownTransformer;
import com.esly.universeimages.animations.RotateUpTransformer;
import com.esly.universeimages.animations.ScaleInOutTransformer;
import com.esly.universeimages.animations.StackTransformer;
import com.esly.universeimages.animations.TabletTransformer;
import com.esly.universeimages.animations.ZoomInTransformer;
import com.esly.universeimages.animations.ZoomOutTranformer;
import com.esly.universeimages.utils.ContentItem;
import com.esly.universeimages.utils.NavigationDrawerFragment;

import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    ViewPager vp;
    private Advertisement advertisement;
    private Toolbar toolbar;
    private ShareActionProvider mShareActionProvider;
    private final ArrayList<ContentItem> mItems = getSampleContent();
    ImageButton  pictureButton, shareButton, icLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        AppBrain.init(this);
        setContentView(R.layout.activity_main);



        //for the ads
        advertisement = new Advertisement(this);
        advertisement.loadInterstitial();
        advertisement.loadExitInterstitialAd();
        LinearLayout adLayout = (LinearLayout) findViewById(R.id.main_wrapper);

        advertisement.loadBanner(adLayout);


        //for the toolbar
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //for the toolBar buttons
       // downloandButton =(ImageButton)findViewById(R.id.ic_action_download);
        pictureButton =(ImageButton)findViewById(R.id.ic_action_picture);
        shareButton =(ImageButton)findViewById(R.id.ic_action_share);
        icLauncher = (ImageButton)findViewById(R.id.ic_launcher);



       // downloandButton.setOnClickListener(this);
        pictureButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        icLauncher.setOnClickListener(this);





        //for go back when I am not in the main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for create the NavigationDrawe
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        //Le paso los parametros en el metodo SetUP de su clase(el id,el objeto drawer, el objeto toolbar)
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        //para el viewpager
         vp = (ViewPager) findViewById(R.id.viewpager);

        vp.setOnPageChangeListener(mOnPageChangeListener);

        // Finally set the adapter so the ViewPager can display items
        vp.setAdapter(mPagerAdapter);
        vp.setPageTransformer(true, getAnimation(3));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFinishing()) {
            AppBrain.getAds().showInterstitial(this);
        }
    }

    //Listen clicks in the icons
    @Override
    public void onClick(View v) {
        int position = ((ViewPager) findViewById(R.id.viewpager)).getCurrentItem();
        ContentItem item = mItems.get(position);
        String imageName = item.contentAssetFilePath;

        switch (v.getId()){



            case R.id.ic_action_picture:

                try {
                    WallpaperManager myWallpaperManager =WallpaperManager.getInstance(getApplicationContext());
                    InputStream ims = getAssets().open(imageName);
                    myWallpaperManager.setStream(ims);
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.changing_wallpaper), Toast.LENGTH_SHORT)
                            .show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),
                            "Error setting wallpaper", Toast.LENGTH_SHORT)
                            .show();
                }



                break;

            case R.id.ic_action_share:

                Intent shareIntent = item.getShareIntent(MainActivity.this);

                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/developer?id=Esly");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "https://play.google.com/store/apps/developer?id=Esly");
                startActivity(shareIntent);

                break;

            case R.id.ic_launcher:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Esly"));
                startActivity(intent);
                break;


        }

    }



    public ViewPager.PageTransformer getAnimation(int currentAnimation){

        switch (currentAnimation){
            case 0:

                return new AccordionTransformer();

            case 1:

                return new BackgroundToForegroundTransformer();

            case 2:

                return new CubeInTransformer();

            case 3:

                return new CubeOutTransformer();

            case 4:

                return new DefaultTransformer();

            case 5:

                return new DepthPageTransformer();

            case 6:

                return new FlipHorizontalTransformer();

            case 7:

                return new FlipVerticalTransformer();

            case 8:

                return new ForegroundToBackgroundTransformer();

            case 9:

                return new RotateDownTransformer();

            case 10:

                return new RotateUpTransformer();
            case 11:

                return new ScaleInOutTransformer();
            case 12:

                return new StackTransformer();
            case 13:

                return new TabletTransformer();

            case 14:

                return new ZoomInTransformer();

            case 15:

                return new ZoomOutSlideTransformer();

            case 16:

                return new ZoomOutTranformer();




        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        AdService ads = AppBrain.getAds();
        MenuItem item = menu.add(ads.getOfferWallButtonLabel(this));
        ads.setOfferWallMenuItemClickListener(this, item);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acordion) {
            vp.setPageTransformer(true, getAnimation(0));
            Toast.makeText(getApplicationContext(),R.string.action_acordion,Toast.LENGTH_LONG).show();


            return true;
        }

        if (id == R.id.action_backgroundToForeground) {
            vp.setPageTransformer(true, getAnimation(1));
            Toast.makeText(getApplicationContext(),R.string.action_backgroundToForeground,Toast.LENGTH_LONG).show();
            return true;


        }

        if (id == R.id.action_cubeIn) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(2));
            Toast.makeText(getApplicationContext(),R.string.action_cubeIn,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_CubeOut) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(3));
            Toast.makeText(getApplicationContext(),R.string.action_CubeOut,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_Default) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(4));
            Toast.makeText(getApplicationContext(),R.string.action_Default,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_depthPage) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(5));
            Toast.makeText(getApplicationContext(),R.string.action_depthPage,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_flipHorizontal) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(6));
            Toast.makeText(getApplicationContext(),R.string.action_flipHorizontal,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_flipVertical) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(7));
            Toast.makeText(getApplicationContext(),R.string.action_flipVertical,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_foregroundToBackgroung) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(8));
            Toast.makeText(getApplicationContext(),R.string.action_foregroundToBackgroung,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_rotateDown) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(9));
            Toast.makeText(getApplicationContext(),R.string.action_rotateDown,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_rotateUp) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(10));
            Toast.makeText(getApplicationContext(),R.string.action_rotateUp,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_scaleInOut) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(11));
            Toast.makeText(getApplicationContext(),R.string.action_scaleInOut,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_stack) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(12));
            Toast.makeText(getApplicationContext(),R.string.action_stack,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_tablet) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(13));
            Toast.makeText(getApplicationContext(),R.string.action_tablet,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_zoomIn) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(14));
            Toast.makeText(getApplicationContext(),R.string.action_zoomIn,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_zoomOutSlide) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(15));
            Toast.makeText(getApplicationContext(),R.string.action_zoomOutSlide,Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_zoomOut) {
            //Toast.makeText(getApplicationContext(),"Hey you just pressed me",Toast.LENGTH_SHORT).show();

            vp.setPageTransformer(true, getAnimation(16));
            Toast.makeText(getApplicationContext(),R.string.action_zoomOut,Toast.LENGTH_LONG).show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private final PagerAdapter mPagerAdapter = new PagerAdapter() {
        LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Just remove the view from the ViewPager
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Ensure that the LayoutInflater is instantiated
            if (mInflater == null) {
                mInflater = LayoutInflater.from(MainActivity.this);
            }

            // Get the item for the requested position
            final ContentItem item = mItems.get(position);

            // The view we need to inflate changes based on the type of content
            switch (item.contentType) {
                case ContentItem.CONTENT_TYPE_TEXT: {
                    // Inflate item layout for text
                    TextView tv = (TextView) mInflater
                            .inflate(R.layout.item_text, container, false);

                    // Set text content using it's resource id
                    tv.setText(item.contentResourceId);

                    // Add the view to the ViewPager
                    container.addView(tv);
                    return tv;
                }
                case ContentItem.CONTENT_TYPE_IMAGE: {
                    // Inflate item layout for images
                    ImageView iv = (ImageView) mInflater
                            .inflate(R.layout.item_image, container, false);

                    // Load the image from it's content URI
                    iv.setImageURI(item.getContentUri());

                    // Add the view to the ViewPager
                    container.addView(iv);
                    return iv;
                }
            }

            return null;
        }
    };

    private void setShareIntent(int position) {
        if (mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            ContentItem item = mItems.get(position);
            Intent shareIntent = item.getShareIntent(MainActivity.this);

            // Now update the ShareActionProvider with the new share intent
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // NO-OP
        }

        @Override
        public void onPageSelected(int position) {
            setShareIntent(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // NO-OP
        }
    };

    static ArrayList<ContentItem> getSampleContent() {
        ArrayList<ContentItem> items = new ArrayList<ContentItem>();
        //img-000

        String photoName= "";
        String extension = ".jpg";
        int numberImages = 149;

        int numberPositionName;
        StringBuffer sb;
        String finalName;
        int numberTimes =4;

        for(int i = 0; i<numberImages*numberTimes;i++){
            sb =new StringBuffer();
            numberPositionName = i%numberImages;
            numberPositionName =numberPositionName+1;
            sb.append(photoName);
            sb.append(numberPositionName);
            sb.append(extension);
            finalName = sb.toString();

            items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, finalName));
        }

/*
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_1.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_2.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_3.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_4.jpg"));
        items.add(new ContentItem(ContentItem.CONTENT_TYPE_IMAGE, "photo_5.jpg"));*/


        return items;
    }








}
