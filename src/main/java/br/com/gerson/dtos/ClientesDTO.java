package br.com.gerson.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.gerson.domain.Clientes;

public class ClientesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private LocalDate dataNascimento;
	private String sexo;
	private String telefone;
	private String endereco;
	
	ClientesDTO(){
	}

	public ClientesDTO(Clientes obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.dataNascimento = obj.getDataNascimento();
		this.sexo = obj.getSexo().getDescricao();
		this.telefone = obj.getTelefone();
		this.endereco = obj.getEndereco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
