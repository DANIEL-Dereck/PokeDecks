package fr.dereck.pokedecks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.Utils.AppUtil;

public class FullScreenActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "EXTRA_URL";

    private ImageView card_view;
    private ImageView card_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        this.card_view = this.findViewById(R.id.card_view);
        this.card_bg = this.findViewById(R.id.card_bg);

        Intent intent = this.getIntent();
        if (intent != null) {

            String url = AppUtil.NOIMG;

            if (intent.getStringExtra(EXTRA_URL) != null && !intent.getStringExtra(EXTRA_URL).isEmpty()) {
                url = intent.getStringExtra(EXTRA_URL);
            }

            this.card_bg.setImageURI(null);
            Picasso.get().load(url).placeholder(R.drawable.pokemon_icon).into(this.card_bg);

            this.card_view.setImageURI(null);
            Picasso.get().load(url).placeholder(R.drawable.pokemon_icon).into(this.card_view);
        }
    }

}
