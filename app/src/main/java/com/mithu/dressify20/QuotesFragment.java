package com.mithu.dressify20;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuotesFragment extends Fragment {

    private static final String API_BASE_URL = "https://api.api-ninjas.com/v1/quotes?category=";
    private static final String API_KEY = "vUeD196XGm5C7AIKjBBnEw==8NSqcq4Kh9WnHc35";
    private TextView quotesTextView;
    private LinearLayout btnHome, btnDiscovery, btnBlogs, btnProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        quotesTextView = view.findViewById(R.id.quotesTextView);
        

        fetchQuotes("beauty");
        fetchQuotes("business");
        fetchQuotes("design");

        return view;
    }


    private void fetchQuotes(String category) {
        String apiUrl = API_BASE_URL + category;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            StringBuilder quotesBuilder = new StringBuilder();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject quoteObj = response.getJSONObject(i);
                                String quote = quoteObj.optString("quote", "No quote available");
                                String author = quoteObj.optString("author", "Unknown");
                                quotesBuilder.append("\"").append(quote).append("\" - ").append(author).append("\n\n");
                            }
                            quotesTextView.setText(quotesBuilder.toString());
                        } catch (JSONException e) {
                            showError("Error parsing response.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError("Error fetching quotes.");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", API_KEY);
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    private void showError(String message) {
        quotesTextView.setText(message);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
