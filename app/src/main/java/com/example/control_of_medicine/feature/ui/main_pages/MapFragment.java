package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.control_of_medicine.Keys;
import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentMapBinding;
import com.example.control_of_medicine.feature.presentation.MapViewModel;
import com.example.control_of_medicine.feature.ui.main.SignFragment;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.map.VisibleRegionUtils;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

public class MapFragment extends Fragment implements Session.SearchListener, CameraListener{

    private Keys keys;
    private final String MAPKIT_API_KEY = Keys.getMAPKIT_API_KEY();
    private FragmentMapBinding binding;
    private MapViewModel mViewModel;

    private UserLocationLayer userLocationLayer;

    private SearchManager searchManager;
    private Session searchSession;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private void submitQuery(String query){
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(binding.mapview.getMapWindow().getMap().getVisibleRegion()),
                new SearchOptions(),
                this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(requireActivity().getApplicationContext());

        requestLocationPermission();
        if(ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            Toast.makeText(requireActivity().getApplicationContext(), "Ошибка геопозиции, разрешите геопозицию", Toast.LENGTH_SHORT).show();
            setFragment();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(getLayoutInflater());

        requestLocationPermission();

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(binding.mapview.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        submitQuery("pharmacy");

        Point point = getLocation();
        binding.mapview.getMapWindow().getMap().move(new CameraPosition(point, 16f, 0f, 0f),
                new Animation(Animation.Type.SMOOTH, 10f), null);

        submitQuery("pharmacy");

        return binding.getRoot();
    }

    private void requestLocationPermission(){
        if(ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }

    private Point getLocation(){
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null){
            double userLat = lastKnownLocation.getLatitude();
            double userLong = lastKnownLocation.getLongitude();
            return new Point(userLat, userLong);
        }
        else{
            Toast.makeText(requireActivity().getApplicationContext(), "Ошибка геопозиции", Toast.LENGTH_SHORT).show();
        }
        return new Point(55.753930, 37.620738);
    }

    private void setFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_pages,
                HomeFragment.newInstance(), null).addToBackStack("Go back").commit();
    }

    @Override
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        binding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void onSearchResponse(@NonNull Response response) {
        MapObjectCollection mapObjects = binding.mapview.getMapWindow().getMap().getMapObjects();
        mapObjects.clear();
        final ImageProvider searchResultImageProvider = ImageProvider
                .fromResource(requireActivity().getApplicationContext(), R.drawable.search_result);
        for (GeoObjectCollection.Item searchResult : response.getCollection().getChildren()) {
            final Point resultLocation = searchResult.getObj().getGeometry().get(0).getPoint();
            if (resultLocation != null) {
                mapObjects.addPlacemark(placemark -> {
                    placemark.setGeometry(resultLocation);
                    placemark.setIcon(searchResultImageProvider);
                });
            }
        }
    }

    @Override
    public void onSearchError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(requireActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPositionChanged(
            Map map,
            CameraPosition cameraPosition,
            CameraUpdateReason cameraUpdateReason,
            boolean finished) {
            if (finished) {
                submitQuery("pharmacy");
            }
    }
}