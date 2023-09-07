package com.fincheck.fincheckapijava.model;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fincheck.fincheckapijava.model.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = User.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UUID userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
