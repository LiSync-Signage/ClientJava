package org.LiSync;

import java.util.Scanner;

public class MetodosLogin {

  Scanner input = new Scanner(System.in);
  Scanner inputNext = new Scanner(System.in);

  void menuLogin() {
    String usuario;

    do {
      System.out.println("\n|----------- LOGIN -----------|");
      System.out.println("Insira suas informações");
      System.out.println("Digite 0 para voltar ao MENU");

      System.out.print("Usuário: ");
      usuario = input.nextLine();

      if (usuario.equals("0")) break;

      String senha;
      String confirmarSenha;

      System.out.print("Senha: ");
      senha = inputNext.nextLine();

      System.out.print("Confirmar senha: ");
      confirmarSenha = inputNext.nextLine();

      resultadoAutenticacao(
          verificarConfirmarSenha(senha, confirmarSenha),
          verificarCamposVazio(usuario, senha, confirmarSenha),
          validarTamanhoSenha(senha), usuario);

    } while (true);
  }

  Boolean verificarCamposVazio(String usuario, String senha, String confirmarSenha) {
    return usuario.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty();
  }

  Boolean validarTamanhoSenha(String senha) {
    Boolean senhaValida = true;
    if (senha.length() >= 6) {
      senhaValida = false;
    }
    return senhaValida;
  }

  Boolean verificarConfirmarSenha(String senha, String confirmarSenha) {
    return confirmarSenha.equals(senha);
  }

  void resultadoAutenticacao(Boolean validacaoConfirmarSenha, Boolean validacaoCampoVazio, Boolean validacaoSenha, String nomeUsuario) {
    switch (1) {
      case 1:
        if (validacaoCampoVazio) {
          System.out.println("Todos os campos devem estar preenchidos");
          break;
        }
      case 2:
        if (validacaoSenha) {
          System.out.println("Senha deve possuir mais do que 6 caracteres");
          break;
        }
      case 3:
        if (!validacaoConfirmarSenha) {
          System.out.println("Senhas não se conferem");
          break;
        }
      default:
        System.out.println("\nBem-vindo, %s!".formatted(nomeUsuario));
        Main.main(new String[0]);
    }
  }
}
