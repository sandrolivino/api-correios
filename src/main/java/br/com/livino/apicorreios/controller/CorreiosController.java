package br.com.livino.apicorreios.controller;


import br.com.livino.apicorreios.exception.NoContentException;
import br.com.livino.apicorreios.exception.NotReadyException;
import br.com.livino.apicorreios.model.Address;
import br.com.livino.apicorreios.service.CorreiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CorreiosController {
    @Autowired
    private CorreiosService service;

    @GetMapping("status")
    public String get() {
        return "Correios Service is " + service.getStatus();
    }

    @GetMapping("zip/{zipcode}")
    public Address getByZipcode(
            @PathVariable("zipcode") String zipcode) throws NotReadyException, NoContentException {
        return this.service.getByZipcode(zipcode);
    }
}