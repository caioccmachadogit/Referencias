// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 05/12/2013 15:33:36
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FTPManager.java

package com.htcom.padrao.funcoes;

import android.util.Log;
import java.io.*;
import org.apache.commons.net.ftp.*;

public class FTPManager
{

    public FTPManager()
    {
        TAG = "clsFTP";
    }

    public FTPFile[] dir(String pDiretorio)
    {
        try
        {
            FTPFile ftpFiles[] = mFtp.listFiles(pDiretorio);
            return ftpFiles;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel listar os arquivos e pastas do diret\363rio ")).append(pDiretorio).append(". ").append(e.getMessage()).toString());
        }
        return null;
    }

    public String diretorioCorrente()
    {
        try
        {
            String workingDir = mFtp.printWorkingDirectory();
            return workingDir;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel obter o diret\363rio atual. ")).append(e.getMessage()).toString());
        }
        return null;
    }

    public boolean isConnected()
    {
        return mFtp.isConnected();
    }

    public boolean mudarDiretorio(String pDiretorio)
    {
        try
        {
            criarDiretorio(pDiretorio);
            return mFtp.changeWorkingDirectory(pDiretorio);
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel mudar o diret\363rio para ")).append(pDiretorio).toString());
        }
        return false;
    }

    public boolean criarDiretorio(String pDiretorio)
    {
        try
        {
            boolean status = mFtp.makeDirectory(pDiretorio);
            return status;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel criar o diret\363rio ")).append(pDiretorio).toString());
        }
        return false;
    }

    public boolean removeDiretorio(String pDiretorio)
    {
        try
        {
            boolean status = mFtp.removeDirectory(pDiretorio);
            return status;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel remover o diret\363rio ")).append(pDiretorio).toString());
        }
        return false;
    }

    public boolean delete(String pArquivo)
    {
        try
        {
            boolean status = mFtp.deleteFile(pArquivo);
            return status;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean rename(String from, String to)
    {
        try
        {
            boolean status = mFtp.rename(from, to);
            return status;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: N\343o pode renomear o arquivo: ")).append(from).append(" para: ").append(to).toString());
        }
        return false;
    }

    public boolean download(String pDiretorioOrigem, String pArqOrigem, String pArqDestino)
    {
        boolean status = false;
        try
        {
            mudarDiretorio(pDiretorioOrigem);
            OutputStream desFileStream = new FileOutputStream(pArqDestino);
            mFtp.setFileType(2);
            mFtp.setControlEncoding("UTF-8");
            mFtp.enterLocalPassiveMode();
            mFtp.enterLocalActiveMode();
            status = mFtp.retrieveFile(pArqOrigem, desFileStream);
            if(!status)
            {
                File file = new File(pArqDestino);
                if(file.exists())
                    file.delete();
            }
            desFileStream.close();
            return status;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: Falha ao efetuar download. ")).append(e.getMessage()).toString());
        }
        return status;
    }

    public boolean upload(String pArqOrigem, String pArqDestino, String pDiretorioDestino)
    {
        boolean status = false;
        try
        {
            FileInputStream srcFileStream = new FileInputStream(pArqOrigem);
            if(mudarDiretorio(pDiretorioDestino))
                status = mFtp.storeFile(pArqDestino, srcFileStream);
            srcFileStream.close();
            return status;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: Falha ao efetuar Upload. ")).append(e.getMessage()).toString());
        }
        return status;
    }

    public boolean desconectar()
    {
        try
        {
            mFtp.disconnect();
            mFtp = null;
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: ao desconectar. ")).append(e.getMessage()).toString());
            return false;
        }
        return true;
    }

    public boolean conectar(String pHost, String pUsuario, String pSenha, int pPorta)
    {
        try
        {
            mFtp = new FTPClient();
            mFtp.connect(pHost, pPorta);
            if(FTPReply.isPositiveCompletion(mFtp.getReplyCode()))
            {
                boolean status = mFtp.login(pUsuario, pSenha);
                mFtp.setFileType(2);
                mFtp.enterLocalPassiveMode();
                return status;
            }
        }
        catch(Exception e)
        {
            Log.e(TAG, (new StringBuilder("Erro: n\343o foi poss\355vel conectar ao host ")).append(pHost).toString());
        }
        return false;
    }

    FTPClient mFtp;
    private String TAG;
}
