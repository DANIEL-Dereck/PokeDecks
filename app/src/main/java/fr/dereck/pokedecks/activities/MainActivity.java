package fr.dereck.pokedecks.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;

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
    public static final String TAG = "MainActivity";

    private List<PokemonCard> cards = new ArrayList<>();
    private int pageNumber = 1;
    private Merlin merlin;

    private RecyclerView rv_main_list_card;
    private PokemonCardAdapter pokemonCardAdapter;
    private boolean onSearchInList = false;
    private boolean inRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.initComponent();

        this.merlin = new Merlin.Builder().withAllCallbacks().build(this);
        this.merlin.registerDisconnectable(new Disconnectable() {
            @Override
            public void onDisconnect() {
                String message = "Application deconnecter";
                final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) MainActivity.this.findViewById(android.R.id.content)).getChildAt(0);
                Snackbar.make(viewGroup,message,Snackbar.LENGTH_SHORT).show();
        }});

        this.merlin.registerConnectable(new Connectable() {
            @Override
            public void onConnect() {
                String message = "Application reconnecter";
                final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) MainActivity.this.findViewById(android.R.id.content)).getChildAt(0);
                Snackbar.make(viewGroup, message, Snackbar.LENGTH_SHORT).show();
            }
        });

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

                if ((visibleCount + firstVisible) == itemsCount && !MainActivity.this.onSearchInList && !MainActivity.this.inRequest) {
                    Log.d(TAG,String.format(" VisibleCount = %d | FirstVisible = %d | ItemsCount = %d", visibleCount, firstVisible, itemsCount));
                    MainActivity.this.inRequest = true;

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, (AppUtil.APIURL + PokemonCard.API_CARDS + "?page=" + MainActivity.this.pageNumber),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    MainActivity.this.pageNumber += 1;
                                    Log.d(TAG, "page " + MainActivity.this.pageNumber + "added");

                                    try {
                                        MainActivity.this.cards.addAll(new ArrayList<>(Arrays.asList(new Gson().fromJson(new JSONObject(response).getString("cards"), PokemonCard[].class))));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    MainActivity.this.pokemonCardAdapter.setItems(MainActivity.this.cards);
                                    MainActivity.this.inRequest = false;
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MainActivity.this.inRequest = false;
                        }
                    });

                    queue.add(stringRequest);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
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
                MainActivity.this.onSearchInList = false;
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
            MainActivity.this.onSearchInList = true;
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, (AppUtil.APIURL + PokemonCard.API_CARDS + "?name=" + query),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MainActivity.this.pageNumber += 1;
                            Log.d(TAG, "page " + MainActivity.this.pageNumber + "added");
                            List<PokemonCard> selectedCards = new ArrayList<>();

                            try {
                                selectedCards.addAll(new ArrayList<>(Arrays.asList(new Gson().fromJson(new JSONObject(response).getString("cards"), PokemonCard[].class))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MainActivity.this.pokemonCardAdapter.clear();
                            MainActivity.this.pokemonCardAdapter.addItems(selectedCards);
                            MainActivity.this.inRequest = false;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MainActivity.this.inRequest = false;
                }
            });

            queue.add(stringRequest);


            MainActivity.this.pokemonCardAdapter.clear();
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
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }
}
