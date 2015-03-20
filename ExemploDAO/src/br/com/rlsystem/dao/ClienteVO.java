package br.com.rlsystem.dao;

public class ClienteVO {
	
	private int id;
	private String nome;
	private double renda;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public double getRenda() {
		return renda;
	}
}
