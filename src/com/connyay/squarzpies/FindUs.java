package com.connyay.squarzpies;


import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class FindUs extends MapActivity {

	private MapView mapView;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.findus); // bind the layout to the activity

		mapView = (MapView) findViewById(R.id.mapview1);
		mapView.setBuiltInZoomControls(true);
		
		MapController mc = mapView.getController();
		mc.setCenter(new GeoPoint(33535671,-111991882));
		mc.setZoom(11);


		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.reddot2);
		MarketLocations itemizedoverlay = new MarketLocations(drawable,
				FindUs.this);

		GeoPoint point1 = new GeoPoint(33552113, -112074206);
		OverlayItem overlayitem1 = new OverlayItem(point1,
				"Central Farmers Market", "Saturdays 9am-2pm \n"
						+ "7901 N Central Ave \n" + "Phoenix, AZ 85020");

		itemizedoverlay.addOverlay(overlayitem1);
		mapOverlays.add(itemizedoverlay);

		GeoPoint point2 = new GeoPoint(33492224, -111924544);
		OverlayItem overlayitem2 = new OverlayItem(point2,
				"Old Town Scottsdale Farmers Market", "Saturdays 8am-1pm \n"
						+ "E 1st St and N Brown Ave \n" + "Scottsdale, AZ 85251");

		itemizedoverlay.addOverlay(overlayitem2);
		mapOverlays.add(itemizedoverlay);

		GeoPoint point3 = new GeoPoint(33303338, -111841374);
		OverlayItem overlayitem3 = new OverlayItem(point3,
				"Chandler Farmers Market", "Thursdays 3-7pm \n"
						+ "3 South Arizona Ave \n" + "Chandler, AZ 85225");

		itemizedoverlay.addOverlay(overlayitem3);
		mapOverlays.add(itemizedoverlay);

		GeoPoint point4 = new GeoPoint(33353972, -111790993);
		OverlayItem overlayitem4 = new OverlayItem(point4,
				"Gilbert Farmers Market", "Saturdays 9am-1pm \n"
						+ "222 N Ash St \n" + "Gilbert, AZ 85234 \n"
						+ "Next to the Water Tower in Downtown Gilbert");

		itemizedoverlay.addOverlay(overlayitem4);
		mapOverlays.add(itemizedoverlay);
		
		GeoPoint point5 = new GeoPoint(33415423, -111740037);
		OverlayItem overlayitem5 = new OverlayItem(point5,
				"Squarz Kitchen", "Mon through Wed 3-7pm \n"
						+ "4016 E Main St \n" + "Mesa, AZ 85205 \n"
						+ "Look for the \"Catering\" shop in Perri Plaza");

		itemizedoverlay.addOverlay(overlayitem5);
		mapOverlays.add(itemizedoverlay);
		
		GeoPoint point6 = new GeoPoint(33596656, -112004697);
		OverlayItem overlayitem6 = new OverlayItem(point6,
				"Roadrunner Park Farmers Market", "Saturdays 8am-1pm \n"
						+ "3502 E Cactus Rd \n" + "Phoenix, AZ 85032");

		itemizedoverlay.addOverlay(overlayitem6);
		mapOverlays.add(itemizedoverlay);
		
		GeoPoint point7 = new GeoPoint(33674923, -111964948);
		OverlayItem overlayitem7 = new OverlayItem(point7,
				"City North Farmers Market", "3rd Sunday of the Month 10am-2pm \n"
						+ "5415 High Street\n" + "Phoenix, AZ 85054");

		itemizedoverlay.addOverlay(overlayitem7);
		mapOverlays.add(itemizedoverlay);
		
		GeoPoint point8 = new GeoPoint(33658197, -112265596);
		OverlayItem overlayitem8 = new OverlayItem(point8,
				"Peoria (Westbrook Village) Farmers Market", "3rd Sunday of the Month 10am-2pm \n"
						+ "19251 N Wesbrook Parkway\n" + "Peoria, AZ 85382");

		itemizedoverlay.addOverlay(overlayitem8);
		mapOverlays.add(itemizedoverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
