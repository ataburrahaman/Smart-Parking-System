package com.example.smartparking.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CardView mapButton;
    private CardView liveBooking;
    private TextView uName;
    private TextView navUsername,navEmail;
    private DatabaseReference nameref,emailref;
    NavigationView navigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        //For Navigation User Name And Email Showing

        navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.userName);
        navEmail =(TextView)headerView.findViewById(R.id.userEmail);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


       mapButton=(CardView)findViewById(R.id.setLocation);
       liveBooking=findViewById(R.id.setLiveBooking);

        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
        nameref= FirebaseDatabase.getInstance().getReference().child("User").child("homeowner").child(uid).child("name");
        emailref = FirebaseDatabase.getInstance().getReference().child("User").child("homeowner").child(uid).child("email");
        //Toast.makeText(HomeMainActivity.this, "Data store successfully :"+nameref, Toast.LENGTH_SHORT).show();

       nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              try {

                  String name = dataSnapshot.getValue(String.class);
                  navUsername.setText(name);
              }
                catch(Throwable e) {

                      Toast.makeText(HomeMainActivity.this, "Error :" +e, Toast.LENGTH_SHORT).show();

                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            emailref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email=dataSnapshot.getValue(String.class);
                navEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeMainActivity.this,homeMapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        liveBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeMainActivity.this,LiveBookingShowActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent=new Intent(HomeMainActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();



            // Handle the camera action
        }else if(id == R.id.LogOut) {
            new Shared(HomeMainActivity.this).removeUser();
            Intent intent=new Intent(HomeMainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_income) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
