package bb.org.bd.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import bb.org.bd.constants.Constants;
import bb.org.bd.model.HeaderParams;
import bb.org.bd.model.exp.EXP_INFO;
import bb.org.bd.model.lc.LC;
import bb.org.bd.utils.DateTimeManager;
import bb.org.bd.xmlmanager.ExpXmlGenerator;
import bb.org.bd.xmlmanager.LcXmlGenerator;

@RestController
public class CustomsController {

	private static final Logger logger = Logger.getLogger(CustomsController.class);
	// private static final String REST_SERVICE_URI =
	// "http://localhost:8080/fxserver/ws-lcFromBB";
	// private static final String REST_SERVICE_URI =
	// "http://localhost:8080/fxserver/ws-expFromBB";

	// private static final String REST_SERVICE_URI =
	// "http://10.11.100.50:8080/BBFxService/ws-bankswithbranches/";
	
	//Doing something for all model
	@ModelAttribute
    public void addingCommonObjects(Model model1) {
		
		model1.addAttribute("headerMessage", "Welcome to khatakhati");
	}
	
	//some works need to be done before calling original request
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		//binder.setDisallowedFields(new String[] {"studentMobile"});
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
		//binder.registerCustomEditor(Date.class,"studentDOB",new CustomDateEditor(dateFormat, false));
		
		//binder.registerCustomEditor(String.class, "studentHobby", new StudentNameEditor());
	}

	@RequestMapping(value = "/lcFromBB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getLCs() {
		//System.out.println("LCS ");
		
		

		String URL = "http://localhost:8080/fxclient/ws-lcFromBB";
		logger.debug(URL);

		RestTemplate restTemplate = new RestTemplate();

		/*
		 * LC_INFO oLC_INFO = restTemplate.getForObject(URL, LC_INFO.class);
		 * log.debug(oLC_INFO);
		 */

		// LC oLC = restTemplate.getForObject(URL, LC.class);
		// log.debug(oLC);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("queryType", "1");
		requestHeaders.set("date", DateTimeManager.getYesterdayDateString("yyyy-MM-dd"));
		requestHeaders.set("timeFrom", "13:30:01");
		requestHeaders.set("timeTo", "16:30:00");
		requestHeaders.set("lc", "80716010733");
		//requestHeaders.set("lc", "1234"); // wrong LC
		requestHeaders.set("exp", "000000020009452016");

		HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

		try {
			HttpEntity<LC> response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, LC.class);
			
			LC oLC = response.getBody();

			HttpHeaders headerFromWs = response.getHeaders();
			logger.debug("log_id: " + headerFromWs.get("log_id"));

			if (oLC == null) {
				logger.warn("No LC received");
			} else {
				LcXmlGenerator oLcXmlGenerator = new LcXmlGenerator();
				oLcXmlGenerator.printLcFiles(oLC, "LC_DATA_");
				//oLcXmlGenerator.generateLCxmlForEveryday();
			}
		} catch (RestClientException e) {
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		// Json to POJO
		/*
		 * ObjectMapper mapper = new ObjectMapper(); String json = ""; try {
		 * json = mapper.writeValueAsString(oLC); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println(json); log.debug("json" +
		 * json);
		 */

		/*
		 * //Json to POJO to XML //
		 * https://github.com/FasterXML/jackson-dataformat-xml
		 * System.out.println("printing XML"); // Important: create XmlMapper;
		 * it will use proper factories, // workarounds ObjectMapper xmlMapper =
		 * new XmlMapper(); //field name xmlMapper.setPropertyNamingStrategy(new
		 * JsonNameManager());
		 * 
		 * try { String xml = xmlMapper.writeValueAsString(oLC); //
		 * System.out.println(xml); logger.debug("xml" + xml);
		 * 
		 * System.out.println("printing XML File"); // writeXMLFile(xml); //
		 * writeFile(xml);
		 * 
		 * logger.info("XML File Generated: " +
		 * XmlFileWritter.writeXMLFile(xml));
		 * 
		 * } catch (JsonProcessingException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

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

	@RequestMapping(value = "/expFromBB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getEXPs() {

		String URL = "http://localhost:8080/fxclient/ws-expFromBB";
		logger.debug(URL);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("queryType", "1");
		requestHeaders.set("date", DateTimeManager.getYesterdayDateString("yyyy-MM-dd"));
		requestHeaders.set("timeFrom", "13:30:01");
		requestHeaders.set("timeTo", "16:30:00");
		requestHeaders.set("lc", "0000133512010758");
		requestHeaders.set("exp", "000000020009452016");

		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

		try {
			HttpEntity<EXP_INFO> response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, EXP_INFO.class);
			EXP_INFO oExp_Info = response.getBody();

			if(oExp_Info == null)
				logger.warn("No Exp received");
			else
			{
				ExpXmlGenerator oExpXmlGenerator = new ExpXmlGenerator();
				oExpXmlGenerator.printExpFiles(oExp_Info, "EXP_DATA_");				
			}			
			
		} catch (RestClientException e) {
			e.printStackTrace();
			logger.warn("Exception:", e);
		}

		ModelAndView model = new ModelAndView("home");
		// model.addObject("bankcode", oBankcode);

		return model;
	}

	@RequestMapping(value = "/ws-lcFromBB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LC> getLCs(HttpServletRequest request) {

		String URL = Constants.REST_SERVICE_ROOT + "ws-lcFromBB";
		logger.debug(URL);

		RestTemplate restTemplate = new RestTemplate();

		// LC oLC = restTemplate.getForObject(URL, LC.class);
		// log.debug(oLC);

		String dataType = "1"; // 1 means LC, 2 Means Exp
		String queryType = "", date = "", timeFrom = "", timeTo = "", lc = "", exp = "";
		HeaderParams headers = null;

		try {
			queryType = request.getHeader("queryType");
			date = request.getHeader("date");
			timeFrom = request.getHeader("timeFrom");
			timeTo = request.getHeader("timeTo");
			lc = request.getHeader("lc");
			exp = request.getHeader("exp");

			headers = new HeaderParams(dataType, queryType, date, timeFrom, timeTo, lc, exp);
			logger.info("headers: " + headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Header reading problem: ", e);
		}

		String validationMessage = headers.validate();
		logger.info("validationMessage: " + validationMessage);
		if (validationMessage.equals("OK")) {
			HttpHeaders requestHeaders = new HttpHeaders();
			// requestHeaders.set("date", "01-OCT-2016");
			requestHeaders.set("queryType", queryType);
			requestHeaders.set("date", date);
			requestHeaders.set("timeFrom", timeFrom);
			requestHeaders.set("timeTo", timeTo);
			requestHeaders.set("lc", lc);

			HttpEntity<?> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

			ResponseEntity<LC> response = null;
			HttpStatus statusCode = null;
			String log_id = null;
			
			try {
				// HttpEntity<LC> response = restTemplate.exchange(URL,
				// HttpMethod.GET, requestEntity, LC.class);
				response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, LC.class);
				statusCode = response.getStatusCode();
				log_id = response.getHeaders().get("log_id").toString();
				
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				logger.warn("RestClientException: ", e);
				statusCode = e.getStatusCode();
			}
			
			logger.debug("StatusCode: " + statusCode);			
			logger.debug("log_id: " + log_id);

			// HttpHeaders headerFromWs = response.getHeaders();
			// headerFromWs.get("");

			// adding additional headers
			// HttpHeaders responseHeaders = new HttpHeaders();
			// responseHeaders.add("Content-Type", "application/json;
			// charset=UTF-8");
			// responseHeaders.add("X-Fsl-Location", "/");
			// responseHeaders.add("X-Fsl-Response-Code", "302");
			// responseHeaders.add("message", validationMessage);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("log_id", log_id);

			if (statusCode != HttpStatus.OK) {
				logger.debug("returning bad response");
				return new ResponseEntity<LC>(responseHeaders, statusCode);
			} else {
				logger.debug("returning good response");
				return new ResponseEntity<LC>(response.getBody(), responseHeaders, HttpStatus.OK);
			}

			// return new ResponseEntity<LC>(oLC, HttpStatus.OK);
			// return new ResponseEntity<LC>(oLC, responseHeaders,
			// HttpStatus.OK);
			//return new ResponseEntity<LC>(HttpStatus.BAD_REQUEST);

		}

		else {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("message", validationMessage);
			return new ResponseEntity<LC>(responseHeaders, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/ws-expFromBB", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EXP_INFO> getEXPs(HttpServletRequest request) {

		String URL = Constants.REST_SERVICE_ROOT + "ws-expFromBB";
		logger.debug("REST_SERVICE_URI: " + URL);

		RestTemplate restTemplate = new RestTemplate();

		String dataType = "2"; // 1 means LC, 2 Means Exp
		String queryType = "", date = "", timeFrom = "", timeTo = "", lc = "", exp = "";
		HeaderParams headers = null;

		try {
			queryType = request.getHeader("queryType");
			date = request.getHeader("date");
			timeFrom = request.getHeader("timeFrom");
			timeTo = request.getHeader("timeTo");
			lc = request.getHeader("lc");
			exp = request.getHeader("exp");

			headers = new HeaderParams(dataType, queryType, date, timeFrom, timeTo, lc, exp);
			logger.info("headers: " + headers);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Header reading problem: ", e);
		}

		String validationMessage = headers.validate();
		logger.info("validationMessage: " + validationMessage);

		if (validationMessage.equals("OK")) {
			HttpHeaders requestHeaders = new HttpHeaders();
			// requestHeaders.set("date", "01-OCT-2016");
			requestHeaders.set("queryType", queryType);
			requestHeaders.set("date", date);
			requestHeaders.set("timeFrom", timeFrom);
			requestHeaders.set("timeTo", timeTo);
			requestHeaders.set("lc", lc);
			requestHeaders.set("exp", exp);

			HttpEntity<?> requestEntity = new HttpEntity<String>("parameters", requestHeaders);
			/*HttpEntity<Exp_Info> response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, Exp_Info.class);

			Exp_Info oExp_Info = response.getBody();
			logger.debug("log_id: " + response.getHeaders().get("log_id"));*/
			
			ResponseEntity<EXP_INFO> response = null;
			HttpStatus statusCode = null;
			String log_id = null;
			
			try {
				response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, EXP_INFO.class);
				statusCode = response.getStatusCode();
				log_id = response.getHeaders().get("log_id").toString();
				
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				logger.warn("RestClientException: ", e);
				statusCode = e.getStatusCode();
			}
			
			logger.debug("StatusCode: " + statusCode);			
			logger.debug("log_id: " + log_id);

			// adding additional headers
			//HttpHeaders responseHeaders = new HttpHeaders();
			// responseHeaders.add("Content-Type", "application/json;
			// charset=UTF-8");
			// responseHeaders.add("X-Fsl-Location", "/");
			// responseHeaders.add("X-Fsl-Response-Code", "302");
			//responseHeaders.add("message", validationMessage);
			//responseHeaders.add("log_id", response.getHeaders().get("log_id").toString());

			// return new ResponseEntity<LC>(oLC, HttpStatus.OK);
			//return new ResponseEntity<Exp_Info>(oExp_Info, responseHeaders, HttpStatus.OK);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("log_id", log_id);

			if (statusCode != HttpStatus.OK) {
				logger.debug("returning bad response");
				return new ResponseEntity<EXP_INFO>(responseHeaders, statusCode);
			} else {
				logger.debug("returning good response");
				return new ResponseEntity<EXP_INFO>(response.getBody(), responseHeaders, HttpStatus.OK);
			}

		}

		else {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("message", validationMessage);
			return new ResponseEntity<EXP_INFO>(responseHeaders, HttpStatus.BAD_REQUEST);
		}

	}
}
