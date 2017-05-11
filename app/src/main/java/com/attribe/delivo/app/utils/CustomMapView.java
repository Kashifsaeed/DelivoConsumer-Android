package com.attribe.delivo.app.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

/**
 * Created by attribe on 9/1/16.
 */
//implements GestureDetector.OnGestureListener
public class CustomMapView extends MapView  {


    private static final String LOG_TAG = "TouchEffect Detect";
    private MapTouchListener listener;
    private GestureDetector gestureDetector;
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;
     private long Downtime;
    private long UpTime;

    public CustomMapView(Context context) {
        super(context);
    }

    public CustomMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CustomMapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context, googleMapOptions);
    }

    public void setMapTouchListener(MapTouchListener listener) {
        this.listener = listener;
    }
    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector  = gestureDetector;
    }





        @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                try {

                    if(event.getPressure()>0.7){
                        listener.OnActionDown();
                    }

                }catch (Exception exc){

                }


                break;

            case MotionEvent.ACTION_UP:
                try {

                    listener.OnActionUp();
                }catch (Exception exc)
                {

                }

                break;


            case MotionEvent.ACTION_MOVE:
                try {

                    listener.OnActionMOve();
                }catch (Exception exc){

                }

                break;

            case MotionEvent.ACTION_SCROLL:

                listener.onScroll();
                break;

        }
        return super.dispatchTouchEvent(event);
    }






//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//       // return super.onTouchEvent(event);
//        return gestureDetector.onTouchEvent(event);
//    }





    public interface MapTouchListener{

        void OnActionDown(float pressure);
        void OnActionDown();
        void OnActionUp();
         void  OnActionMOve();
        void onScroll();





    }

}
