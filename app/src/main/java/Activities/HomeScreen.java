package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.AdapterViewFlipper;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infusibleCoder.grocerystorefyp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.FlipperAdapter;
import Interface.GetSliderItemPosition;
import Models.NewsModel;

public class HomeScreen extends AppCompatActivity implements GetSliderItemPosition {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;


    List<NewsModel> mList = new ArrayList<>();
    private FirebaseDatabase mdatabase;
    private DatabaseReference mRef;

    //recycler
    RecyclerView categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
//    List<CategoryModel> catList = new ArrayList<>();
//    CategoryAdapter adapter;


    //fipper
    private AdapterViewFlipper adapterViewFlipper;

    // private int postition =-1;

    TextView[] mDots;
    LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);



        adapterViewFlipper = findViewById(R.id.mFlipper);
        mdatabase = FirebaseDatabase.getInstance();
        mRef = mdatabase.getReference("News");


        //creating 3 dots
        mLayout = findViewById(R.id.txt_dot);

        //createing adapter object


        final FlipperAdapter adapter = new FlipperAdapter(HomeScreen.this,mList,this);

        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NewsModel model = ds.getValue(NewsModel.class);
                    mList.add(model);

                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sort_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view

        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.LEFT);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = new HomeFragment();
//        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:

                mDrawer.closeDrawers();
                break;
            case R.id.nav_category:
                toolbar.setTitle("Category");
//                openFragment(new AllLoansFragment());
////                menuItem.setChecked(true);
////                // Set action bar title
////                setTitle(menuItem.getTitle());
////                // Close the navigation drawer
                mDrawer.closeDrawers();
                break;
            case R.id.nav_my_orders:
//                openFragment(new LatestLoanFragment());
////                menuItem.setChecked(true);
////                // Set action bar title
////                setTitle(menuItem.getTitle());
////                // Close the navigation drawer
                mDrawer.closeDrawers();
                break;
            case R.id.nav_profile:

                mDrawer.closeDrawers();
                break;
            case R.id.nav_settnig:

                mDrawer.closeDrawers();
                break;
            case R.id.nav_logout:

                startActivity(new Intent(this, LoginActivity.class));
                logout();
                finish();
                break;
            default:
                //fragmentClass = FirstFragment.class;
        }

    }

    public void addDotsIndecator(int position){


        mDots = new TextView[3];
        mLayout.removeAllViews();
        for (int i = 0; i< mDots.length; i ++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.gray));
            mLayout.addView(mDots[i]);
        }

        if(mDots.length >= 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }


    @Override
    public void getSlider(int position) {


        // Toast.makeText(this, "My pos" +position, Toast.LENGTH_SHORT).show();
        addDotsIndecator(position);
    }

    public void logout(){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}
