package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    //Implementação do método
    void criarConta(Conta conta) {

        new Thread( () -> repository.criarConta(conta)).start();
    }

    //Métodos de atualização e remoção de contas
    void atualizarConta(Conta conta) {
        new Thread( () -> repository.atualizarConta(conta)).start();
    }

    void removerConta(Conta conta) {
        new Thread( () -> repository.removerConta(conta)).start();
    }

    //Método de busca por número da conta
    void buscarNumeroConta(String numeroConta) {
        new Thread( () -> {
            List<Conta> contas = this.repository.buscarNumeroConta(numeroConta);
            _contaAtual.postValue(contas.get(0));
        }).start();
    }

}