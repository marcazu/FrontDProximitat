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
import com.example.frontapp.entities.Producte;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProducteAdapter extends RecyclerView.Adapter<ProducteAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Producte> productes;
    private OnProducteListener onProducteListener;

    public ProducteAdapter(Context ctx, List<Producte> productes, OnProducteListener onProducteListener){
        this.inflater = LayoutInflater.from(ctx);
        this.productes = productes;
        this.onProducteListener = onProducteListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_producte_list_layout,parent, false);
        return new ViewHolder(view,onProducteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        holder.producteNom.setText(productes.get(position).getNom());
        holder.producteDescricpio.setText(productes.get(position).getDescripcio());
        holder.productePreu.setText(productes.get(position).getPreu());


        picasso.get().load((productes.get(position).getIconaUrl())).placeholder(R.drawable.ic_launcher_background).into(holder.producteImage);



    }

    @Override
    public int getItemCount() {
        return productes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView producteNom, producteDescricpio, productePreu;
        ImageView producteImage;
        OnProducteListener onProducteListener;

        public ViewHolder(@NonNull View itemView,OnProducteListener onProducteListener) {
            super(itemView);
            producteNom = itemView.findViewById(R.id.producteName);
            producteDescricpio = itemView.findViewById(R.id.producteDescripcio);
            productePreu = itemView.findViewById(R.id.productePreu);
            producteImage = itemView.findViewById(R.id.producteImage);
            this.onProducteListener = onProducteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProducteListener.onProducteClick(getAbsoluteAdapterPosition());

        }
    }

    public interface OnProducteListener{
        void onProducteClick(int position);
    }
}
