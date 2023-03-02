package br.com.livino.apicorreios.controller;

import br.com.livino.apicorreios.model.Address;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreiosController {
    @GetMapping("/status")
    public String getStatus(){
        return "UP";
    }

    @GetMapping("/zipcode/{zipcode}")
    public Address getAdressByZipCode(@PathParam("zipcode") String zipcode){
        return Address.builder().zipCode(zipcode).build();
    }
}
