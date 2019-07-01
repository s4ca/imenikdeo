package com.example.imenikapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imenikapp.R;
import com.example.imenikapp.db.model.Kontakt;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterKontakt extends RecyclerView.Adapter<AdapterKontakt.MyViewHolder> {
    private List<Kontakt> listaKontakata;
    public OnRVClickListener listener;

    public interface OnRVClickListener{
        void onRVClickListener(Kontakt kontakt);
    }
    public AdapterKontakt(List<Kontakt> listaKontakata, OnRVClickListener listener) {
        this.listaKontakata = listaKontakata;
        this.listener = listener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ime,prezime;
        ImageView slika;
        View view;

        public MyViewHolder( View itemView) {
            super(itemView);
            view = itemView;
            ime= itemView.findViewById(R.id.kontakt_ime_recycler);
            prezime = itemView.findViewById(R.id.kontakt_prezime_recycler);
            slika=itemView.findViewById(R.id.kontakt_image);


        }
        public void bind(final Kontakt kontakt, final OnRVClickListener listener){
            ime.setText(kontakt.getIme());
            prezime.setText(kontakt.getPrezime());

            Picasso.get()
                    .load("http://i.imgur.com/DvpvklR.png")
                    .fit()
                    .centerInside()
                    .into(slika);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRVClickListener(kontakt);
                }
            });
        }
    }
    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.main_list,viewGroup,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.bind(listaKontakata.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return listaKontakata.size();
    }

    public void setNewData(List<Kontakt> listaKontakata){
        this.listaKontakata.clear();
        this.listaKontakata.addAll(listaKontakata);
        notifyDataSetChanged();
    }

}

