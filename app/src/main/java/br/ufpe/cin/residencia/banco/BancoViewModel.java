package br.ufpe.cin.residencia.banco;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.residencia.banco.conta.Conta;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;

    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    private MutableLiveData<List<Conta>> _contasDadosAtualizados = new MutableLiveData<>();
    public LiveData<List<Conta>> contasDadosAtualizados = _contasDadosAtualizados;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = this.repository.getContas();
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        //Inicia uma nova thread e chama as operações de debitar e creditar
        new Thread( () -> {

            debitar(numeroContaOrigem, valor);

            creditar(numeroContaDestino, valor);

        }).start();

    }

    void creditar(String numeroConta, double valor) {
        //Inicia uma thread, busca a conta pelo número, aplica o método creditar de Conta.java e atualiza o valor para aplicar o crédito
        new Thread(()->{
            List<Conta> contas = this.repository.buscarNumeroConta(numeroConta);
            Conta conta = contas.get(0);
            conta.creditar(valor);
            this.repository.atualizarConta(conta);
        }).start();
    }

    void debitar(String numeroConta, double valor) {
        //Inicia uma thread, busca a conta pelo número, aplica o método debitar de Conta.java e atualiza o valor para aplicar o débito
        new Thread(()->{
            List<Conta> contas = this.repository.buscarNumeroConta(numeroConta);
            Conta conta = contas.get(0);
            conta.debitar(valor);
            this.repository.atualizarConta(conta);
        }).start();

    }

    void buscarPeloNome(String nomeCliente) {
        //Inicia uma thread e atualiza a lista de contas com os dados atualizados
        new Thread( () -> {
            List<Conta> listaContas = this.repository.buscarNomeCliente(nomeCliente);
            _contasDadosAtualizados.postValue(listaContas);
        }).start();
    }

    void buscarPeloCPF(String cpfCliente) {
        //Inicia uma thread e atualiza a lista de contas com os dados atualizados
        new Thread(() -> {
            List<Conta> listaContas = this.repository.buscarCpfCliente(cpfCliente);
            _contasDadosAtualizados.postValue(listaContas);
        }).start();
    }

    void buscarNumeroConta(String numeroConta) {
        //Inicia uma thread e atualiza a lista de contas com os dados atualizados
        new Thread(() -> {
            List<Conta> listaContas = this.repository.buscarNumeroConta(numeroConta);
            _contasDadosAtualizados.postValue(listaContas);
        }).start();
    }

    //Método para somar os saldos das contas do banco
    double calcularSubtotalBanco() {
        double valorTotalBanco = 0;

        List<Conta> contasBanco = contas.getValue();
        if(contasBanco != null) {
            for (Conta conta :contasBanco) {
                valorTotalBanco += conta.saldo;
            }
        }
        return valorTotalBanco;
    }
}