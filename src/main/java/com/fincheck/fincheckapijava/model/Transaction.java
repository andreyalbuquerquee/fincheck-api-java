package com.fincheck.fincheckapijava.model;

import java.sql.Timestamp;
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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UUID userId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = BankAccount.class)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UUID bankAccountId;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UUID categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
