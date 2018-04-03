package com.example.vbarboza.singwell2;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by evaramirez on 4/2/18.
 */

public class CustomListAdapterMusicLibrary extends ArrayAdapter<Card> {

    private static final String TAG = "CustomLAdaptLibrary";

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView composer;
        TextView instrumentation;
        RelativeLayout parentLayout;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CustomListAdapterMusicLibrary(Context context, int resource, ArrayList<Card> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        //sets up the image loader library
        setupImageLoader();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //get the event information
        String title = getItem(position).getTitle();
        String composer = getItem(position).getComposer();
        String instrumentation = getItem(position).getInstrumentation();

        try{
            //ViewHolder object
            final CustomListAdapterMusicLibrary.ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new CustomListAdapterMusicLibrary.ViewHolder();
                holder.title = convertView.findViewById(R.id.title);
                holder.composer = convertView.findViewById(R.id.composer);
                holder.instrumentation = convertView.findViewById(R.id.instrumentation);
                holder.parentLayout = convertView.findViewById(R.id.parent_layout);

                convertView.setTag(holder);
            }
            else{
                holder = (CustomListAdapterMusicLibrary.ViewHolder) convertView.getTag();
            }

            holder.title.setText(title);
            holder.composer.setText(composer);
            holder.instrumentation.setText(instrumentation);

            System.out.println("holder.title: " +  title);
            System.out.println("holder.composer: " + composer);
            System.out.println("holder.instrumentation: " + instrumentation);
            System.out.println("***************** After else ********************");

            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }
    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                //.cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

}


