package com.example.luiseduardo.crudsqlite.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luiseduardo.crudsqlite.R;
import com.example.luiseduardo.crudsqlite.vo.CarroVO;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * Created by Luis Eduardo on 16/09/2017.
 */

public class DialogCadastroCarro extends DialogFragment {

    private OnListener listener;
    private Button btnCancelar;
    private Button btnGravar;
    private EditText edtModelo;
    private EditText edtMarca;
    private EditText edtAno;
    private CarroVO carro;
    private View vCor;
    private int cor;

    public void setListener(OnListener listener) {
        this.listener = listener;
    }

    public void setCarro(CarroVO carro) {
        this.carro = carro;
    }

    public interface OnListener {
        void aoConcluir(CarroVO livro);
    }

    public static DialogCadastroCarro newInstance(OnListener listener) {
        DialogCadastroCarro dialog = new DialogCadastroCarro();
        dialog.setListener(listener);
        return dialog;
    }

    public static DialogCadastroCarro newInstance(CarroVO livro, OnListener listener) {
        DialogCadastroCarro dialog = new DialogCadastroCarro();
        dialog.setCarro(livro);
        dialog.setListener(listener);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_adicionar_carro, null);

        instanciarComponentes(rootView);
        aplicarListeners();
        if(carro != null) {
            setarValores();
        }

        builder.setView(rootView);

        return builder.create();
    }

    private void setarValores() {
        edtModelo.setText(carro.getModelo());
        edtMarca.setText(carro.getMarca());
        edtAno.setText(carro.getAno().toString());
        vCor.setBackgroundColor(carro.getCor());
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
                    if(carro == null) {
                        carro = new CarroVO();
                    }
                    carro.setModelo(edtModelo.getText().toString());
                    carro.setMarca(edtMarca.getText().toString());
                    carro.setAno(Integer.valueOf(edtAno.getText().toString()));
                    if(cor != 0) {
                        carro.setCor(cor);
                    }

                    listener.aoConcluir(carro);
                    dismiss();
                }
            }
        });
    }

    private boolean dadosValidos() {
        boolean camposInvalidos = false;
        if(edtModelo.getText().length() == 0) {
            edtModelo.setError("Informe o título.");
            camposInvalidos = true;
        }
        if(edtMarca.getText().length() == 0) {
            edtMarca.setError("Informe o autor.");
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

        vCor = view.findViewById(R.id.vCor);
        edtModelo = view.findViewById(R.id.edtModelo);
        edtMarca = view.findViewById(R.id.edtMarca);
        edtAno = view.findViewById(R.id.edtAno);

        vCor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(getActivity())
                        .setTitle("Selecione uma Cor")
                        .initialColor(carro != null ? carro.getCor() : Color.parseColor("#FFFFFF"))
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                Toast.makeText(getActivity(), "0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                vCor.setBackgroundColor(selectedColor);
                                cor = selectedColor;
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });
    }
}
