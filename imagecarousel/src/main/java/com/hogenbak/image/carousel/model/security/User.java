package com.hogenbak.image.carousel.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column
    String password;
}
