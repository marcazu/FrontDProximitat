package com.example.frontapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.frontapp.R;
import com.example.frontapp.fragments.BotiguesFragment;
import com.example.frontapp.fragments.ComandesFragment;
import com.example.frontapp.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView btm_view;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();


        // parametres de navegació de la botiga
        btm_view = findViewById(R.id.bottomNavView);
        Intent myIntent = getIntent();
        int intentInt = myIntent.getIntExtra("firstKeyName",2);
        // si 1 inicialitzem el fragmetn de comandes
        if(intentInt == 1){
            getFragment(new ComandesFragment());
        }
        else if(intentInt == 3){
            getFragment(new PerfilFragment());
        }
        else{
            // es 2 o null
            getFragment(new BotiguesFragment());
        }
        btm_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //posem el fragemtn que toca segons el boto que pitxem
                if(item.getItemId() == R.id.back2Main)setComandesFragment();
                else if(item.getItemId() == R.id.perfil)setUsuariFragment();
                else setBotiguesFramgment();
                return false;
            }
        });
    }

    //crea el menu de 3 puntets
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu_botiga,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // no funciona si discriminem buttons no se pq
        fAuth.signOut();
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    public void getFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //si posem .add posa el fragmetn un sobre l'altre i no funciona bé
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
    private void setComandesFragment(){
        Toast.makeText(MainActivity.this,"comandes",Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Comandes");
        getFragment(new ComandesFragment());
    }
    private void setUsuariFragment(){
        Toast.makeText(MainActivity.this,"perfil",Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Perfil d'usuari");
        getFragment(new PerfilFragment());
    }
    private void setBotiguesFramgment(){
        Toast.makeText(MainActivity.this,"botigues",Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Botigues");
        getFragment(new BotiguesFragment());

    }
}
