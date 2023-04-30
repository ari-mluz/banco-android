package br.ufpe.cin.residencia.banco.conta;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void criarConta(Conta conta);

    //Criação dos métodos atualizarConta e removerConta
    @Update
    void atualizarConta(Conta conta);

    @Delete
    void removerConta(Conta conta);

    @Query("SELECT * FROM contas ORDER BY numero ASC")
    LiveData<List<Conta>> contas();

    //Métodos de busca

    //1. Busca pelo número da conta
    @Query("SELECT * FROM contas WHERE numero = :numeroConta LIMIT 1")
    List<Conta>buscarNumeroConta(String numeroConta);

    //2. Busca pelo CPF
    @Query("SELECT * FROM contas WHERE cpfCliente = :cpfCliente")
    List<Conta>buscarCpfCliente(String cpfCliente);

    //3. Busca pelo nome
    @Query("SELECT * FROM contas WHERE nomeCliente LIKE :nomeCliente")
    List<Conta>buscarNomeCliente(String nomeCliente);

}
