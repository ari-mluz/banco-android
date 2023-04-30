package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufpe.cin.residencia.banco.R;

public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);

        //Preenche os campos com as informações da conta obtidas através do número da conta via Intent
        viewModel.buscarNumeroConta(numeroConta);
        viewModel.contaAtual.observe(this, conta -> {
            campoNumero.setText(conta.numero);
            campoSaldo.setText(String.valueOf(conta.saldo));
            campoCPF.setText(conta.cpfCliente);
            campoNome.setText(conta.nomeCliente);
        });

        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //Validações da edição
                    if(nomeCliente.isEmpty()) {
                        campoNome.setError("Este campo é obrigatório.");
                        campoNome.requestFocus();
                        return;
                    }
                    if(cpfCliente.isEmpty()) {
                        campoCPF.setError("Este campo é obrigatório.");
                        campoCPF.requestFocus();
                        return;
                    }
                    if(saldoConta.isEmpty()) {
                        campoSaldo.setError("Este campo é obrigatório.");
                        campoSaldo.requestFocus();
                        return;
                    }

                    //Chamada do método de atualização de conta
                    Conta dadosAtualizados = new Conta(numeroConta, Double.parseDouble(saldoConta), nomeCliente, cpfCliente);
                    viewModel.atualizarConta(dadosAtualizados);
                    finish();
                }
        );

        btnRemover.setOnClickListener(v -> {
            //Remoção de conta
            Conta conta = viewModel.contaAtual.getValue();
            viewModel.removerConta(conta);
            finish();
        });
    }
}