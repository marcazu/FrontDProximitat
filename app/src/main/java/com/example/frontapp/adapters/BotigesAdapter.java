package com.example.frontapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontapp.R;
import com.example.frontapp.entities.Botiga;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BotigesAdapter extends RecyclerView.Adapter<BotigesAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Botiga> botigues;
    private OnBotigaListener onBotigaListener;

    public BotigesAdapter(Context ctx, List<Botiga> botigues, OnBotigaListener onBotigaListener){
        this.inflater = LayoutInflater.from(ctx);
        this.botigues = botigues;
        this.onBotigaListener = onBotigaListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_botiga_list_layout,parent, false);
        return new ViewHolder(view,onBotigaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Bind the data
        holder.botigaTitle.setText(botigues.get(position).getNom());
        holder.botigaDescripcio.setText(botigues.get(position).getDescripcio());
        // si surt verd hi ha algun error en la foto, no acaba de carregar
        //String url2 = "https://cdn.icon-icons.com/icons2/3007/PNG/128/discord_logo_icon_188422.png";
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        //Picasso.get().load(url2).placeholder(R.drawable.ic_launcher_background).into(holder.botigaImage);
        picasso.get().load((botigues.get(position).getIconUrl())).placeholder(R.drawable.ic_launcher_background).into(holder.botigaImage);


    }

    @Override
    public int getItemCount() {
        return botigues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView botigaTitle, botigaDescripcio;
        ImageView botigaImage;
        OnBotigaListener onBotigaListener;

        public ViewHolder(@NonNull View itemView,OnBotigaListener onBotigaListener) {
            super(itemView);
            botigaTitle = itemView.findViewById(R.id.botigaTitle);
            botigaDescripcio = itemView.findViewById(R.id.botigaDescription);
            botigaImage = (ImageView) itemView.findViewById(R.id.botigaIconImage);
            this.onBotigaListener = onBotigaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBotigaListener.onBotigaClick(getAbsoluteAdapterPosition());

        }
    }

    public interface OnBotigaListener{
        void onBotigaClick(int position);
    }
}
