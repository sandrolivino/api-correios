package br.com.livino.apicorreios.repository;

import br.com.livino.apicorreios.model.Address;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SetupRepository {

    private String url = "https://raw.githubusercontent.com/miltonhit/miltonhit/main/public-assets/cep-20190602.csv";
    public List<Address> getFromOrigin() throws Exception{
        List<Address> result = new ArrayList<>();
        String resultStr = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(new HttpGet(this.url))){
            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity);
        }

        for (String current : resultStr.split("\n")) {
            String[] currentSplited = current.split(",");

            if (currentSplited[0].length() > 2) // breaks the header line, if exists
                continue;

            result.add(Address.builder()
                    .state(currentSplited[0])
                    .city(currentSplited[1])
                    .district(currentSplited[2])
                    .zipCode(StringUtils.leftPad(currentSplited[3], 8, "0"))
                    .street(currentSplited.length > 4 ? currentSplited[4] : null).build());
        }

        return result;
    }
}
