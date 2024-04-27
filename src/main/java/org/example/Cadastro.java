package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * The type Cadastro.
 */
public class Cadastro {
    private final MongoCollection<Document> collection;

    /**
     * Instantiates a new Cadastro.
     */
    public Cadastro() {
        // Conectando ao banco de dados "todo-list" e à coleção "usuarios"
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("todo-list");
        this.collection = database.getCollection("usuarios");
    }

    /**
     * Cadastrar usuario.
     *
     * @param email the email
     * @param senha the senha
     */
    public void cadastrarUsuario(String email, String senha) {
        // Criando um novo documento para o usuário
        Document usuario = new Document("email", email)
                .append("password", senha);

        // Inserindo o documento na coleção
        collection.insertOne(usuario);
    }
}
