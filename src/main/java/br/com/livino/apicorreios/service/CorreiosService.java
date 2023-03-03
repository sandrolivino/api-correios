package br.com.livino.apicorreios.service;

import br.com.livino.apicorreios.enums.Status;
import br.com.livino.apicorreios.exception.NoContentException;
import br.com.livino.apicorreios.exception.NotReadyException;
import br.com.livino.apicorreios.model.Address;
import br.com.livino.apicorreios.model.AddressStatus;
import br.com.livino.apicorreios.repository.AddressRepository;
import br.com.livino.apicorreios.repository.AddressStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorreiosService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressStatusRepository addressStatusRepository;
    public Status getStatus(){

        return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getAddressByZipcode(String zipcode) throws NotReadyException, NoContentException {
        if (!this.getStatus().equals(Status.READY))
            throw new NotReadyException();

        return addressRepository.findById(zipcode).orElseThrow(NoContentException::new);
    }

    public void setup(){

    }
}
