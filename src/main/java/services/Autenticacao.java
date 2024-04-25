package services;

import dao.UsuarioDAO;

public class Autenticacao {
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Boolean validacaoLogin(String email, String senha) {
        Integer usuarioExistentes = usuarioDAO.contarUsuariosExistentes(email, senha);
        if (usuarioExistentes == 1) {
            System.out.println("Login realizado por usuário de email: " + email + "\n");
            return true;
        } else {
            System.out.println("Falha no login, não foi possível econtrar usuário de email: " + email + "\n");
            return false;
        }
    }
}
