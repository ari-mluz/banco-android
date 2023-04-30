package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import br.ufpe.cin.residencia.banco.conta.ContaAdapter;

public class PesquisarActivity extends AppCompatActivity {
    BancoViewModel viewModel;
    ContaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);
        EditText aPesquisar = findViewById(R.id.pesquisa);
        Button btnPesquisar = findViewById(R.id.btn_Pesquisar);
        RadioGroup tipoPesquisa = findViewById(R.id.tipoPesquisa);
        RecyclerView rvResultado = findViewById(R.id.rvResultado);
        adapter = new ContaAdapter(getLayoutInflater());
        rvResultado.setLayoutManager(new LinearLayoutManager(this));
        rvResultado.setAdapter(adapter);

        btnPesquisar.setOnClickListener(
                v -> {
                    String oQueFoiDigitado = aPesquisar.getText().toString();

                    //Armazena e processa de acordo com o tipo de busca selecionado
                    int botaoSelecionado = tipoPesquisa.getCheckedRadioButtonId();

                    RadioButton radioButton = findViewById(botaoSelecionado);

                    String tipoBusca = radioButton.getText().toString();

                    if(tipoBusca.equals("CPF")) {
                        viewModel.buscarPeloCPF(oQueFoiDigitado);
                    } else if(tipoBusca.equals("Nome") && !(oQueFoiDigitado.isEmpty())) {
                        viewModel.buscarPeloNome(oQueFoiDigitado);
                    } else if(!oQueFoiDigitado.isEmpty()){
                        viewModel.buscarNumeroConta(oQueFoiDigitado);
                    }

                }
        );

        //Observa os resultados e atualiza de acordo
        viewModel.contasDadosAtualizados.observe(this, contas -> {adapter.submitList(contas);});


    }
}