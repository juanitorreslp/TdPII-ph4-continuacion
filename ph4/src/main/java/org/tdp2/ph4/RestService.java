package org.tdp2.ph4;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Bean
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

  

	public String getValuesProcessing() {
		String url = "http://localhost:5204";
        return this.restTemplate.getForObject(url, String.class);
	}


    
}
