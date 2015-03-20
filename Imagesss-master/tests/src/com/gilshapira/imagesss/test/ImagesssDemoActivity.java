package com.gilshapira.imagesss.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gilshapira.imagesss.DrawableStore;
import com.gilshapira.imagesss.ImageDownloader;
import com.gilshapira.imagesss.ImageStore;
import com.gilshapira.imagesss.views.RemoteImageView;

/**
 * Demo activity that pulls a list of images from Flickr and shows them in a list.
 *
 * @author Gil Shapira
 */
public class ImagesssDemoActivity extends ListActivity {
    
    public static final String LOGTAG = "ImagesssDemoActivity";
    
    private List<String> mImages;
    
    private ImageStore mImageStore;
    
    private DrawableStore mDrawableStore;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // initialize stuffs
        mImageStore = new ImageStore();
        mImageStore.setNetworkDelegate(new ImageDownloader());
        
        mDrawableStore = new DrawableStore(this, mImageStore);
        
        RemoteImageView.setDefaultDrawableStore(mDrawableStore);

        // start a background task to load a list of images from Flickr
        setTitle("Loading...");
        new LoadImagesTask(this).execute();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        mDrawableStore.clearCallbacks();
    }

    /**
     * Process a list of images returned by the background task.
     */
    private void processImages(List<String> images) {
        setTitle("Showing images");
        mImages = images;
        setListAdapter(new ImagesAdapter());
    }
    
    /**
     * An adapter that returns ListView views for each image.
     */
    private class ImagesAdapter extends BaseAdapter {

        @Override
        public String getItem(int position) {
            return mImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ImagesssDemoActivity.this).inflate(R.layout.listitem, null);
                RemoteImageView imageView = (RemoteImageView) convertView.findViewById(R.id.image);
                convertView.setTag(imageView);
            }
            
            RemoteImageView imageView = (RemoteImageView) convertView.getTag();
            imageView.loadImage(getItem(position));
            
            return convertView;
        }
        
    }

    /**
     * Background task to load a list of image URLs from a Flickr image set. 
     */
    private static class LoadImagesTask extends AsyncTask<Void, Void, List<String>> {
        
        private static final String URL = "http://api.flickr.com/services/feeds/photoset.gne?set=72157622662466829&nsid=76194867@N00&lang=en-us&format=json";
        private static final String PREFIX = "jsonFlickrFeed(";
        private static final String SUFFIX = ")";
        
        private WeakReference<ImagesssDemoActivity> mActivity;
        
        public LoadImagesTask(ImagesssDemoActivity activity) {
            mActivity = new WeakReference<ImagesssDemoActivity>(activity);
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> result = new ArrayList<String>();
            
            try {
                // load the Flickr response
                String file = downloadFile(URL);
                
                // remove Javascript stuff from it so we can convert to JSON
                file = file.substring(PREFIX.length(), file.length() - SUFFIX.length());
                
                JSONObject obj = new JSONObject(file);
                
                // get images from the response
                JSONArray items = obj.getJSONArray("items");
                for (int i = 0; i < items.length(); ++i) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject media = item.getJSONObject("media");
                    String image = media.getString("m");
                    Log.v(LOGTAG, "Added image " + image);
                    result.add(image);
                }
            }
            catch(JSONException e) {
                Log.e(LOGTAG, "Couldn't parse JSON", e);
            }
            catch(Exception e) {
                Log.e(LOGTAG, "Couldn't load file", e);
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(List<String> result) {
            ImagesssDemoActivity act = mActivity.get();
            if (act != null) {
                act.processImages(result);
            }
        }

        /**
         * Downloads the contents of the file at the specified URL.
         */
        private String downloadFile(String url) throws Exception {
            StringBuilder builder = new StringBuilder();
            
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                builder.append(line);
            }
            
            return builder.toString();
        }
        
    }

}
