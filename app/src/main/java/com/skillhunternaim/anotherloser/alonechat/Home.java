package com.skillhunternaim.anotherloser.alonechat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseUser mUser,currUser;
    SharedPreferences sharedPreferences,userInfoPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        sharedPreferences=getPreferences(MODE_PRIVATE);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///////////////////// Saving user info so that it can be used in all the Fragments easily.
        //saveUserInfoInSharedPref();
        Fragment fragment=new Fragment();
        if(sharedPreferences.contains("frag")){
            if(sharedPreferences.getString("frag","").equals("1")){
                toolbar.setTitle("Routine");
                fragment=new FragmentRoutine();
            }else if(sharedPreferences.getString("frag","").equals("2")){
                toolbar.setTitle("Students");
                fragment=new FragmentStudentsBatch();
            }else{
                toolbar.setTitle("Personal Notes");
                fragment=new FragmentsListNotes();
            }

        }else {
            toolbar.setTitle("Routine");
            fragment=new FragmentRoutine();

        }
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.id_content_fragment_holder,fragment);
            fragmentTransaction.commit();




    }



    protected void onDestroy() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("frag","1");
        editor.apply();
        super.onDestroy();
    }
    protected void onPause() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("frag", "1");
        editor.apply();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Want to Exit???")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog= builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent=new Intent(this,ProfilleOwn.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_admin) {
            Intent intent=new Intent(this,AdminPanel.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_Logout) {
            mAuth.signOut();
            mUser=mAuth.getCurrentUser();
            SharedPreferences s=getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor=s.edit();
            editor.clear();
            editor.apply();

            Intent intent=new Intent(this,Login.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Fragment fragment=null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_routine) {
            toolbar.setTitle("Routine");
            fragment=new FragmentRoutine();
            editor.putString("frag","1");
            editor.apply();

        } else if (id == R.id.nav_students) {
            toolbar.setTitle("Students");
            fragment = new FragmentStudentsBatch();
            editor.putString("frag","2");
            editor.apply();

        } else if (id == R.id.nav_notes) {
            toolbar.setTitle("Personal Notes");
            fragment=new FragmentsListNotes();
            editor.putString("frag","3");
            editor.apply();

        } else if (id == R.id.nav_setting) {
            toolbar.setTitle("Settings");
            fragment=new FragmentSettings();

        } else if (id == R.id.nav_rate_share) {
            toolbar.setTitle("Rate and Share");
            fragment=new FragmentRateAndShare();

        } else if (id == R.id.nav_about_developer) {
            toolbar.setTitle("About Developer");
            fragment=new AboutDeveloperFragment();
        }


        if(fragment!=null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.id_content_fragment_holder,fragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    private void saveUserInfoInSharedPref() {
//        currUser= FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("user");
//        ref.child(Objects.requireNonNull(currUser.getDisplayName())).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ModelStudent m=dataSnapshot.getValue(ModelStudent.class);
//
//                userInfoPref=getSharedPreferences("userInfo",MODE_PRIVATE);
//                SharedPreferences.Editor editor = userInfoPref.edit();
//                editor.putString("fullname", Objects.requireNonNull(m).getFullname());
//                editor.putString("university",m.getUniversity());
//                editor.putString("dept",m.getDept());
//                editor.putString("batch",m.getBatch());
//                editor.putString("user_id",currUser.getDisplayName());
//                editor.apply();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(),"Error in getting dir",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}
