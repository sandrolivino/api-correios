package br.com.livino.apicorreios.service;

import br.com.livino.apicorreios.ApiCorreiosApplication;
import br.com.livino.apicorreios.enums.Status;
import br.com.livino.apicorreios.exception.NoContentException;
import br.com.livino.apicorreios.exception.NotReadyException;
import br.com.livino.apicorreios.model.Address;
import br.com.livino.apicorreios.model.AddressStatus;
import br.com.livino.apicorreios.repository.AddressRepository;
import br.com.livino.apicorreios.repository.AddressStatusRepository;
import br.com.livino.apicorreios.repository.SetupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;



@Service
public class CorreiosService {
    private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressStatusRepository statusRepository;

    @Autowired
    private SetupRepository setupRepository;

    public Status getStatus(){

        return this.statusRepository.findById(AddressStatus.DEFAULT_ID)
                .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
                .getStatus();
    }

    public Address getAddressByZipcode(String zipcode) throws NotReadyException, NoContentException {
        if (!this.getStatus().equals(Status.READY))
            throw new NotReadyException();

        return addressRepository.findById(zipcode).orElseThrow(NoContentException::new);
    }

    private void saveStatus(Status status){
        statusRepository.save(AddressStatus.builder().id(AddressStatus.DEFAULT_ID).status(status).build());
    }

    @EventListener(ApplicationStartedEvent.class)
    protected void setupOnStartup(){
        try {
            this.setup();
        }
        catch (Exception exception)
        {
            ApiCorreiosApplication.close(999999);
        }
    }

    public void setup() throws Exception{
        logger.info("---");
        logger.info("---");
        logger.info("--- STARTING SETUP");
        logger.info("--- Please wait... This may take a few minutes");
        logger.info("---");
        logger.info("---");

        if (this.getStatus().equals(Status.NEED_SETUP)){
            this.saveStatus(Status.SETUP_RUNNING);

            try {
                this.addressRepository.saveAll(this.setupRepository.getFromOrigin());
            }
            catch (Exception exception)
            {
                this.saveStatus(Status.NEED_SETUP);
            }
            this.saveStatus(Status.READY);
        }

        logger.info("---");
        logger.info("---");
        logger.info("--- SETUP READY");
        logger.info("---");
        logger.info("---");
    }
}
