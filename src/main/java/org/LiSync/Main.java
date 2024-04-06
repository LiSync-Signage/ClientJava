package org.LiSync;

import org.LiSync.dao.UsuarioDAO;
import org.LiSync.models.Usuario;
import org.LiSync.services.Autenticacao;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Autenticacao autenticacao = new Autenticacao();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    String prosseguir;
    Boolean cadastroValido = false;
    Usuario usuarioAutenticado = null;


    do {
      System.out.println("""
      \n|----------- LOGIN -----------|
      Insira suas informações
      Digite 0 para voltar ao MENU
      """);

      System.out.println("Digite seu e-mail: ");
      String email = input.next();
      if(email.equals("0")) break;

      System.out.println("Digite sua senha: ");
      String senha = input.next();
      if(senha.equals("0")) break;

      cadastroValido = autenticacao.validacaoLogin(email, senha);
      if(cadastroValido) {
        usuarioAutenticado = usuarioDAO.buscarCreedenciasUsuario(email, senha);
        System.out.println("Bem vindo %s!".formatted(usuarioAutenticado.getNome()));
          System.out.println("\nDados cliente: \n" + usuarioAutenticado.toString());
      } else {
        System.out.println("Email ou senha incorretos!");
        System.out.println("Realizar nova tentativa? (Digite 'N' Para cancelar) ");
        prosseguir = input.next();
        if(prosseguir.equalsIgnoreCase("N")) {
          System.out.println("Login encerrado");
          System.exit(0);
        }
      }
    } while (!cadastroValido);
  }
}