package com.example.luiseduardo.crudsqlite.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.adapters.CarrosRecycleViewAdapter;
import com.example.luiseduardo.crudsqlite.dao.CarroDAO;
import com.example.luiseduardo.crudsqlite.dialogs.DialogCadastroCarro;
import com.example.luiseduardo.crudsqlite.vo.CarroVO;

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
                DialogCadastroCarro dialog = DialogCadastroCarro.newInstance(new DialogCadastroCarro.OnListener() {
                    @Override
                    public void aoConcluir(CarroVO livro) {
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
        rvLivros.setAdapter(new CarrosRecycleViewAdapter(null, new CarrosRecycleViewAdapter.OnItemClickClistener() {
            @Override
            public void aoRemover(CarroVO livro) {
                new DeletarLivroAsynkTask().execute(livro);
            }

            @Override
            public void onClickItemListener(CarroVO livro) {
                DialogCadastroCarro dialog = DialogCadastroCarro.newInstance(livro, new DialogCadastroCarro.OnListener() {
                    @Override
                    public void aoConcluir(CarroVO livro) {
                        new GravarLivroAsynkTask().execute(livro);
                    }
                });
                dialog.show(getSupportFragmentManager(), "dialogLivro");
            }
        }));
    }

    private class GravarLivroAsynkTask extends AsyncTask<CarroVO, CarroVO, CarroVO> {
        @Override
        protected CarroVO doInBackground(CarroVO... livros) {
            CarroDAO.abrirConexao(MainActivity.this);
            CarroDAO.inserir(livros[0]);
            CarroDAO.fecharConexao();
            return livros[0];
        }

        @Override
        protected void onPostExecute(CarroVO livro) {
            ((CarrosRecycleViewAdapter) rvLivros.getAdapter()).adicionarItem(livro);
        }
    }

    private class RecuperarLivrosAsynkTask extends AsyncTask<Void, Void, ArrayList<CarroVO>> {
        @Override
        protected ArrayList<CarroVO> doInBackground(Void... voids) {
            ArrayList<CarroVO> lista;

            CarroDAO.abrirConexao(MainActivity.this);
            lista = CarroDAO.recuperar(new CarroVO());
            CarroDAO.fecharConexao();

            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<CarroVO> lista) {
            ((CarrosRecycleViewAdapter) rvLivros.getAdapter()).setarLista(lista);
        }
    }

    private class DeletarLivroAsynkTask extends AsyncTask<CarroVO, Void, CarroVO>{
        @Override
        protected CarroVO doInBackground(CarroVO... livros) {
            CarroDAO.abrirConexao(MainActivity.this);
            CarroDAO.deletar(livros[0]);
            CarroDAO.fecharConexao();
            return livros[0];
        }

        @Override
        protected void onPostExecute(CarroVO livro) {
            ((CarrosRecycleViewAdapter) rvLivros.getAdapter()).removerItem(livro);
        }
    }
}
