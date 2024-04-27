package org.example;

import org.bson.types.ObjectId;

import java.util.Scanner;

/**
 * The type Menu.
 */
public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorTarefa gerenciadorTarefa = new GerenciadorTarefa();
    private static Cadastro cadastro = new Cadastro();
    private static Login login = new Login();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Bem-vindo! Escolha uma opção:");
            System.out.println("1. Login");
            System.out.println("2. Cadastro");
            System.out.println("3. Sair");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loggedIn = login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }

        exibirMenuTodoList();
    }

    private static void exibirMenuTodoList() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== Menu Todo List ===");
            System.out.println("1. Listar tarefas");
            System.out.println("2. Adicionar tarefa");
            System.out.println("3. Excluir tarefa");
            System.out.println("4. Editar tarefa");
            System.out.println("5. Sair");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listarTarefas();
                    break;
                case 2:
                     criarTarefa();
                    break;
                case 3:
                     excluirTarefa();
                    break;
                case 4:
                     editarTarefa();
                    break;
                case 5:
                    System.out.println("Saindo do Todo List...");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarTarefa() {
        Scanner scanner = new Scanner(System.in);
        ObjectId userId = login.getUserId();

        System.out.println("Criando tarefas:");

        System.out.print("Digite a descrição da tarefa: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite a data de vencimento da tarefa (dd-mm-yyyy): ");
        String dataVencimento = scanner.nextLine();

        System.out.print("Digite o status da tarefa (pendente, em andamento, concluída): ");
        String status = scanner.nextLine();

        gerenciadorTarefa.criarTarefa(userId.toString(), descricao, dataVencimento, status);
    }

    private static void excluirTarefa() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da tarefa que deseja excluir:");
        String taskId = scanner.nextLine();

        System.out.println("Excluindo tarefa:");
        gerenciadorTarefa.excluirTarefa(taskId);
    }

    private static void listarTarefas() {
        System.out.println("Listando tarefa:");
        ObjectId userId = login.getUserId();

        if (userId != null) {
            System.out.println("Tarefas do usuário " +  ":");
            gerenciadorTarefa.listarTarefasPorUserId(userId.toString());
        } else {
            System.out.println("Nenhuma tarefa cadastrada.");
        }
    }

    private static void editarTarefa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Editando tarefa:");

        System.out.print("Digite o ID da tarefa que deseja editar: ");
        String taskId = scanner.nextLine();

        System.out.print("Nova descrição da tarefa: ");
        String novaDescricao = scanner.nextLine();

        System.out.print("Novo status da tarefa: ");
        String novoStatus = scanner.nextLine();

        gerenciadorTarefa.editarTarefaPorId(taskId, novaDescricao, novoStatus);
    }

    private static boolean login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o e-mail:");
        String email = scanner.nextLine();

        System.out.println("Digite a senha:");
        String senha = scanner.nextLine();

        login.fazerLogin(email, senha);
        return true;
    }

    private static void cadastrar() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o e-mail do usuário:");
        String email = scanner.nextLine();

        System.out.println("Digite a senha do usuário:");
        String senha = scanner.nextLine();

        cadastro.cadastrarUsuario(email, senha);
        System.out.println("Cadastro realizado com sucesso!");
    }

}
