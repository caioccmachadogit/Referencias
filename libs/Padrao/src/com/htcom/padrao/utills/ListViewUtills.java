package com.htcom.padrao.utills;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtills {
	
	public static void calculeHeightListView(ListView lv) {
		int totalHeight = 0;

		ListAdapter adapter = lv.getAdapter();
		int lenght = adapter.getCount();

		for (int i = 0; i < lenght; i++) {
			View listItem = adapter.getView(i, null, lv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
		lv.setLayoutParams(params);
		lv.requestLayout();
	}

}
