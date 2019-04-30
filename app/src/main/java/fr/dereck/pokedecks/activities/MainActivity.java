package fr.dereck.pokedecks.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    List<PokemonCard> cards = new ArrayList<>();

    private RecyclerView rv_main_list_card;
    private PokemonCardAdapter pokemonCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                if ((visibleCount + firstVisible) == itemsCount) {
                    // TODO : add items with pagination
//                    MainActivity.this.pokemonCardAdapter.addItems(null);
                }
            }
        });
    }

    private void initComponent() {
        this.rv_main_list_card = this.findViewById(R.id.rv_main_list_card);
    }

    private void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtil.APIURL + PokemonCard.API_CARDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<PokemonCard> result = new ArrayList<>();

                        try {
                            result = Arrays.asList(new Gson().fromJson(new JSONObject(response).getString("cards"), PokemonCard[].class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MainActivity.this.pokemonCardAdapter.setItems(result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }
}
