package fr.dereck.pokedecks.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.Utils.AppUtil;
import fr.dereck.pokedecks.adapters.PokemonCardAdapter;
import fr.dereck.pokedecks.entities.PokemonCard;

public class MainActivity extends AppCompatActivity {

    private List<PokemonCard> cards = new ArrayList<>();
    private int pageNumber = 1;

    private RecyclerView rv_main_list_card;
    private PokemonCardAdapter pokemonCardAdapter;
    private boolean onsearchInList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.initComponent();

        this.pokemonCardAdapter = new PokemonCardAdapter(this);
        this.initData();

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        this.rv_main_list_card.setAdapter(pokemonCardAdapter);
        this.rv_main_list_card.setLayoutManager(gridLayoutManager);
        this.rv_main_list_card.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleCount = gridLayoutManager.getChildCount();
                int firstVisible = gridLayoutManager.findFirstVisibleItemPosition();
                int itemsCount = gridLayoutManager.getItemCount();

                if ((visibleCount + firstVisible) == itemsCount && !MainActivity.this.onsearchInList) {
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, (AppUtil.APIURL + PokemonCard.API_CARDS + "?page=" + MainActivity.this.pageNumber),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        MainActivity.this.pageNumber += 1;

                                        MainActivity.this.cards.addAll(new ArrayList<>(Arrays.asList(new Gson().fromJson(new JSONObject(response).getString("cards"), PokemonCard[].class))));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    MainActivity.this.pokemonCardAdapter.setItems(MainActivity.this.cards);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

                    queue.add(stringRequest);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        menu.findItem(R.id.search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                MainActivity.this.onsearchInList = false;
                MainActivity.this.pokemonCardAdapter.setItems(MainActivity.this.cards);
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            MainActivity.this.pokemonCardAdapter.clear();
            MainActivity.this.onsearchInList = true;
            for (PokemonCard card : cards) {
                if (card.getName().toLowerCase().contains(query.toLowerCase())) {
                    MainActivity.this.pokemonCardAdapter.addItem(card);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.pokemonCardAdapter.getItemCount() != this.cards.size()) {
            this.pokemonCardAdapter.setItems(this.cards);
        } else {
            super.onBackPressed();
        }
    }

    private void initComponent() {
        this.rv_main_list_card = this.findViewById(R.id.rv_main_list_card);
    }

    private void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtil.APIURL + PokemonCard.API_CARDS + "?page=" + this.pageNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            MainActivity.this.pageNumber += 1;
                            MainActivity.this.cards.addAll(new ArrayList<>(Arrays.asList(new Gson().fromJson(new JSONObject(response).getString("cards"), PokemonCard[].class))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MainActivity.this.pokemonCardAdapter.setItems(MainActivity.this.cards);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }
}
