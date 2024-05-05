package models;

public class Usuario {
	private Integer idUsuario;
	private String nome;
	private String email;
	private String senha;
	private Integer fkEmpresa;
	private Integer fkGestor;

	public Usuario(){}

	public Usuario(String nome, String email, String senha, Integer fkEmpresa, Integer fkGestor) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.fkEmpresa = fkEmpresa;
		this.fkGestor = fkGestor;
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

	public Integer getFkGestor() {
		return fkGestor;
	}

	public void setFkGestor(Integer fkGestor) {
		this.fkGestor = fkGestor;
	}

	@Override
	public String toString() {
		return """
				ID Usuário: %d
				Nome: %s
				Email: %s
				Senha: %s
				Empresa (FK): %d
				Gstor (FK): %d
				""".formatted(this.idUsuario, this.nome, this.email, this.senha, this.fkEmpresa, this.fkGestor);
	}
}
