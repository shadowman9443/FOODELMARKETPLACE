package com.digitechlabs.neat.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dgtech.neat.R;
import com.digitechlabs.neat.entities.DirectionsJSONParser;
import com.digitechlabs.neat.utils.AppConstant;
import com.digitechlabs.neat.utils.CommonTask;
import com.digitechlabs.neat.utils.GPSTracker;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GMAPLineDrawActivity extends HeaderFooterActivity implements LocationListener {

	private GoogleMap googleMap;
	private MapView mapView;
	ArrayList<LatLng> markerPoints;
	LocationManager locationManager;
	Context context;
	Button btnGetDirection;
	double destlat = AppConstant.COMPANY_LAT, Currentlat, Currentlng;
	double destlong = AppConstant.COMPANY_LANG;
	private GPSTracker gpsTracker;
	LatLng currentPosition;
	LatLng RestaurantPosition;
	private boolean mapsSupported = true;
	double distance;

	// 51.7569266,-0.4748163 23.8050 90.3667
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmap_linedraw);
		MapsInitializer.initialize(this);

		/*try {
			
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		mapView = (MapView) findViewById(R.id.mapNearbyLine);
		mapView.onCreate(savedInstanceState);
		
		context = this;

		initializeMap();
		initUI();

		//Log.i("Lat Long Are ", Currentlat + "  Long   " + Currentlng);

	}

	private void initializeMap() {
		if (googleMap == null && mapsSupported) {
			googleMap = ((MapView) findViewById(R.id.mapNearbyLine)).getMap();
			// setup markers etc...

		}
	}

	@SuppressLint("NewApi")
	private void initUI() {
		gpsTracker = new GPSTracker(context);
		markerPoints = new ArrayList<LatLng>();
		Currentlat = gpsTracker.getLatitude();
		Currentlng = gpsTracker.getLongitude();

		currentPosition = new LatLng(Currentlat, Currentlng);
		RestaurantPosition = new LatLng(destlat, destlong);
		distance = CalculationByDistance(currentPosition, RestaurantPosition);
		btnGetDirection = (Button) findViewById(R.id.btnGetDirection);

		googleMap = ((MapView) findViewById(R.id.mapNearbyLine)).getMap();
		// map = ((MapFragment)
		// getFragmentManager().findFragmentById(R.id.mapNearbyLine)).getMap();
		googleMap.setMyLocationEnabled(true);

		drawMarker(currentPosition);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		googleMap.addMarker(new MarkerOptions().position(RestaurantPosition)
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Restaurant"));

	}

	private void drawMarker(LatLng point) {
		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions()
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("You are here ")
				.snippet("Distance Away " + String.format("%.2f", distance) + " KM");
		// Setting latitude and longitude for the marker
		markerOptions.position(point);
		googleMap.addMarker(markerOptions).showInfoWindow();
	}

	public void GetRoute(View view) {
		
		if(distance>1000){
			Toast.makeText(ctx, "Sorry More then 1000 KM is not permitted by google !", Toast.LENGTH_LONG).show();
			
		}else {
			if (markerPoints.size() > 1) {
				googleMap.clear();
			}
			MarkerOptions options = new MarkerOptions();
			if (markerPoints.size() == 1) {
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			} else if (markerPoints.size() == 2) {
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}
			currentPosition = new LatLng(Currentlat, Currentlng);
			googleMap.addMarker(new MarkerOptions().position(RestaurantPosition)
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Restaurant"));
			String url = getDirectionsUrl();
			DownloadTask downloadTask = new DownloadTask();
			downloadTask.execute(url);	
		}
		
	}



	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
		gpsTracker = new GPSTracker(context);
		if (!CommonTask.isOnline(this)) {
			Toast.makeText(GMAPLineDrawActivity.this, "Your internet connection dry!!!, Please connect...",
					Toast.LENGTH_LONG).show();

		}
	}

	public double CalculationByDistance(LatLng StartP, LatLng EndP) {
		int Radius = 6371;// radius of earth in Km
		double lat1 = StartP.latitude;
		double lat2 = EndP.latitude;
		double lon1 = StartP.longitude;
		double lon2 = EndP.longitude;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double valueResult = Radius * c;
		double km = valueResult / 1;
		DecimalFormat newFormat = new DecimalFormat("####");
		int kmInDec = Integer.valueOf(newFormat.format(km));
		double meter = valueResult % 1000;
		int meterInDec = Integer.valueOf(newFormat.format(meter));
		Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);

		return Radius * c;
	}

	private String getDirectionsUrl() {
		String str_origin = "origin=" + Currentlat + "," + Currentlng;
		String str_dest = "destination=" + destlat + "," + destlong;
		// Sensor enabled
		String sensor = "sensor=false";
		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;
		// Output format
		String output = "json";
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();
			// Connecting to url
			urlConnection.connect();
			// Reading data from url
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
			// For storing data from web service
			String data = "";
			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ParserTask parserTask = new ParserTask();
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;
			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(10);
				lineOptions.color(Color.RED);
				lineOptions.describeContents();

			}

			// Drawing polyline in the Google Map for the i-th route
			googleMap.addPolyline(lineOptions);
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
}
