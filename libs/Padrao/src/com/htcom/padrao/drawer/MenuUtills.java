package com.htcom.padrao.drawer;

import java.util.List;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.htcom.padrao.funcoes.R;

public class MenuUtills {
	public DrawerLayout mDrawerLayout;
	public ListView mDrawerList;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;
	
	public void Inicializar(DrawerLayout layout, ListView list, Context context, List<DrawerItem> dataList){
		// Initializing
		//dataList = new ArrayList<DrawerItem>();
		mDrawerLayout = layout;
		mDrawerList = list;
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		
		adapter = new CustomDrawerAdapter(context, R.layout.custom_drawer_item, dataList);
		mDrawerList.setAdapter(adapter);
	}

}
