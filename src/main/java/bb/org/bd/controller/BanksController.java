package bb.org.bd.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import bb.org.bd.model.Adscode;
import bb.org.bd.model.Bankcode;
import bb.org.bd.model.User;

@RestController
public class BanksController {

	private static final Logger log = Logger.getLogger(BanksController.class);

	@RequestMapping(value = "/bankswithbranch")
	public ModelAndView listContact(ModelAndView model) throws IOException {

		final String uri = "http://localhost:8080/fxserver/ws-bankswithbranches";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		// ResponseEntity<List<Adscode>> response =
		// exchange("http://rest.com/person", HttpMethod.GET, HttpEntity.EMPTY,
		// new ParameterizedTypeReference<List<Adscode>>() {});
		ParameterizedTypeReference<List<Bankcode>> responseType = new ParameterizedTypeReference<List<Bankcode>>() {
		};

		ResponseEntity<List<Bankcode>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType,
				(Object) "pwebb");

		// RestTemplate restTemplate = new RestTemplate();
		/*
		 * ResponseEntity<List<Adscode>> esponse =
		 * restTemplate.exchange("https://bitpay.com/api/rates", HttpMethod.GET,
		 * null, new ParameterizedTypeReference<List<Adscode>>() { });
		 */
		List<Bankcode> listBankcode = response.getBody();
		MediaType contentType = response.getHeaders().getContentType();
		// log.info("MediaType: " + contentType);
		HttpStatus statusCode = response.getStatusCode();
		// log.warn("HttpStatus: " + statusCode);

		/* List<Adscode> listContact = adscodeService.findAllAdscode(); */

		if (listBankcode == null)
			log.warn("No Bank Information is found");

		for (Bankcode bankcode : listBankcode) {
			log.debug(bankcode);
			if (bankcode.getAdscodeList() != null){
				log.info("There are Branches");
				for (Adscode adscode : bankcode.getAdscodeList()) {
					log.debug(adscode.toString());
				}
			
		}else {
			log.info("There is no Branches for :" + bankcode.getBankCode());
		}
		}

		// model.addObject("listAdscode", listAdscode);
		model.setViewName("home");

		return model;
	}

	@RequestMapping(value = "/bankswithbranch/{bankCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getBankWithBranches(@PathVariable("bankCode") String bankCode) {
		System.out.println("Fetching User with id " + bankCode);

		String URL = "http://localhost:8080/fxserver/ws-bankswithbranches/" + bankCode;
		log.debug(URL);

		RestTemplate restTemplate = new RestTemplate();
		Bankcode oBankcode = restTemplate.getForObject(URL, Bankcode.class);

		log.debug(oBankcode);
		if (oBankcode.getAdscodeList() != null)
			for (Adscode adscode : oBankcode.getAdscodeList()) {
				log.debug(adscode);
			}

		ModelAndView model = new ModelAndView("home");
		// model.addObject("bankcode", oBankcode);

		return model;
	}

}
