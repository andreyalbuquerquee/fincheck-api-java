package com.fincheck.fincheckapijava.model;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.shared.dtos.TransactionDto;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
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
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = BankAccount.class)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "date", nullable = false)
    private Calendar date;

    @Column(name = "type", columnDefinition = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private TransactionType type;

    public Transaction() {
    }

    public Transaction(TransactionDto transactionDto, User user, BankAccount bankAccount, Category category) {
        this.user = user;
        this.bankAccount = bankAccount;
        this.category = category;
        this.name = transactionDto.name();
        this.value = transactionDto.value();
        this.date = transactionDto.date();
        this.date.setTimeZone(TimeZone.getDefault());
        this.type = TransactionType.valueOf(transactionDto.type());
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getBankAccountId() {
        return bankAccount.getId();
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public UUID getCategoryId() {
        return category.getId();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getDate() {
        return new Timestamp(date.getTimeInMillis() - 10800000);
    }

    public void setDate(Calendar date) {
        this.date = date;
        this.date.setTimeZone(TimeZone.getDefault());
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
