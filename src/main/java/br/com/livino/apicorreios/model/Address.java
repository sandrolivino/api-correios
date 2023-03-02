package br.com.livino.apicorreios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String zipCode;
    private String street;
    private String district;
    private String city;
    private String state;
}
