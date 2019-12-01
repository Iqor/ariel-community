package com.igor.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "users")
@Getter @Setter
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String institution;

    @Column(nullable = false)
    private String password;

    @Column(name = "acess_counter")
    private Integer acessCounter;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Model> modelList;

    @Transient
    private String confirmPassword;

}
