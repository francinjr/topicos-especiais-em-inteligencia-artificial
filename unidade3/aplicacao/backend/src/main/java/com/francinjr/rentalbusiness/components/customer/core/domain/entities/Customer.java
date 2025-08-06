package com.francinjr.rentalbusiness.components.customer.core.domain.entities;

import com.francinjr.rentalbusiness.commons.core.domain.entities.DomainModel;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.Cpf;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.CustomerAge;
import com.francinjr.rentalbusiness.commons.core.exceptions.DomainException;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.Email;
import java.time.LocalDate;
import java.time.Period;

public class Customer implements DomainModel {

    private final Long id;
    private String name;
    private CustomerAge age;
    private Email email;
    private final Cpf cpf;
    private Address address;

    public Customer(Long id, String name, LocalDate birthDate, String email, String cpf,
            Address address) {
        validateId(id);
        validateName(name);

        this.id = id;
        this.name = name;
        this.age = new CustomerAge(birthDate);
        this.email = new Email(email);
        this.cpf = new Cpf(cpf);

        // Decidir se o endereço deve ou não ser obrigatório, considerando a feature de entrega de produto na residência
        this.address = address;
    }

    public void verifyEligibility() {
        if (this.age.getValue() < 18) {
            throw new DomainException("Cliente precisa ter 18 anos de idade ou mais para utilizar a aplicação.");
        }
    }

    public void changeCustomerValues(String newName, LocalDate newBirthDate, String newEmail, Address newAddress) {
        this.changeName(newName);
        this.changeAge(newBirthDate);
        this.changeEmail(newEmail);
        this.changeAddress(newAddress);
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void changeAge(LocalDate newBirthDate) {
        this.age = new CustomerAge(newBirthDate);
    }

    public void changeEmail(String newEmail) {
        this.email = new Email(newEmail);
    }

    public void changeAddress(Address newAddress) {
        this.address = newAddress;
    }

    public void validateId(Long id) {
        if (id != null && id < 0) {
            throw new DomainException(
                    "O identificador do cliente deve ser um valor maior ou igual a zero");
        }
    }

    public void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new DomainException(
                    "O nome do cliente deve ser informado e ter pelo menos 1 caractere");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age.getValue();
    }

    public LocalDate getBirthDate() {
        return age.getBirthDate();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getCpf() {
        return cpf.getValue();
    }

    public Address getAddress() {
        return address;
    }

    public Long getAddressId() {
        return address.getId();
    }
}
