package com.mithu.dressify20;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherDetails extends AppCompatActivity implements ProductAdapter.OnProductClickListener{

    private static final String API_KEY = "542a37733f18e1ddc2a0c46956f55cf7";
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView locationTextView, seasonTextView, temperatureTextView, weatherConditionTextView, suggestionTextView;

    private ProductAdapter productAdapter;
    private RecyclerView productsRecyclerView;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        String cat = getCurrentSeason();
        //String title = getIntent().getStringExtra("title");


        // Initialize RecyclerView
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Adapter
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        productsRecyclerView.setAdapter(productAdapter);

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference().child("products");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Product product = childSnapshot.getValue(Product.class);

                    if (product != null && cat.equals(product.getCategory())) {
                        productList.add(product);
                    }
                }
                productAdapter.updateProducts(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(WeatherDetails.this,
                        "Failed to load products: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        // Initialize views
        TextView recom = findViewById(R.id.recommend);
        locationTextView = findViewById(R.id.locationTextView);
        seasonTextView = findViewById(R.id.seasonTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        weatherConditionTextView = findViewById(R.id.weatherConditionTextView);
        suggestionTextView = findViewById(R.id.suggestionTextView);

        recom.setText(getCurrentSeason() + " Season Recommendation");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                fetchWeatherDetails(location);
            } else {
                locationTextView.setText("Unable to fetch location.");
            }
        });
    }

    private void fetchWeatherDetails(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Fetch location name
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                locationTextView.setText("Location: " + addresses.get(0).getLocality());
            }
        } catch (IOException e) {
            locationTextView.setText("Error fetching location name.");
        }

        // Build Weather API URL
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&units=metric";

        // Fetch Weather Data
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Extract Weather Data
                        JSONObject main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        String weatherCondition = response.getJSONArray("weather").getJSONObject(0).getString("description");

                        temperatureTextView.setText("Temperature: " + temp + "°C");
                        weatherConditionTextView.setText("Weather Condition: " + weatherCondition);

                        // Determine Season
                        String season = getCurrentSeason();
                        seasonTextView.setText("Season: " + season);

                        // Display Clothing Suggestions
                        String suggestion = generateClothingSuggestions(temp, weatherCondition);
                        suggestionTextView.setText("Suggestions: " + suggestion);

                    } catch (Exception e) {
                        suggestionTextView.setText("Error: " + e.getMessage());
                    }
                },
                error -> suggestionTextView.setText("Error fetching weather data."));

        queue.add(request);
    }

    private String getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        if (month >= 2 && month <= 4) return "Spring";
        else if (month >= 5 && month <= 7) return "Summer";
        else if (month >= 8 && month <= 10) return "Sutumn";
        else return "Winter";
    }

    private String generateClothingSuggestions(double temp, String weatherCondition) {
        if (weatherCondition.contains("rain")) {
            return "Rainy day! Try raincoats and waterproof shoes.";
        } else if (temp > 25) {
            return "Sunny day! Light cotton dresses and sunglasses are great.";
        } else if (temp < 15) {
            return "Cold weather! Wear jackets and scarves.";
        } else {
            return "Mild weather! Hoodies and casual wear are perfect.";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchWeatherData();
        } else {
            locationTextView.setText("Location permission denied.");
        }
    }
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("product", product); // Pass the Product object
        startActivity(intent);
    }
}
