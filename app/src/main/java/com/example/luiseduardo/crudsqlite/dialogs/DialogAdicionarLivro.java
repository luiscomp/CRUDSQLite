package com.example.luiseduardo.crudsqlite.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.vo.LivroVO;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class DialogAdicionarLivro extends DialogFragment {

    private OnListener listener;
    private Button btnCancelar;
    private Button btnGravar;
    private EditText edtTitulo;
    private EditText edtAutor;
    private EditText edtAno;
    private LivroVO livro;

    public void setListener(OnListener listener) {
        this.listener = listener;
    }

    public void setLivro(LivroVO livro) {
        this.livro = livro;
    }

    public interface OnListener {
        void aoConcluir(LivroVO livro);
    }

    public static DialogAdicionarLivro newInstance(OnListener listener) {
        DialogAdicionarLivro dialog = new DialogAdicionarLivro();
        dialog.setListener(listener);
        return dialog;
    }

    public static DialogAdicionarLivro newInstance(LivroVO livro, OnListener listener) {
        DialogAdicionarLivro dialog = new DialogAdicionarLivro();
        dialog.setLivro(livro);
        dialog.setListener(listener);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_adicionar_livro, null);

        instanciarComponentes(rootView);
        aplicarListeners();
        if(livro != null) {
            setarValores();
        }

        builder.setView(rootView);

        return builder.create();
    }

    private void setarValores() {
        edtTitulo.setText(livro.getTitulo());
        edtAutor.setText(livro.getAutor());
        edtAno.setText(livro.getAno().toString());
    }

    private void aplicarListeners() {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dadosValidos()) {
                    if(livro == null) {
                        livro = new LivroVO();
                    }
                    livro.setTitulo(edtTitulo.getText().toString());
                    livro.setAutor(edtAutor.getText().toString());
                    livro.setAno(Integer.valueOf(edtAno.getText().toString()));

                    listener.aoConcluir(livro);
                    dismiss();
                }
            }
        });
    }

    private boolean dadosValidos() {
        boolean camposInvalidos = false;
        if(edtTitulo.getText().length() == 0) {
            edtTitulo.setError("Informe o título.");
            camposInvalidos = true;
        }
        if(edtAutor.getText().length() == 0) {
            edtAutor.setError("Informe o autor.");
            camposInvalidos = true;
        }
        if(edtAno.getText().length() == 0) {
            edtAno.setError("Informe o ano.");
            camposInvalidos = true;
        }
        return !camposInvalidos;
    }

    private void instanciarComponentes(View view) {
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnGravar = view.findViewById(R.id.btnGravar);

        edtTitulo = view.findViewById(R.id.edtTitulo);
        edtAutor = view.findViewById(R.id.edtAutor);
        edtAno = view.findViewById(R.id.edtAno);
    }
}
