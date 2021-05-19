package com.example.frontapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontapp.R;
import com.example.frontapp.adapters.BotigesAdapter;
import com.example.frontapp.entities.Botiga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BotiguesActivity extends AppCompatActivity implements BotigesAdapter.OnBotigaListener {

    // Es podrà borrar ja veuàs
    private RecyclerView recyclerView;
    private List<Botiga> botigues;
    private String url = "https://backenddproximitat.herokuapp.com/botigues";
    private BotigesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.botigues_fragment);

        recyclerView = findViewById(R.id.botiguesList);
        botigues = new ArrayList<>();

        extractBotigues(this);



    }

    private void extractBotigues(BotigesAdapter.OnBotigaListener onBotigaListener) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject botigaObject = response.getJSONObject(i);

                                Botiga botiga = new Botiga();
                                botiga.setId(botigaObject.getString("id").toString());
                                botiga.setNom(botigaObject.getString("nom").toString());
                                botiga.setDescripcio(botigaObject.getString("descripcio").toString());
                                botiga.setEmail(botigaObject.getString("email").toString());
                                botiga.setTelefon(botigaObject.getString("telefon").toString());
                                botiga.setLongitud(botigaObject.getString("longitud").toString());
                                botiga.setLatitud(botigaObject.getString("latitud").toString());
                                //afegim a la llista la botiga que ens torna el JSON
                                botigues.add(botiga);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // s'ha de fer aquí emplenar el layout no se pq
                        //si vlem fer quadeicules fer un grid layout
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new BotigesAdapter(getApplicationContext(), botigues,onBotigaListener);
                        recyclerView.setAdapter(adapter);
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
    public void onBotigaClick(int position) {
        //aixis pillem la botiga que estem provant
        botigues.get(position);
        Log.d("botiga", String.valueOf(position));
        //aqui navagarem a la nova activitat
    }
}