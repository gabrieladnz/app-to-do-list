package org.example;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

/**
 * The type Login.
 */
public class Login {
    private final MongoCollection<Document> collection;
    private final Scanner scanner;
    private ObjectId userId;

    /**
     * Instantiates a new Login.
     */
    public Login() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("todo-list");
        this.collection = database.getCollection("usuarios");
        this.scanner = new Scanner(System.in);
    }

    /**
     * Fazer login.
     *
     * @param email the email
     * @param senha the senha
     */
    public void fazerLogin(String email, String senha) {
        Document usuario = collection.find(Filters.and(Filters.eq("email", email), Filters.eq("password", senha))).first();
        if (usuario != null) {
            userId = usuario.getObjectId("_id");
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Dados de login incorretos ou não existentes. Tente novamente.");
        }
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public ObjectId getUserId() {
        return userId;
    }
}
