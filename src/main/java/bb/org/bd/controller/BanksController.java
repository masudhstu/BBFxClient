package bb.org.bd.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import bb.org.bd.model.Adscode;
import bb.org.bd.model.Bankcode;
import bb.org.bd.xmlmanager.XmlFileWritter;

@RestController
public class BanksController {

	private static final Logger log = Logger.getLogger(BanksController.class);
	// private static final String REST_SERVICE_URI = "http://localhost:8080/fxserver/ws-adscodes/";
	private static final String REST_SERVICE_URI = "http://10.11.100.50:8080/BBFxService/ws-bankswithbranches/";

	@RequestMapping(value = "/bankswithbranch")
	public ModelAndView listContact(ModelAndView model) throws IOException {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		// ResponseEntity<List<Adscode>> response =
		// exchange("http://rest.com/person", HttpMethod.GET, HttpEntity.EMPTY,
		// new ParameterizedTypeReference<List<Adscode>>() {});
		ParameterizedTypeReference<List<Bankcode>> responseType = new ParameterizedTypeReference<List<Bankcode>>() {
		};

		ResponseEntity<List<Bankcode>> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.GET, entity,
				responseType, (Object) "pwebb");

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
			if (bankcode.getAdscodeList() != null) {
				log.info("There are Branches");
				for (Adscode adscode : bankcode.getAdscodeList()) {
					log.debug(adscode.toString());
				}

			} else {
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

		String URL = REST_SERVICE_URI + bankCode;
		log.debug(URL);

		RestTemplate restTemplate = new RestTemplate();
		Bankcode oBankcode = restTemplate.getForObject(URL, Bankcode.class);

		log.debug(oBankcode);
		/*
		 * if (oBankcode.getAdscodeList() != null) for (Adscode adscode :
		 * oBankcode.getAdscodeList()) { log.debug(adscode); }
		 */

		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(oBankcode);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(json);
		log.debug(json);

		// https://github.com/FasterXML/jackson-dataformat-xml
		System.out.println("printing XML");
		// Important: create XmlMapper; it will use proper factories,
		// workarounds
		ObjectMapper xmlMapper = new XmlMapper();
		try {
			String xml = xmlMapper.writeValueAsString(oBankcode);
			// System.out.println(xml);
			log.debug(xml);

			System.out.println("printing XML File");
			//writeXMLFile(xml);			
			// writeFile(xml);
			
			log.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { xmlMapper.writeValue(new File("/tmp/stuff.xml"), oBankcode); }
		 * catch (JsonGenerationException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (JsonMappingException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		// or
		/*
		 * try { xmlMapper.writeValue(new File("/tmp/stuff.json"), oBankcode); }
		 * catch (JsonGenerationException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (JsonMappingException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		ModelAndView model = new ModelAndView("home");
		// model.addObject("bankcode", oBankcode);

		return model;
	}

	public void writeFile(String yourXML) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("outfilename.xml"));
			out.write(yourXML);

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void writeXMLFile(String xml) {
		try {

			File file = new File("/u01/fxJava/xmls/filename.xml");

			// if file doesnt exists, then create it
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(xml);
			bw.close();

			System.out.println(file.getAbsolutePath() + "Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
