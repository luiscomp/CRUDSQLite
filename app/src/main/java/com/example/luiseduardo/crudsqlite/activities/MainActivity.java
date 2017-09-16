package com.example.luiseduardo.crudsqlite.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.adapters.LivrosRecycleViewAdapter;
import com.example.luiseduardo.crudsqlite.dao.LivroDAO;
import com.example.luiseduardo.crudsqlite.dialogs.DialogAdicionarLivro;
import com.example.luiseduardo.crudsqlite.vo.LivroVO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdicionar;
    private RecyclerView rvLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponenetes();
        aplicarListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RecuperarLivrosAsynkTask().execute();
    }

    private void aplicarListeners() {
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdicionarLivro dialog = DialogAdicionarLivro.newInstance(new DialogAdicionarLivro.OnListener() {
                    @Override
                    public void aoConcluir(LivroVO livro) {
                        new GravarLivroAsynkTask().execute(livro);
                    }
                });
                dialog.show(getSupportFragmentManager(), "dialogLivro");
            }
        });
    }

    private void instanciarComponenetes() {
        fabAdicionar = (FloatingActionButton) findViewById(R.id.fabAdicionar);

        rvLivros = (RecyclerView) findViewById(R.id.rvLivros);
        rvLivros.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvLivros.setAdapter(new LivrosRecycleViewAdapter(null, new LivrosRecycleViewAdapter.OnItemClickClistener() {
            @Override
            public void aoRemover(LivroVO livro) {
                new DeletarLivroAsynkTask().execute(livro);
            }

            @Override
            public void onClickItemListener(LivroVO livro) {
                DialogAdicionarLivro dialog = DialogAdicionarLivro.newInstance(livro, new DialogAdicionarLivro.OnListener() {
                    @Override
                    public void aoConcluir(LivroVO livro) {
                        new GravarLivroAsynkTask().execute(livro);
                    }
                });
                dialog.show(getSupportFragmentManager(), "dialogLivro");
            }
        }));
    }

    private class GravarLivroAsynkTask extends AsyncTask<LivroVO, LivroVO, LivroVO> {
        @Override
        protected LivroVO doInBackground(LivroVO... livros) {
            LivroDAO.abrirConexao(MainActivity.this);
            LivroDAO.inserir(livros[0]);
            LivroDAO.fecharConexao();
            return livros[0];
        }

        @Override
        protected void onPostExecute(LivroVO livro) {
            ((LivrosRecycleViewAdapter) rvLivros.getAdapter()).adicionarItem(livro);
        }
    }

    private class RecuperarLivrosAsynkTask extends AsyncTask<Void, Void, ArrayList<LivroVO>> {
        @Override
        protected ArrayList<LivroVO> doInBackground(Void... voids) {
            ArrayList<LivroVO> lista;

            LivroDAO.abrirConexao(MainActivity.this);
            lista = LivroDAO.recuperar(new LivroVO());
            LivroDAO.fecharConexao();

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<LivroVO> lista) {
            ((LivrosRecycleViewAdapter) rvLivros.getAdapter()).setarLista(lista);
        }
    }

    private class DeletarLivroAsynkTask extends AsyncTask<LivroVO, Void, LivroVO>{
        @Override
        protected LivroVO doInBackground(LivroVO... livros) {
            LivroDAO.abrirConexao(MainActivity.this);
            LivroDAO.deletar(livros[0]);
            LivroDAO.fecharConexao();
            return livros[0];
        }

        @Override
        protected void onPostExecute(LivroVO livro) {
            ((LivrosRecycleViewAdapter) rvLivros.getAdapter()).removerItem(livro);
        }
    }
}
