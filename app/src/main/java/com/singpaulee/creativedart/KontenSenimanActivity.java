package com.singpaulee.creativedart;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.singpaulee.creativedart.SharedPreference.SharedPrefManager;
import com.singpaulee.creativedart.fragment.GaleriFragment;
import com.singpaulee.creativedart.fragment.HomeFragment;
import com.singpaulee.creativedart.fragment.ProfilFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class KontenSenimanActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	SharedPrefManager sharedPrefManager;
	private boolean doubleBackToExitPressedOnce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_konten_seniman);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		sharedPrefManager = new SharedPrefManager(this);


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		getSupportFragmentManager()
				.beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.replace(R.id.frame_layout, new HomeFragment())
				.commit();

		View header = navigationView.getHeaderView(0);
		TextView name = (TextView) header.findViewById(R.id.header_tv_username);
		name.setText(sharedPrefManager.getFirstNama()+" "+sharedPrefManager.getLastName());
		TextView email = (TextView) header.findViewById(R.id.header_tv_email);
		email.setText(sharedPrefManager.getEmail());
		CircleImageView civ = (CircleImageView) header.findViewById(R.id.header_image);
		Picasso.with(this)
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/"+sharedPrefManager.getPhoto())
				.error(R.drawable.profil)
				.into(civ);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		View header = navigationView.getHeaderView(0);
		TextView name = (TextView) header.findViewById(R.id.header_tv_username);
		name.setText(sharedPrefManager.getFirstNama()+" "+sharedPrefManager.getLastName());
		TextView email = (TextView) header.findViewById(R.id.header_tv_email);
		email.setText(sharedPrefManager.getEmail());
		CircleImageView civ = (CircleImageView) header.findViewById(R.id.header_image);
		Picasso.with(this)
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/"+sharedPrefManager.getPhoto())
				.into(civ);
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			if (doubleBackToExitPressedOnce) {
				super.onBackPressed();
				finishAffinity();
				return;
			}
			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "Ketuk lagi untuk keluar", Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					doubleBackToExitPressedOnce = false;
					finishAffinity();
				}
			}, 2000);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.konten_seniman, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.menu_utama) {
			getSupportFragmentManager()
					.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(R.id.frame_layout, new HomeFragment())
					.commit();
		}else if(id == R.id.menu_profil){
			getSupportFragmentManager()
					.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(R.id.frame_layout, new ProfilFragment())
					.commit();
		}else if(id == R.id.menu_galeri){
			getSupportFragmentManager()
					.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.replace(R.id.frame_layout, new GaleriFragment())
					.commit();
		}else if(id == R.id.menu_keluar){
			sharedPrefManager.deletePref();
			sharedPrefManager.savePrefBoolean(SharedPrefManager.LOGIN,false);
			if (!sharedPrefManager.getSudahLogin()){
				finish();
			}
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
