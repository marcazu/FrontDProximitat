package com.example.frontapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.frontapp.activities.BotigaActivity;
import com.example.frontapp.activities.BotiguesActivity;
import com.example.frontapp.R;
import com.example.frontapp.adapters.BotigesAdapter;
import com.example.frontapp.entities.Botiga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BotiguesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BotiguesFragment extends Fragment implements BotigesAdapter.OnBotigaListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<Botiga> botigues;
    private String url = "https://backenddproximitat.herokuapp.com/botigues";
    private BotigesAdapter adapter;
    private int containerViewID;

    private FragmentActivity myContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BotiguesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BotiguesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BotiguesFragment newInstance(String param1, String param2) {
        BotiguesFragment fragment = new BotiguesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_botigues, container, false);


        recyclerView = vista.findViewById(R.id.botiguesList);
        botigues = new ArrayList<>();

        extractBotigues(this);
        return vista;


    }

    private void extractBotigues(BotigesAdapter.OnBotigaListener onBotigaListener) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                                botiga.setIconUrl(botigaObject.getString("iconUrl").toString());
                                botiga.setMainImageUrl(botigaObject.getString("mainImageUrl").toString());
                                //afegim a la llista la botiga que ens torna el JSON
                                botigues.add(botiga);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // s'ha de fer aquí emplenar el layout no se pq
                        //si vlem fer quadeicules fer un grid layout
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        adapter = new BotigesAdapter(getActivity().getApplicationContext(), botigues,onBotigaListener);
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
        Log.d("posicio", String.valueOf(position));
        Intent intent = new Intent(getActivity(), BotigaActivity.class);
        intent.putExtra("botiga",botigues.get(position));
        startActivity(intent);
        //aqui navagarem a la nova activitat
    }
}