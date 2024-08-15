package com.example.blog_api.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId Id;

    private String username;
    private String password;

    public ObjectId getId() {
        return Id;
    }

    public void setId(ObjectId id) {
        this.Id = id;
    }

    // Getters and Setters
}
