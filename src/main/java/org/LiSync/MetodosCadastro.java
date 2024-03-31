package org.LiSync;

import java.util.Scanner;

public class MetodosCadastro {
  Scanner input = new Scanner(System.in);
  Scanner inputNext = new Scanner(System.in);

  void menuCadastro() {
    String nome;

    do {
      System.out.println("\n|---------- CADASTRO ----------|");
      System.out.println("Insira suas informações");
      System.out.println("Digite 0 para voltar ao MENU");

      System.out.print("Nome: ");
      nome = input.nextLine();

      if (nome.equals("0")) break;

      String sobrenome;
      String empresa;
      String email;
      String senha;
      String confirmarSenha;

      System.out.print("Sobrenome: ");
      sobrenome = inputNext.nextLine();

      System.out.print("Empresa: ");
      empresa = inputNext.nextLine();

      System.out.print("E-mail: ");
      email = inputNext.nextLine();

      System.out.print("Senha: ");
      senha = inputNext.nextLine();

      System.out.print("Confirmar senha: ");
      confirmarSenha = inputNext.nextLine();

      resultadoAutenticacao(
          verificarConfirmarSenha(senha, confirmarSenha),
          verificarEmail(email),
          verificarCamposVazio(nome, sobrenome, empresa, email, senha, confirmarSenha),
          validarTamanhoSenha(senha), nome, sobrenome);

    } while (true);
  }

  Boolean verificarCamposVazio(String nome, String sobrenome, String empresa, String email, String senha, String confirmarSenha) {
    return nome.trim().isEmpty() || sobrenome.trim().isEmpty() || empresa.trim().isEmpty() || email.trim().isEmpty() || senha.trim().isEmpty() || confirmarSenha.trim().isEmpty();
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

  Boolean verificarEmail(String email) {
    String caracteresValidos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._-@";
    for (int i = 0; i < email.length(); i++) {
      String caractereAtual = email.substring(i, i + 1);
      // O substring serve para "pegar uma parte” de uma String desde um índice definido (i) até o outro (i + 1).
      if (!caracteresValidos.contains(caractereAtual)) {
        return false;
      }
    }
    return true;
  }

  void resultadoAutenticacao(Boolean validacaoConfirmarSenha, Boolean validacaoCampoVazio, Boolean validacaoSenha, Boolean verificarEmail, String nome, String sobrenome) {
    switch (1) {
      case 1:
        if (!validacaoCampoVazio) {
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
      case 4:
        if (verificarEmail) {
          System.out.println("Contém caracteres não aceitos");
          break;
        }
      default:
        System.out.println("\nBem-vindo(a), %s %s!".formatted(nome, sobrenome));
        Main.main(new String[0]);
    }
  }
}
