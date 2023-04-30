package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TransferirActivity extends AppCompatActivity {

    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacoes);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        TextView tipoOperacao = findViewById(R.id.tipoOperacao);
        EditText numeroContaOrigem = findViewById(R.id.numeroContaOrigem);
        TextView labelContaDestino = findViewById(R.id.labelContaDestino);
        EditText numeroContaDestino = findViewById(R.id.numeroContaDestino);
        EditText valorOperacao = findViewById(R.id.valor);
        Button btnOperacao = findViewById(R.id.btnOperacao);

        valorOperacao.setHint(valorOperacao.getHint() + " transferido");
        tipoOperacao.setText("TRANSFERIR");
        btnOperacao.setText("Transferir");

        btnOperacao.setOnClickListener(
                v -> {
                    String numOrigem = numeroContaOrigem.getText().toString();
                    String numDestino = numeroContaDestino.getText().toString();

                    double valor = Double.valueOf(valorOperacao.getText().toString());

                    //Validações de número da conta e valor
                    if (numOrigem.isEmpty()) {
                        Toast.makeText(this, "Insira o número da conta.", Toast.LENGTH_SHORT).show();
                    } else if (numDestino.isEmpty()) {
                        Toast.makeText(this, "Insira o número da conta.", Toast.LENGTH_SHORT).show();
                    } else if (numOrigem.equals(numDestino)){
                        Toast.makeText(this, "Insira um número de origem/destino diferente", Toast.LENGTH_SHORT).show();
                    }
                    else if (valor <= 0) {
                        Toast.makeText(this, "Valor mínimo da operação é de R$1,00.", Toast.LENGTH_SHORT).show();
                    }

                    viewModel.transferir(numOrigem, numDestino, valor);
                    finish();
                }
        );

    }
}