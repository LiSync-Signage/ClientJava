package org.LiSync;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner selectOption = new Scanner(System.in);
    Integer option;

    do {
      System.out.println("\nBem-vindo ao menu da LiSync!");
      System.out.println("----------------------------");
      System.out.println("""
          Digite o número para acessar
          1 - Cadastrar
          2 - Entrar
          3 - Sair
          """);

      System.out.print("Insira aqui: ");
      option = selectOption.nextInt();

      switch (option) {
        case 1 -> {
          System.out.println("Redirecionando para Cadastro ...");
          MetodosCadastro cadastro = new MetodosCadastro();
          cadastro.menuCadastro();
        }
        case 2 -> {
          System.out.println("Redirecionando para Login ...");
          MetodosLogin login = new MetodosLogin();
          login.menuLogin();
        }
        case 3 -> {
          System.out.println("Até logo! Encerrando o sistema ...");
          System.exit(0);
        }
        default -> System.out.println("Opção Inválida! Tente novamente.");
      }
    } while (option != 3);
  }
}