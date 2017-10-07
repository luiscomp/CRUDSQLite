package com.example.luiseduardo.crudsqlite.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.vo.CarroVO;

import java.util.ArrayList;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class CarrosRecycleViewAdapter extends RecyclerView.Adapter<CarrosRecycleViewAdapter.ViewHolder> {
    private OnItemClickClistener listener;
    private ArrayList<CarroVO> lista;

    public interface OnItemClickClistener {
        void aoRemover(CarroVO livro);
        void onClickItemListener(CarroVO livro);
    }

    public CarrosRecycleViewAdapter(ArrayList<CarroVO> lista, OnItemClickClistener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void setarLista(ArrayList<CarroVO> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    public void adicionarItem(CarroVO livro) {
        if(lista == null) {
            lista = new ArrayList<>();
        }
        boolean atualizado = false;
        for(int i = 0; i < lista.size(); i++) {
            if(livro.getId() == lista.get(i).getId()) {
                lista.set(i, livro);
                notifyItemChanged(lista.indexOf(livro));
                atualizado = true;
                break;
            }
        }
        if(!atualizado) {
            lista.add(livro);
            notifyItemInserted(lista.indexOf(livro));
        }
    }

    public void removerItem(CarroVO livro) {
        lista.remove(livro);
        notifyDataSetChanged();
    }

    @Override
    public CarrosRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_carros, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CarrosRecycleViewAdapter.ViewHolder holder, final int position) {
        holder.txtModelo.setText(lista.get(position).getModelo());
        holder.txtMarca.setText(lista.get(position).getMarca());
        holder.txtAno.setText(lista.get(position).getAno().toString());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.aoRemover(lista.get(position));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemListener(lista.get(position));
            }
        });

        holder.itemView.setBackgroundColor(lista.get(position).getCor());
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtModelo;
        private final TextView txtMarca;
        private final TextView txtAno;
        private final ImageView btnDeletar;

        public ViewHolder(View itemView) {
            super(itemView);

            txtModelo = itemView.findViewById(R.id.txtModelo);
            txtMarca = itemView.findViewById(R.id.txtMarca);
            txtAno = itemView.findViewById(R.id.txtAno);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }
}
