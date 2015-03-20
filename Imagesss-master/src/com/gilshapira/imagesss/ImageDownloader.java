package com.gilshapira.imagesss;

import java.io.IOException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.gilshapira.imagesss.ImageStore.NetworkDelegate;


/**
 * A simple implementation of the {@code NetworkDelegate} interface using 
 * the Apache HTTP library.
 *
 * @author Gil Shapira
 */
public class ImageDownloader implements NetworkDelegate {
    
    public static final String LOGTAG = "ImageDownloader";
    
    private static final int MAX_IMAGE_SIZE = 512*1024;
    private static final int MAX_TOTAL_CONNECTIONS = 50;
    private static final int MAX_HOST_CONNECTIONS = 10;
    private static final int NETWORK_TIMEOUT = 15000;
    private static final boolean REUSE_CONNECTIONS = false;
    
    /** The {@code HttpClient} used to execute requests. */
    private final HttpClient mClient;
    
    /**
     * Creates a new {@code ImageDownloader} object.
     */
    public ImageDownloader() {
        mClient = createThreadSafeHttpClient();
    }
    
    @Override
    public byte[] download(String url) {
        byte[] data = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = mClient.execute(httpGet);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                Header type = responseEntity.getContentType();
                // we discard the image data if we can determine that what we downloaded
                // wasn't actually an image, or if the image we downloaded is too large 
                if ((type == null) || (type.getValue() == null) || (type.getValue().contains("image"))) {
                    if (responseEntity.getContentLength() <= MAX_IMAGE_SIZE) {
                        data = EntityUtils.toByteArray(responseEntity);
                    }
                }
                responseEntity.consumeContent();
            }
        }
        catch(IllegalArgumentException e) {
            Log.d(LOGTAG, "Couldn't load image for URL: "+url, e);
        }
        catch(IOException e) {
            Log.d(LOGTAG, "Couldn't load image for URL: "+url, e);
        }
        
        // verify again that any returned data isn't too big
        if ((data != null) && (data.length > MAX_IMAGE_SIZE)) {
            data = null;
        }
        
        return data;
    }

    /**
     * Creates a thread-safe reusable {@code HttpClient}.
     * @return the {@code HttpClient}.
     */
    private static HttpClient createThreadSafeHttpClient() {
        // define general client parameters
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTIONS);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRoute() {
            public int getMaxForRoute(HttpRoute route) {
                return MAX_HOST_CONNECTIONS;
            }
        });
        HttpConnectionParams.setConnectionTimeout(params, NETWORK_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, NETWORK_TIMEOUT);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        
        // Create and initialize scheme registry
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

        HttpClient httpClient;
        if (REUSE_CONNECTIONS) {
            httpClient = new DefaultHttpClient(cm, params);
        } else {
            httpClient = new NoConnectionReuseHttpClient(cm, params);
        }
        
        return httpClient;
    }
    
    /**
     * A {@code DefaultHttpClient} that doesn't keep connection reuse caches.
     */
    private static class NoConnectionReuseHttpClient extends DefaultHttpClient {
        
        public NoConnectionReuseHttpClient(ClientConnectionManager cm, HttpParams params) {
            super(cm, params);
        }

        @Override
        protected ConnectionReuseStrategy createConnectionReuseStrategy() {
            return new NoConnectionReuseStrategy();
        }
        
    }
    
}
