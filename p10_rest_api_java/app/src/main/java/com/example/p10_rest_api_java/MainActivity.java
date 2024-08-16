package com.example.p10_rest_api_java;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private String nextUrl;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(this, pokemonList);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadMorePokemon();
                }
            }
        });

        fetchPokemonList("https://pokeapi.co/api/v2/pokemon?limit=20");
    }

    private void fetchPokemonList(String url) {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nextUrl = response.getString("next");
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject pokemonObject = results.getJSONObject(i);
                                String pokemonUrl = pokemonObject.getString("url");
                                fetchPokemonDetails(pokemonUrl);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchPokemonDetails(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String imageUrl = response.getJSONObject("sprites").getString("front_default");

                            List<String> types = new ArrayList<>();
                            JSONArray typesArray = response.getJSONArray("types");
                            for (int i = 0; i < typesArray.length(); i++) {
                                types.add(typesArray.getJSONObject(i).getJSONObject("type").getString("name"));
                            }

                            List<String> abilities = new ArrayList<>();
                            JSONArray abilitiesArray = response.getJSONArray("abilities");
                            for (int i = 0; i < abilitiesArray.length(); i++) {
                                abilities.add(abilitiesArray.getJSONObject(i).getJSONObject("ability").getString("name"));
                            }

                            Pokemon pokemon = new Pokemon(name, imageUrl, types, abilities);
                            pokemonList.add(pokemon);
                            adapter.notifyItemInserted(pokemonList.size() - 1);

                            if (pokemonList.size() % 20 == 0) {
                                isLoading = false;
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void loadMorePokemon() {
        if (nextUrl != null && !nextUrl.isEmpty()) {
            fetchPokemonList(nextUrl);
        }
    }
}
