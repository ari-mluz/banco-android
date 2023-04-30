package br.ufpe.cin.residencia.banco.conta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufpe.cin.residencia.banco.R;

public class AdicionarContaActivity extends AppCompatActivity {

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

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();

                    //Validações por campo antes da criação da conta
                    // 1. Validação no número da conta
                    if(numeroConta.length() != 5) {
                        campoNumero.setError("O número da conta deve possuir 5 dígitos.");
                        return;
                    }
                    // 2. Validação no saldo da conta
                    if(saldoConta.isEmpty()) {
                        campoSaldo.setError("Por favor, insira um valor.");
                        return;
                    }
                    // 3. Validação no nome
                    if(nomeCliente.isEmpty()) {
                        campoNome.setError("O nome não pode estar em branco.");
                        return;
                    }
                    //4. Validação no CPF
                    if(cpfCliente.length() != 11) {
                        campoCPF.setError("O campo CPF deve conter 11 dígitos.");
                        return;
                    }

                    Conta conta = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);

                    //Chama o método criarConta
                    viewModel.criarConta(conta);
                    finish();
                }
        );

    }
}