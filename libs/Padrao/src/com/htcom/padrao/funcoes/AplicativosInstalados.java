package com.htcom.padrao.funcoes;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;

public class AplicativosInstalados
{
	private Context context;
    private String nome = "";
    private String versaoNome = "";
    private int versaoCodigo = 0;

	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getVersaoNome()
	{
		return versaoNome;
	}

	public void setVersaoNome(String versaoNome)
	{
		this.versaoNome = versaoNome;
	}

	public int getVersaoCodigo()
	{
		return versaoCodigo;
	}

	public void setVersaoCodigo(int versaoCodigo)
	{
		this.versaoCodigo = versaoCodigo;
	}

	public ArrayList<AplicativosInstalados> getPackages(Context context) 
	{
		this.context = context;
	    ArrayList<AplicativosInstalados> apps = getInstalledApps(false); 
	    return apps;
	}

	private ArrayList<AplicativosInstalados> getInstalledApps(boolean getSysPackages) 
	{
	    ArrayList<AplicativosInstalados> res = new ArrayList<AplicativosInstalados>();        
	    List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
	    for(int i=0;i<packs.size();i++) 
	    {
	        PackageInfo p = packs.get(i);
	        if ((!getSysPackages) && (p.versionName == null)) 
	        {
	            continue ;
	        }
	        
	        AplicativosInstalados aplicativosIntalados = new AplicativosInstalados();
	        aplicativosIntalados.setNome(p.applicationInfo.loadLabel(context.getPackageManager()).toString());
	        aplicativosIntalados.setVersaoNome(p.versionName);
	        aplicativosIntalados.setVersaoCodigo(p.versionCode);
	        res.add(aplicativosIntalados);
	    }
	    return res; 
	}
}
