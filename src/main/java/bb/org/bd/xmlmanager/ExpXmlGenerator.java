package bb.org.bd.xmlmanager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import bb.org.bd.model.exp.Exp_Detail;
import bb.org.bd.constants.Constants;
import bb.org.bd.model.exp.EXP_INFO;
import bb.org.bd.utils.DateTimeManager;
import bb.org.bd.utils.JsonNameManager;

public class ExpXmlGenerator {
	private static final Logger logger = Logger.getLogger(ExpXmlGenerator.class);
	private static final String REST_SERVICE_URI = Constants.REST_SERVICE_ROOT + "ws-expFromBB";
	private RestTemplate restTemplate = null;

	public boolean generateExpXmlForHolidays() {

		try {
			String yesterday = DateTimeManager.getYesterdayDateString();
			// holiday checking

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.set("queryType", "2");
			requestHeaders.set("date", yesterday);
			requestHeaders.set("timeFrom", "");
			requestHeaders.set("timeTo", "");
			requestHeaders.set("lc", "");
			requestHeaders.set("exp", "");

			HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

			restTemplate = new RestTemplate();

			logger.debug("Service URL: " + REST_SERVICE_URI);
			logger.info("Calling Service for data");
			HttpEntity<EXP_INFO> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.GET, requestEntity,
					EXP_INFO.class);
			logger.debug("Got data");

			EXP_INFO fullExp_Info = response.getBody();

			int numberOfFile = 0;
			String xml = "", fileName = "";
			List<EXP_INFO> listExp_Info = devideExp(fullExp_Info);

			for (EXP_INFO oExp_Info : listExp_Info) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = expPojoToXmlString(oExp_Info);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = "EXP_DATA_" + DateTimeManager.getYesterdayDateString("yyyyMMdd") + "_" + numberOfFile + "_Of_"
						+ listExp_Info.size();
				logger.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml, fileName));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		return true;
	}

	public boolean generateLCxmlForEveryday() {

		try {
			// holiday checking

			String date = "";
			String timeTo = "";
			String timeFrom = DateTimeManager.getCurrentDateTime("HH:mm:ss");
			String fileNameStarts = "";

			if (timeFrom.startsWith("00:")) {
				timeFrom = "16:30:01";
				timeTo = "23:59:59";
				date = DateTimeManager.getYesterdayDateString("yyyy-MM-dd");
				fileNameStarts = "EXP_DATA_" + DateTimeManager.getYesterdayDateString("yyyyMMdd") + "_16-30_to_24-00_";
			} else {
				date = DateTimeManager.getToday("yyyy-MM-dd");
				fileNameStarts = "EXP_DATA_" + DateTimeManager.getToday("yyyyMMdd");

				if (timeFrom.startsWith("13:")) {
					timeFrom = "00:00:00";
					timeTo = "13:30:00";
					fileNameStarts = fileNameStarts + "_00-00_to_13-30_";
				} else {
					timeFrom = "13:30:01";
					timeTo = "16:30:00";
					fileNameStarts = fileNameStarts + "_13-30_to_16-30_";
				}
			}

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.set("queryType", "3");
			requestHeaders.set("date", date);
			requestHeaders.set("timeFrom", timeFrom);
			requestHeaders.set("timeTo", timeTo);
			requestHeaders.set("lc", "0000013216010687");
			requestHeaders.set("exp", "0000013216010687");
			HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

			restTemplate = new RestTemplate();

			logger.debug("Service URL: " + REST_SERVICE_URI);
			logger.info("Calling Service for data");
			HttpEntity<EXP_INFO> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.GET, requestEntity,
					EXP_INFO.class);
			logger.debug("Got data");

			EXP_INFO fullExp_Info = response.getBody();
			printExpFiles(fullExp_Info, fileNameStarts);
			
			/*int numberOfFile = 0;
			String xml = "";
			String fileName = "";
			List<EXP_INFO> listExp_Info = devideExp(fullExp_Info);

			for (EXP_INFO oExp_Info : listExp_Info) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = expPojoToXmlString(oExp_Info);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = fileNameStarts + numberOfFile + "_Of_" + listExp_Info.size();
				logger.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml, fileName));
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		return true;
	}

	public boolean printExpFiles(EXP_INFO fullExp_Info, String fileNameStarts) {

		int numberOfFile = 0;
		String xml = "";
		String fileName = "";
		List<EXP_INFO> listExp_Info = devideExp(fullExp_Info);

		try {
			for (EXP_INFO oExp_Info : listExp_Info) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = expPojoToXmlString(oExp_Info);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = fileNameStarts + numberOfFile + "_Of_" + listExp_Info.size();
				logger.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml, fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Exception: ", e);
			return false;
		}

		return true;
	}

	private List<EXP_INFO> devideExp(EXP_INFO oExp_Info) {
		logger.debug("Deviding EXP");
		List<EXP_INFO> listExp_Info = new ArrayList<EXP_INFO>();
		EXP_INFO partExp_Info = null;
		int max = 0;

		try {

			List<Exp_Detail> listExpDetail = oExp_Info.getExp_detail();
			int sizeOfExpDetail = listExpDetail.size();
			logger.debug("Total LC INFO: " + sizeOfExpDetail);

			for (int i = 0; i <= sizeOfExpDetail; i = i + 1000) {
				if (i + 1000 < sizeOfExpDetail)
					max = i + 1000;
				else
					max = sizeOfExpDetail;

				partExp_Info = new EXP_INFO();
				partExp_Info.setExp_detail(listExpDetail.subList(i, max));
				listExp_Info.add(partExp_Info);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		return listExp_Info;
	}

	// Json to POJO to XML
	private String expPojoToXmlString(EXP_INFO oExp_Info) {

		// https://github.com/FasterXML/jackson-dataformat-xml
		logger.debug("Converting EXP to  XML");
		// Important: create XmlMapper; it will use proper factories,
		// workarounds
		ObjectMapper xmlMapper = new XmlMapper();
		// field name
		xmlMapper.setPropertyNamingStrategy(new JsonNameManager());
		String xml = "";

		try {
			xml = xmlMapper.writeValueAsString(oExp_Info);

			logger.debug("xml generated");
			// logger.trace("xml" + xml);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml;
	}

}
