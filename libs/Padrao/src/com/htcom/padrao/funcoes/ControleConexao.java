package com.htcom.padrao.funcoes;

import android.content.Context;
import android.net.ConnectivityManager;

public class ControleConexao {

	public boolean isOnline(Context context) {

		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() != null
					&& cm.getActiveNetworkInfo().isAvailable()
					&& cm.getActiveNetworkInfo().isConnected()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}