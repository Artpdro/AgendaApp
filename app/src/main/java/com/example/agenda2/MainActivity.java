package com.example.agenda2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda2.adapter.ContatoAdapter;
import com.example.agenda2.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etTelefone;
    private Button btnAdicionar;
    private RecyclerView rvContatos;

    private final List<Contato> contatos = new ArrayList<>();
    private ContatoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome      = findViewById(R.id.et_nome);
        etTelefone  = findViewById(R.id.et_telefone);
        btnAdicionar= findViewById(R.id.btn_adicionar);
        rvContatos  = findViewById(R.id.rv_contatos);

        rvContatos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContatoAdapter(contatos, position -> {
            contatos.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(this, "Contato excluído", Toast.LENGTH_SHORT).show();
        });
        rvContatos.setAdapter(adapter);

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                btnAdicionar.setEnabled(
                        !etNome.getText().toString().trim().isEmpty() &&
                                !etTelefone.getText().toString().trim().isEmpty()
                );
            }
            @Override public void afterTextChanged(Editable s) {}
        };
        etNome.addTextChangedListener(watcher);
        etTelefone.addTextChangedListener(watcher);
        btnAdicionar.setEnabled(false);

        btnAdicionar.setOnClickListener(v -> {
            String nome = etNome.getText().toString().trim();
            String tel  = etTelefone.getText().toString().trim();

            contatos.add(new Contato(nome, tel));
            adapter.notifyItemInserted(contatos.size() - 1);
            rvContatos.scrollToPosition(contatos.size() - 1);

            etNome.setText("");
            etTelefone.setText("");
            etNome.requestFocus();
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(etNome.getWindowToken(), 0);

            Toast.makeText(this, "Contato adicionado", Toast.LENGTH_SHORT).show();
        });
    }
}
