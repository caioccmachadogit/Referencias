package com.htcom.padrao.funcoes;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Constantes {

/*----------------------------------------------------------------------------------------------------------------------------------------*/  
// **** DADOS GERAIS
/*----------------------------------------------------------------------------------------------------------------------------------------*/ 
	public static String      NOME_BANCO,						
							  NOME_APK,
							  PROJETO,						
							  URL_LOGIN = "",
							  URL_UPDATE_VERSAO,
							  URL_RECEBE_ENDERECO,
							  URL_RECEBE_DADOS,
							  URL_ENVIA_BACKUP,
							  URL_ATUALIZA_DADOS,
							  URL_ENVIA_DADOS,
							  URL_ENVIA_POSICOES,
							  URL_ENVIA_SUPORTE,
							  URL_STATUS_SERVIDOR,
							  URL_ENVIA_DADOS_MULT_PART,
							  URL_UPLOAD_FOTO,
							  URL_DATA_SERVIDOR,
							  URL_UPLOAD_ANEXO,
							  CAMINHO_INTERNO_FOTOS;
	
	public static ArrayList<String> QUERY_CREATE_BANCO_DE_DADOS = new ArrayList<String>();
	public static ArrayList<String> QUERY_UPGRADE_BANCO_DE_DADOS = new ArrayList<String>();

	public static int 	 VERSAO_BANCO;
	
	public static double VERSAO_APP;
	public static String NOME_APP;
	
	public static final int	FLG_A_ENVIAR    = -1;
    public static final int FLG_ENVIADO     =  1;
    public static final int FLG_NAO_ENVIADO =  0;
    public static final int FLG_ATIVO 		=  1;       
    
    public static final int				CAPTURAR_POSICAO_1				= 5;
	public static final int				CAPTURAR_POSICAO_2				= 6;
	public static final int				CAPTURAR_POSICAO_3				= 7;
	public static final int				CAPTURAR_POSICAO_4				= 8;
	public static final int				CAPTURAR_POSICAO_5				= 11;
	public static final int				CAPTURAR_POSICAO_6				= 13;
	public static final int				ATIVAR_GPS_1					= 1;
	public static final int				ATIVAR_GPS_2					= 2;
	public static final int				ATIVAR_GPS_3					= 3;
	public static final int				ATIVAR_GPS_4					= 4;
	public static final int				ATIVAR_GPS_5					= 10;
	public static final int				ATIVAR_GPS_6					= 12;
	public static final int				ENVIAR_POSICOES					= 9;
	
    public static final int			STATUS_CRIADO					= 1;
	public static final int			STATUS_ABERTO					= 2;
	public static final int			STATUS_FINALIZADO				= 3;
	public static final int			STATUS_SINCRONIZADO				= 4;
	public static final int			STATUS_EXCLUIDO					= 5;
	public static final int			STATUS_ACEITA					= 6;
	public static final int			STATUS_REJEITADO				= 7;
	public static final int			STATUS_NEGOCIACAO_FINALIZADA	= 8;
	public static final int			STATUS_ERRO						= 9;
	public static final int			STATUS_CONCLUIDO				= 10;

	public static final String subModulos = "1, 4, 6, 7, 8, 9, 10";
	
	public static final String		TIPO_ARQUIVO_FOTO				= "foto";
	public static final String		TIPO_ARQUIVO_ANEXO				= "anexo";
	
	public static final String		TIPO_ESTABELECIMENTO_DISTRIBUIDOR				= "2";
	
	public static final String		ID_ATIVIDADE				= "1";
	public static final String		ID_POSICIONAMENTO			= "2";
	public static final String		ID_DISPLAY					= "3";	
	
	public static Cursor CURSOR;
	public static SQLiteDatabase db;
	
    public final static String VIEW_ENVIO_ROTEIROS = "VIEW_ENVIO_ROTEIROS";
    
    public static int ID_PESQUISA_PARCERIAS = 1;
    public static int ID_PESQUISA_BOSCH     = 2;
    public static int ID_PESQUISA_SERVICOS_OFERECIDOS    = 3;
    public static int CNPJ_EXISTENTE_WEB    = 100;
    public static int VISITA_EXISTENTE_WEB    = 200;
    
	
	public static final String[] LISTA_UF = new String[] { "UF", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI",
		"PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" };
	
	public static final  String SIGLA_ORIGEM = "TAB";
	public static final  String SIGLA_WEB    = "WEB";
	public static String PUT_EXTRA_ID_SETOR      			= "idSetor";
	public static String PUT_EXTRA_ID_USUARIO_WEB 		    = "idUsuarioWeb";
	public static String PUT_EXTRA_ID_ROTEIRO    			= "idRoteiro";
	public static String PUT_EXTRA_ID_VISITA     			= "IdVisita";
	public static String PUT_EXTRA_ID_ESTABELECIMENTO       = "IdEstabelecimento";
	public static String PUT_EXTRA_ID_ESTABELECIMENTO_WEB   = "IdEstabelecimentoWeb";
	public static String PUT_EXTRA_DATA		    			= "data";
	public static String PUT_EXTRA_NOME_PROMOTOR   			= "nomePromotor";
	public static String PUT_EXTRA_ID_COTACAO   			= "IdCotacao";
	public static String PUT_EXTRA_ID_TIPO_ATIVIDADE		= "IdTipoAtividade";
	public static String PUT_EXTRA_PESQUISA_REALIZADA		= "pesquisaRealizada";
	public static String PUT_EXTRA_CRIAR_ROTEIRO			= "criarRoteiro";
	public static String PREFERENCES_LOGIN	    			= "preferencesLogin";	
	public static  String TP_RECEBIMENTO_ENDERECO			= "xml";	
	
	public static Class<?> ACTIVITY_POSTERIOR_LOGIN;
	
	
}