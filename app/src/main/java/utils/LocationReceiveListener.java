package utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sabih Ahmed on 12-Jul-16.
 */
public interface LocationReceiveListener {

    void onLocationReceive(LatLng geocodes);

}
