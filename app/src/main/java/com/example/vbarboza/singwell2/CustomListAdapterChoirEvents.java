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
 * Created by evaramirez on 3/26/18.
 */

public class CustomListAdapterChoirEvents extends ArrayAdapter<Card> {

    private static final String TAG = "CustomLAdaptChoirEvents";

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView date;
        TextView time;
        TextView location;
        ProgressBar dialog;
        RelativeLayout parentLayout;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CustomListAdapterChoirEvents(Context context, int resource, ArrayList<Card> objects) {
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
        final String name = getItem(position).getName();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        String location = getItem(position).getLocation();
        String imgUrl = getItem(position).getImgURL();

//        System.out.println("***************** Inside getView() ********************");
        try{
//            System.out.println("***************** Inside try ********************");

            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            final CustomListAdapterChoirEvents.ViewHolder holder;

            if(convertView == null){
//                System.out.println("***************** Inside if() ********************");

                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new CustomListAdapterChoirEvents.ViewHolder();
                holder.name = convertView.findViewById(R.id.eventName);
                holder.image = convertView.findViewById(R.id.calendarImage);
                //holder.dialog = convertView.findViewById(R.id.progress_Dialog);
                holder.time = convertView.findViewById(R.id.eventTime);
                holder.date = convertView.findViewById(R.id.eventDate);
                holder.location = convertView.findViewById(R.id.eventLocation);
                holder.parentLayout = convertView.findViewById(R.id.parent_layout);

                convertView.setTag(holder);
            }
            else{
//                System.out.println("***************** Inside else ********************");

                holder = (CustomListAdapterChoirEvents.ViewHolder) convertView.getTag();
            }

            holder.name.setText(name);
            holder.time.setText(time);
            holder.date.setText(date);
            holder.location.setText(location);

//            System.out.println("holder.name: " +  name);
//            System.out.println("holder.time: " +  time);
//            System.out.println("holder.date: " +  date);
//            System.out.println("holder.location: " +  location);
//            System.out.println("***************** After else ********************");

            //create the imageloader object
            ImageLoader imageLoader = ImageLoader.getInstance();

           // System.out.println("**************** Instantiating imageLoader ********************");

            int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

//            System.out.println("*************** default image *****************");

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    //.cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showImageOnLoading(defaultImage).build();

           // System.out.println("******************** display option created ******************");


            //download and display image from url
            imageLoader.displayImage(imgUrl, holder.image, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
//                    holder.dialog.setVisibility(View.VISIBLE);
//                    System.out.println("********************* onLoadingStarted ******************");

                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
 //                   holder.dialog.setVisibility(View.GONE);
//                    System.out.println("********************* onLoadingFailed ******************");

                }
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
 //                   holder.dialog.setVisibility(View.GONE);
//                    System.out.println("********************* onLoadingComplete ******************");

                }
                @Override
                public void onLoadingCancelled(String imageUri, View view) {
//                    System.out.println("********************* onLoadingCancelled ******************");

                }
            });

//            System.out.println("***************** returning image view ***************");

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
