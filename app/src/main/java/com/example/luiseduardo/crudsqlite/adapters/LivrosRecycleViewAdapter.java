package com.example.luiseduardo.crudsqlite.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.vo.LivroVO;

import java.util.ArrayList;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class LivrosRecycleViewAdapter extends RecyclerView.Adapter<LivrosRecycleViewAdapter.ViewHolder> {
    private OnItemClickClistener listener;
    private ArrayList<LivroVO> lista;

    public interface OnItemClickClistener {
        void aoRemover(LivroVO livro);
        void onClickItemListener(LivroVO livro);
    }

    public LivrosRecycleViewAdapter(ArrayList<LivroVO> lista, OnItemClickClistener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void setarLista(ArrayList<LivroVO> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    public void adicionarItem(LivroVO livro) {
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

    public void removerItem(LivroVO livro) {
        lista.remove(livro);
        notifyDataSetChanged();
    }

    @Override
    public LivrosRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_livros, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LivrosRecycleViewAdapter.ViewHolder holder, final int position) {
        holder.txtTitulo.setText(lista.get(position).getTitulo());
        holder.txtAutor.setText(lista.get(position).getAutor());
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
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitulo;
        private final TextView txtAutor;
        private final TextView txtAno;
        private final ImageView btnDeletar;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAutor = itemView.findViewById(R.id.txtAutor);
            txtAno = itemView.findViewById(R.id.txtAno);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }
}
