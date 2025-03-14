package com.example.lab2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;


    // Getters
    public String getName() {
        return name;
    }

    // Setter (Make sure this exists)
    public void setName(String name) {
        this.name = name;
    }

        // Getters
        public String getEmail () {
            return email;
        }

        // Setter (Make sure this exists)
        public void setEmail (String email){
            this.email = email;
        }
    }


