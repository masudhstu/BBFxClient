package bb.org.bd.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import bb.org.bd.model.Adscode;


@Controller
public class ADscodeController {
	
	private static final Logger log = Logger.getLogger(ADscodeController.class);
	private static final String REST_SERVICE_URI = "http://localhost:8080/fxserver/ws-adscodes/";
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/adscodes")
	public ModelAndView listContact(ModelAndView model) throws IOException {
		
		
		//final String uri = "http://localhost:8080/fxserver/ws-adscodes";	     
	    
		//RestTemplate restTemplate = new RestTemplate();
	     
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	     
	    //ResponseEntity<List<Adscode>> response = exchange("http://rest.com/person", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Adscode>>() {});
	    ParameterizedTypeReference<List<Adscode>> responseType =
	            new ParameterizedTypeReference<List<Adscode>>() {   };

	    ResponseEntity<List<Adscode>> response = restTemplate.exchange(
	    		REST_SERVICE_URI, HttpMethod.GET, entity, responseType, (Object) "pwebb");
		
		//RestTemplate restTemplate = new RestTemplate();
		/*ResponseEntity<List<Adscode>> esponse =
		        restTemplate.exchange("https://bitpay.com/api/rates",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Adscode>>() {
		            });*/
		List<Adscode> listAdscode = response.getBody();
		MediaType contentType = response.getHeaders().getContentType();
		log.info("MediaType: " + contentType);
		HttpStatus statusCode = response.getStatusCode();
		log.warn("HttpStatus: " + statusCode);
		
		/*List<Adscode> listContact = adscodeService.findAllAdscode();*/

		if (listAdscode == null)
			log.warn("No Adscode is found");

		model.addObject("listAdscode", listAdscode);
		model.setViewName("home");

		return model;
	}

	@RequestMapping(value = "/editContact", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		
		String adscode = request.getParameter("adscode");
		//RestTemplate restTemplate = new RestTemplate();
		ModelAndView model = null;
		if(adscode != null)
		{
			Adscode oAdscode = restTemplate.getForObject(REST_SERVICE_URI + adscode, Adscode.class);
	        log.info(oAdscode.toString());		
			
	        model = new ModelAndView("ContactForm");
			model.addObject("adscode", oAdscode);
		}
		else
		{
			model = new ModelAndView("newAdscode");
			model.addObject("adscode", new Adscode());
		}

		return model;
	}
	
	//-------------------Create a User--------------------------------------------------------
	
		@RequestMapping(value = "/NewContact", method = RequestMethod.POST)
		public ModelAndView AddAdscode(@ModelAttribute("adscode") Adscode adscode) {
			log.debug("Creating User " + adscode.getAdscode());

			/*if (userService.isUserExist(user)) {
				System.out.println("A User with name " + user.getName() + " already exist");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}*/
			ModelAndView model = new ModelAndView("newAdscode");
			
			if(adscode != null)
			{
				URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"add", adscode, Adscode.class);
		        log.debug("Location : "+uri.toASCIIString());				
	
				model.addObject("adscode", adscode);
			}

			return model;
		}


}
