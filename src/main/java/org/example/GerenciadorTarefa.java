package org.example;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * The type Gerenciador tarefa.
 */
public class GerenciadorTarefa {
    private MongoCollection<Document> taskCollection;

    /**
     * Instantiates a new Gerenciador tarefa.
     */
    public GerenciadorTarefa() {
        // Inicialize a conexão com o banco de dados
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("todo-list");
            taskCollection = database.getCollection("tasks");
        } catch (Exception e) {
            System.err.print("Erro ao conectar ao banco de dados MongoDB: " + e.getMessage());
        }
    }

    /**
     * Criar tarefa.
     *
     * @param usuario      the usuario
     * @param descricao    the descricao
     * @param data         the data
     * @param statusTarefa the status tarefa
     */
    public void criarTarefa(String usuario, String descricao, String data, String statusTarefa) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Acessar o banco de dados e a coleção
            MongoDatabase database = mongoClient.getDatabase("todo-list");
            MongoCollection<Document> collection = database.getCollection("tasks");

            // Criar um documento com os detalhes da tarefa
            Document taskDocument = new Document("descricao", descricao)
                    .append("data", data)
                    .append("statusTarefa", statusTarefa)
                    .append("usuario", usuario);

            // Inserir o documento na coleção
            collection.insertOne(taskDocument);
            System.out.println("Tarefa adicionada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    /**
     * Excluir tarefa.
     *
     * @param taskId the task id
     */
    public void excluirTarefa(String taskId) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("todo-list");
            MongoCollection<Document> collection = database.getCollection("tasks");

            // Converter o id para ObjectId
            ObjectId objectId = new ObjectId(taskId);

            // Deletar a task com o id especificado
            DeleteResult result = collection.deleteOne(Filters.eq("_id", objectId));

            // Verificar se a task foi deletada com sucesso
            if (result.getDeletedCount() == 1) {
                System.out.println("Task deletada com sucesso!");
            } else {
                System.out.println("Não foi possível encontrar a task com o id especificado.");
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e);
        }
    }

    /**
     * Listar tarefas por user id.
     *
     * @param usuario the usuario
     */
    public void listarTarefasPorUserId(String usuario) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("todo-list");
            MongoCollection<Document> collection = database.getCollection("tasks");

            // Consultar as tarefas com o id de usuário especificado
            FindIterable<Document> result = collection.find(new Document("usuario", usuario));

            // Iterar sobre o resultado e exibir as tarefas encontradas
            for (Document document : result) {
                System.out.println(document.toJson());
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e);
        }
    }

    /**
     * Editar tarefa por id.
     *
     * @param taskId       the task id
     * @param descricao    the descricao
     * @param statusTarefa the status tarefa
     */
    public void editarTarefaPorId(String taskId, String descricao, String statusTarefa) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("todo-list");
            MongoCollection<Document> collection = database.getCollection("tasks");

            // Atualizar a descrição e o status da tarefa
            Document filter = new Document("_id", new ObjectId(taskId));
            Document update = new Document("$set", new Document("descricao", descricao).append("statusTarefa", statusTarefa));

            // Executar a atualização
            collection.updateOne(filter, update);

            System.out.println("Tarefa atualizada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao editar a tarefa: " + e);
        }
    }
}
