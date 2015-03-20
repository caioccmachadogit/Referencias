package com.htcom.padrao.interfaces;

public interface ISincronismo {
			
	public String getTabela();
	public String getCampos();
	public String getTag();
	public String getWhere(String dtUltSincronizacao);
}