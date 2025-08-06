package com.francinjr.rentalbusiness.components.customer.core.domain.entities;

public class Address {
    private final Long id;
    private final String cep;
    private final String street;
    private final String additionalInfo;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String number;

    public Address(Long id, String cep, String street, String additionalInfo, String neighborhood,
            String city,
            String state, String number) {
        this.id = id;
        this.cep = cep;
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public String getStreet() {
        return street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }
}
