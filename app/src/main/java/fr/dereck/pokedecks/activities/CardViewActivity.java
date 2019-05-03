package fr.dereck.pokedecks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.Utils.AppUtil;
import fr.dereck.pokedecks.adapters.AttackAdapter;
import fr.dereck.pokedecks.adapters.ResistanceAdapter;
import fr.dereck.pokedecks.adapters.WeaknessAdapter;
import fr.dereck.pokedecks.entities.PokemonCard;

public class CardViewActivity extends AppCompatActivity {
    public static final String EXTRA_CARD = "EXTRA_CARD";

    private PokemonCard selectedCard;

    private ImageView pkm_profil;
    private TextView pkm_name;
    private TextView pkm_id;
    private TextView pkm_lp;
    private TextView pkm_type;

    private RecyclerView stats_atk;
    private AttackAdapter attackAdapter;

    private RecyclerView stats_weak;
    private ResistanceAdapter resistanceAdapter;

    private RecyclerView stats_res;
    private WeaknessAdapter weaknessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        this.initComponent();

        final Intent intent = getIntent();
        if (intent != null) {
            selectedCard = (PokemonCard)intent.getSerializableExtra(EXTRA_CARD);
        }

        if (selectedCard != null) {
            this.pkm_id.setText(selectedCard.getId());
            this.pkm_name.setText(selectedCard.getName());
            this.pkm_type.setText(selectedCard.getTypesString());
            this.pkm_lp.setText(selectedCard.getLifepoints());

            String url = AppUtil.NOIMG;
            if (selectedCard.getImageUrl() != null && !selectedCard.getImageUrl().isEmpty()) {
                url = selectedCard.getImageUrl();
            }

            this.pkm_profil.setImageURI(null);
            Picasso.get().load(url).placeholder(R.drawable.pokemon_icon).into(this.pkm_profil);

            this.pkm_profil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(CardViewActivity.this, FullScreenActivity.class);
                    intent1.putExtra(FullScreenActivity.EXTRA_URL, CardViewActivity.this.selectedCard.getImageUrlHiRes());
                    CardViewActivity.this.startActivity(intent1);
                }
            });

            if (this.selectedCard.getAttacks() != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                this.attackAdapter = new AttackAdapter(this.selectedCard.getAttacks());
                this.stats_atk.setAdapter(this.attackAdapter);
                this.stats_atk.setLayoutManager(layoutManager);
                this.attackAdapter.notifyDataSetChanged();
            }

            if (this.selectedCard.getResistances() != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                this.resistanceAdapter = new ResistanceAdapter(this.selectedCard.getResistances());
                this.stats_res.setAdapter(this.resistanceAdapter);
                this.stats_res.setLayoutManager(layoutManager);
                this.resistanceAdapter.notifyDataSetChanged();
            }

            if (this.selectedCard.getWeaknesses() != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                this.weaknessAdapter = new WeaknessAdapter(this.selectedCard.getWeaknesses());
                this.stats_weak.setAdapter(this.weaknessAdapter);
                this.stats_weak.setLayoutManager(layoutManager);
                this.weaknessAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initComponent() {
        this.pkm_profil = this.findViewById(R.id.pkm_profil);
        this.pkm_name = this.findViewById(R.id.pkm_name);
        this.pkm_id = this.findViewById(R.id.pkm_id);
        this.pkm_lp = this.findViewById(R.id.pkm_lp);
        this.pkm_type = this.findViewById(R.id.pkm_type);

        this.stats_atk = this.findViewById(R.id.stats_atk);
        this.stats_weak = this.findViewById(R.id.stats_weak);
        this.stats_res = this.findViewById(R.id.stats_res);
    }

}
