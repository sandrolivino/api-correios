package br.com.livino.apicorreios.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Address {
    @Id
    private String zipCode;
    private String street;
    private String district;
    private String city;
    private String state;
}
