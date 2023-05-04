package com.example.springlv3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Table
@Entity
@NoArgsConstructor
public class CrudLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentlikeId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CrudId", nullable = false)
    @JsonManagedReference
    private Crud crud;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonManagedReference
    private Users users;

    public CrudLike (Crud crud, Users users){
        this.crud = crud;
        this.users = users;
    }

}
