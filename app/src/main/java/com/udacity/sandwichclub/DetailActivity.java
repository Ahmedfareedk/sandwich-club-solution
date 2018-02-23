package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private Sandwich sandwich;


    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTV;

    @BindView(R.id.place_of_origin_tv)
    TextView placeOfOriginTV;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTV;

    @BindView(R.id.description_tv)
    TextView descriptionTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwiches == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTV.setText(R.string.empty_data);
        } else {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTV.setText(R.string.empty_data);
        } else {
            alsoKnownAsTV.setText(getListedData(sandwich.getAlsoKnownAs()));
        }


        if (sandwich.getDescription().isEmpty()) {

            descriptionTV.setText(R.string.empty_data);
        } else {
            descriptionTV.setText(sandwich.getDescription());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTV.setText(R.string.empty_data);
        } else {
            ingredientsTV.setText(getListedData(sandwich.getIngredients()));
        }
    }


    private StringBuilder getListedData(List<String> list) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append("\n");
        }

        return stringBuilder;
    }
}
