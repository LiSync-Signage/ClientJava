package org.LiSync.models;

public class Usuario {
	private Integer idUsuario;
	private String nome;
	private String email;
	private String senha;
	private Integer fkEmpresa;

	public Usuario(){}

	public Usuario(Integer idUsuario, String nome, String email, String senha, Integer fkEmpresa) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.fkEmpresa = fkEmpresa;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getFkEmpresa() {
		return fkEmpresa;
	}

	public void setFkEmpresa(Integer fkEmpresa) {
		this.fkEmpresa = fkEmpresa;
	}

	@Override
	public String toString() {
		return """
				ID Usuário: %d
				Nome: %s
				Email: %s
				Senha: %s
				Empresa (FK): %d
				""".formatted(this.idUsuario, this.nome, this.email, this.senha, this.fkEmpresa);
	}
}
