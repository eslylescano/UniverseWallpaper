package com.esly.universeimages.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esly.universeimages.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {




    ListView mListView;

    //creo nombre de archivo
    public static final String PREF_FILNE_NAME = "testpref";

    //palabra clave para guardar el drawer dentro de sharedPReferences
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    //objeto para crear el boton que desplegue el drawerlayout
    private ActionBarDrawerToggle mDrawerToggle;

    //objeto drawer layout
    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;

    private VivzAdapter adapter;


    //indica si siexiste el drawer
    private boolean mUserLearnedDrawer;

    //si existe el fragmento
    private boolean mFromSavedInstanceState;

    private View containerView;




    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //instancio la variable con valor falso de las preferencias
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        //si se ha guardado alguna instancia, la variable valdra true
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_navigation_drawer, container,false );
       recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new VivzAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return layout;
    }

     public  List<Information> getData(){
        final List<Information> data = new ArrayList<>();


//        int[] icons = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img10,R.drawable.img11,
//                R.drawable.img12,R.drawable.img13,R.drawable.img14,R.drawable.img15,R.drawable.img16,R.drawable.img17,R.drawable.img18,R.drawable.img19,R.drawable.img20,R.drawable.img21,R.drawable.img22,R.drawable.img23,
//                R.drawable.img24};
//
//        String[] titles =getResources().getStringArray(R.array.applicationsName);
//        String[] urls =getResources().getStringArray(R.array.applicationsURL);
//
//        for(int i =0;i<titles.length&&i<icons.length&&i<urls.length;i++){
//            Information current = new Information();
//
//            current.iconId=icons[i];
//            current.title=titles[i];
//            current.url=urls[i];
//
//            data.add(current);
//
//        }

         ParseQuery<ParseObject> query = ParseQuery.getQuery("Applications");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> applications, ParseException e) {
                            if(e==null){
                                for(ParseObject parseObject : applications){
                                    String title = (String) parseObject.get("title");
                                    String url = (String) parseObject.get("url");
                                    String icon = (String) parseObject.get("icon");

                                    Information information = new Information();
                                    information.title=title;
                                    information.url=url;
                                    information.iconId=icon;
                                    data.add(information);
                                }

                }else {
                    Log.d("score", "Error: " + e.getMessage());
                }
             }
         });


         return data;

    }




    //recibo los parametros enviados desde el mainActivity
    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {

        //recojo la actividad
        containerView = getActivity().findViewById(fragmentID);

        //instancio el drawer
        mDrawerLayout = drawerLayout;

        ////instacio el objeto drawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {

                    mUserLearnedDrawer = true;

                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);

                }

            }
        };

        //si no hay drawer ni fragmento, abrir el drawer
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {

            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        //boton para que aparezca la hamburguesa a la izquierda del titulo de la actividad
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    //metodo para guardar preferencias
    public static void saveToPreferences(Context context, String PreferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILNE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceName, preferenceValue);
        editor.apply();
    }

    //funcion para recoger preferencias
    public static String readFromPreferences(Context context, String PreferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILNE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PreferenceName, preferenceValue);
    }




}
