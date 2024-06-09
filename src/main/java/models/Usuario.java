package models;

public class Usuario {
	private Integer idUsuario;
	private String nomeUsuario;
	private String email;
	private String senha;
	private Integer fkEmpresa;
	private Integer fkGestor;

	public Usuario(){}

	public Usuario(String nomeUsuario, String email, String senha, Integer fkEmpresa, Integer fkGestor) {
		this.nomeUsuario = nomeUsuario;
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

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
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
                Gestor (FK): %d
                """.formatted(this.idUsuario, this.nomeUsuario, this.email, this.senha, this.fkEmpresa, this.fkGestor);
	}
}
