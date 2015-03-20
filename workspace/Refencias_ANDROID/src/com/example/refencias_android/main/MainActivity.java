package com.example.refencias_android.main;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.refencias_android.R;
import com.example.refencias_android.camera.CameraActivity;
import com.example.refencias_android.fotos.CircleViewFlowExample;
import com.example.referencias_android.crud.ListCrudActivity;
import com.example.referencias_android.gps.GPSActivity;
import com.example.referencias_android.login.LogoutUtills;
import com.example.referencias_android.model.Colaborador;
import com.htcom.padrao.drawer.DrawerItem;
import com.htcom.padrao.drawer.MenuUtills;

public class MainActivity extends Activity {

	MenuUtills menuUtills = new MenuUtills();
	List<DrawerItem> dataList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Colaborador colaborador = (Colaborador)getIntent().getSerializableExtra("Colaborador");
        setContentView(R.layout.main);
        TextView tv_titulo = (TextView)findViewById(R.id.tv_titulo);
        tv_titulo.setText("Usuário: "+colaborador.getNome());
        ConfigurarMenu();
        /*if (savedInstanceState == null) {
			SelectItem(0);
		}*/
    } 
    
  //==========PADRÃO PARA CRIAR MENU===============================

    @SuppressLint("NewApi") private void ConfigurarMenu(){
    	PreencherListMenu();
		mTitle = mDrawerTitle = getTitle();
		menuUtills.Inicializar((DrawerLayout) findViewById(R.id.drawer_layout),
				(ListView) findViewById(R.id.left_drawer), this, dataList);
        menuUtills.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, menuUtills.mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		
		menuUtills.mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    
    private void PreencherListMenu() {
    	dataList = new ArrayList<DrawerItem>();
		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("CRUD", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("GPS", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Câmera", R.drawable.ic_action_camera));
		dataList.add(new DrawerItem("Fotos", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("SharedPref", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Sair", R.drawable.ic_action_good));
	}
    
    @SuppressLint("NewApi") @Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
    
    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}
	
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //==========PADRÃO PARA CRIAR MENU===============================
	
	@SuppressLint("NewApi") public void SelectItem(int possition) {
		Fragment fragment = null;
		switch (possition) {
			case 0:
			startActivity(new Intent(this, ListCrudActivity.class));
			break;
			case 1:
				startActivity(new Intent(this, GPSActivity.class));
				break;
			case 2:
				fragment = new CameraActivity(); 
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
				break;
			case 3:
				startActivity(new Intent(this, CircleViewFlowExample.class));
				break;
			case 4:
				startActivity(new Intent(this, SharedPrefActivity.class));
				break;
			case 5:
				startActivity(new Intent(this, LogoutUtills.class));
				break;
			default:
				break;
		}

		menuUtills.mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		menuUtills.mDrawerLayout.closeDrawer(menuUtills.mDrawerList);
	}

}
