package com.fincheck.fincheckapijava.model;

import java.util.List;
import java.util.UUID;

import com.fincheck.fincheckapijava.shared.dtos.bankAccount.UpdateBankAccountDto;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.fincheck.fincheckapijava.model.enums.BankAccountType;
import com.fincheck.fincheckapijava.shared.dtos.bankAccount.CreateBankAccountDto;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "initial_balance", nullable = false)
    private double initialBalance;

    @Column(name = "color", nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "bank_account_type", nullable = false)
    @Type(PostgreSQLEnumType.class)
    private BankAccountType type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public BankAccount() {}
    
    public BankAccount(CreateBankAccountDto createBankAccountDto, User user) {
        this.name = createBankAccountDto.name();
        this.initialBalance = createBankAccountDto.initialBalance();
        this.color = createBankAccountDto.color();
        this.type = BankAccountType.valueOf(createBankAccountDto.type());
        this.user = user;
    }

    public BankAccount(UpdateBankAccountDto updateBankAccountDto, User user) {
        this.name = updateBankAccountDto.name();
        this.initialBalance = updateBankAccountDto.initialBalance();
        this.color = updateBankAccountDto.color();
        this.type = BankAccountType.valueOf(updateBankAccountDto.type());
        this.user = user;
    }

    //#region Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BankAccountType getType() {
        return type;
    }

    public void setType(BankAccountType type) {
        this.type = type;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    public List<Transaction> getTransactions() { return transactions; }
    //#endregion
}
