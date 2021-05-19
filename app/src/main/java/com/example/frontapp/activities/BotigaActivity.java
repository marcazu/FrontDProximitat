package com.example.frontapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontapp.R;
import com.example.frontapp.adapters.BotigesAdapter;
import com.example.frontapp.adapters.ProducteAdapter;
import com.example.frontapp.entities.Botiga;
import com.example.frontapp.entities.Producte;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BotigaActivity extends AppCompatActivity implements ProducteAdapter.OnProducteListener {
    BottomNavigationView btm_view;

    private RecyclerView recyclerView;
    private List<Producte> productes;
    private String url;
    private ProducteAdapter producteAdapter;

    FirebaseAuth fAuth;

    private Botiga botiga;
    private Intent intent;
    private TextView botigaDescription,botigaTelefon, botigaEmail;
    private ImageView botigaImage;

    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botiga);

        picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);

        //obtenim la botiga
        intent = getIntent();
        botiga = (Botiga)intent.getSerializableExtra("botiga");
        botigaDescription = findViewById(R.id.botigaBigDescription);
        botigaTelefon = findViewById(R.id.botigaTelefon);
        botigaEmail = findViewById(R.id.botigaEmail);
        botigaImage = findViewById(R.id.botigaImage);

        getSupportActionBar().setTitle(botiga.getNom());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        botigaDescription.setText(botiga.getDescripcio());
        botigaEmail.setText(botiga.getEmail());
        botigaTelefon.setText(botiga.getTelefon());
        picasso.get().load(botiga.getMainImageUrl()).placeholder(R.drawable.ic_launcher_background).into(botigaImage);


        fAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.botigaProducteList);
        productes = new ArrayList<>();
        url = "https://backenddproximitat.herokuapp.com/botigues/"+botiga.getId()+"/productes";

        extractProductes(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu_botiga,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // no funciona si discriminem buttons no se pq
        fAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void extractProductes(ProducteAdapter.OnProducteListener onProducteListener) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject producteObject = response.getJSONObject(i);

                                Botiga botiga = new Botiga();
                                Producte producte = new Producte();
                                producte.setId(producteObject.getString("id").toString());
                                producte.setDescripcio(producteObject.getString("descripcio").toString());
                                producte.setNom(producteObject.getString("nom").toString());
                                producte.setPreu(producteObject.getString("preu").toString());
                                producte.setTipus(producteObject.getString("tipus").toString());
                                producte.setIconaUrl(producteObject.getString("iconaProducte").toString());
                                //afegim el producte a la llista
                                productes.add(producte);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // fem un grid layout amb 2 columnes
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                        producteAdapter = new ProducteAdapter(getApplicationContext(), productes,onProducteListener);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
                        recyclerView.setAdapter(producteAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","on Error response: "+error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onProducteClick(int position) {

    }
}