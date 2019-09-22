package com.example.hack123;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    List<Address> addressList=null;
    HashMap<String,LatLng> locationHashMap;

    LocationManager locationManager;
    LocationListener locationListener;
    double curLatitude, curLongitude;
    Button currentLocationButton;

    Location locationCurrent;

    private FusedLocationProviderClient client;
    HashMap<String,LatLng> latLngHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }


        latLngHashMap=new HashMap<>(1);
        latLngHashMap.put("Hublie Hall",new LatLng(29.9465012,76.8131874));
        latLngHashMap.put("LHC",new LatLng(29.9449505,76.8146038));
        latLngHashMap.put("SAE",new LatLng(29.9447785,76.8155989));
        latLngHashMap.put("SAC",new LatLng(29.9452353,76.815877));
        latLngHashMap.put("Senate",new LatLng(29.9483053,76.8146328));
        latLngHashMap.put("Academic",new LatLng(29.9462932,76.8144913));

        /*searchView=(SearchView)findViewById(R.id.search_searchview);
        mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map_fragment);
        locationHashMap=new HashMap<>();
        currentLocationButton=(Button)findViewById(R.id.current_location_button);

        client= LocationServices.getFusedLocationProviderClient(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location=searchView.getQuery().toString();
                if(location!=null || !location.equals("")){
                    Geocoder geocoder=new Geocoder(MapsActivity.this);
                    try{
                        addressList=geocoder.getFromLocationName(location,1);
                        Address address=addressList.get(0);
                        LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                        LatLng currentLatLng=new LatLng(curLatitude,curLongitude);

                        Polyline line =map.addPolyline(new PolylineOptions()
                                .add(currentLatLng,latLng)
                                .width(5)
                                .color(Color.RED));

                    }catch (Exception e){
                        Toast.makeText(MapsActivity.this, "Locaton Not Found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                client.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){
                            updateCurrentLocation(location.getLatitude(),location.getLongitude(),"Current Location","Current Location");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    updateCurrentLocation(location.getLatitude(), location.getLongitude(), "Current Location", "Current Position");
                    //locationManager.removeUpdates(this);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };

        if (Build.VERSION.SDK_INT < 23) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationCurrent = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationCurrent != null) {
                    updateCurrentLocation(locationCurrent.getLatitude(), locationCurrent.getLongitude(), "Current Location", "Current Postion");
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationCurrent = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationCurrent != null) {
                    updateCurrentLocation(locationCurrent.getLatitude(), locationCurrent.getLongitude(), "Current Location", "Current Position");
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                try{
                    Toast.makeText(MapsActivity.this, locationCurrent.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MapsActivity.this, "Not initialied", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }

    public void updateCurrentLocation(double lat, double lng, String name, String address) {
        LatLng place = new LatLng(lat, lng);
        curLatitude=lat;
        curLongitude=lng;
        Marker x = map.addMarker(new MarkerOptions()
                .position(place).title(name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .snippet(address));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationCurrent= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationCurrent != null) {
                    updateCurrentLocation(locationCurrent.getLatitude(), locationCurrent.getLongitude(), "Current Location", "Current Position");
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
