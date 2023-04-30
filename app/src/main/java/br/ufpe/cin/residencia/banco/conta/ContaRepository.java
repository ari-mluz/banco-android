package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    @WorkerThread
    public void criarConta(Conta conta) {

        dao.criarConta(conta);
    }

    // Implementação dos métodos atualizarConta e removerConta
    @WorkerThread
    public void atualizarConta(Conta conta) {
        dao.atualizarConta(conta);
    }

    @WorkerThread
    public void removerConta(Conta conta) {

        dao.removerConta(conta);
    }

    // Implementação dos métodos de busca por número da conta, nome e CPF
    @WorkerThread
    public List<Conta> buscarNomeCliente(String nomeCliente) {
        return dao.buscarNomeCliente(nomeCliente);

    }

    @WorkerThread
    public List<Conta> buscarCpfCliente(String cpfCliente) {
        return dao.buscarCpfCliente(cpfCliente);

    }

    @WorkerThread
    public List<Conta> buscarNumeroConta(String numeroConta) {
        return dao.buscarNumeroConta(numeroConta);

    }
}