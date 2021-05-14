package com.example.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sandwichclub.model.Sandwich;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = com.example.sandwichclub.utils.JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.get()
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        TextView mainNameTV = findViewById(R.id.main_name_tv);
        String mainName = mSandwich.getMainName();
        mainNameTV.setText(mainName);

        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() == 0){
            alsoKnownAsTV.setText(R.string.no_info);
        }else{
            String knownAs = null;
            for(int i=0; i<alsoKnownAsList.size();i++){
                knownAs = alsoKnownAsList.get(i);

                if(i == alsoKnownAsList.size() -1){
                    alsoKnownAsTV.append(knownAs + ".");
                }else{
                    alsoKnownAsTV.append(knownAs + ", ");

                }
            }
        }

        TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        String placeOfOrigin = mSandwich.getPlaceOfOrigin();
        if(placeOfOrigin.isEmpty()){
            placeOfOriginTV.setText(R.string.no_info);
        }else {
            placeOfOriginTV.setText(placeOfOrigin);
        }

        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = mSandwich.getIngredients();
        if (ingredientsList.size() == 0){
            ingredientsTV.setText(R.string.no_info);
        }else{
            String ingredient = null;
            for(int i=0; i<ingredientsList.size();i++){
                ingredient = ingredientsList.get(i);

                if(i == ingredientsList.size() -1){
                    ingredientsTV.append(ingredient + ".");
                }else{
                    ingredientsTV.append(ingredient + ", ");

                }
            }
        }


        TextView descriptionTV = findViewById(R.id.description_tv);
        String description = mSandwich.getDescription();
        if(description.isEmpty()){
            descriptionTV.setText(R.string.no_info);
        }else{
            descriptionTV.setText(description);
        }

    }
}
