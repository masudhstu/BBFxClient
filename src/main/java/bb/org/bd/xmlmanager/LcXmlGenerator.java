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

import bb.org.bd.constants.Constants;
import bb.org.bd.model.lc.LC;
import bb.org.bd.model.lc.LC_INFO;
import bb.org.bd.utils.DateTimeManager;
import bb.org.bd.utils.JsonNameManager;

public class LcXmlGenerator {

	private static final Logger logger = Logger.getLogger(LcXmlGenerator.class);
	private static final String REST_SERVICE_URI = Constants.REST_SERVICE_ROOT + "ws-lcFromBB";
	private RestTemplate restTemplate = null;

	public boolean generateLCxmlForHolidays() {

		try {
			String yesterday = DateTimeManager.getYesterdayDateString();
			// holiday checking

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.set("queryType", "2");
			requestHeaders.set("date", yesterday);
			requestHeaders.set("timeFrom", "");
			requestHeaders.set("timeTo", "");
			requestHeaders.set("lc", "");
			HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

			restTemplate = new RestTemplate();

			logger.debug("Service URL: " + REST_SERVICE_URI);
			logger.info("Calling Service for data");
			HttpEntity<LC> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.GET, requestEntity, LC.class);
			logger.debug("Got data");

			LC fullLC = response.getBody();

			int numberOfFile = 0;
			String xml = "", fileName = "";
			List<LC> listLC = devideLC(fullLC);

			for (LC oLC : listLC) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = lcPojoToXmlString(oLC);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = "LC_DATA_" + DateTimeManager.getYesterdayDateString("yyyyMMdd") + "_" + numberOfFile + "_Of_"
						+ listLC.size();
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
				fileNameStarts = "LC_DATA_" + DateTimeManager.getYesterdayDateString("yyyyMMdd") + "_16-30_to_24-00_";
			} else {
				date = DateTimeManager.getToday("yyyy-MM-dd");
				fileNameStarts = "LC_DATA_" + DateTimeManager.getToday("yyyyMMdd");

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
			HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", requestHeaders);

			restTemplate = new RestTemplate();

			logger.debug("Service URL: " + REST_SERVICE_URI);
			logger.info("Calling Service for data");
			HttpEntity<LC> response = restTemplate.exchange(REST_SERVICE_URI, HttpMethod.GET, requestEntity, LC.class);
			logger.debug("Got data");

			LC fullLC = response.getBody();
			printLcFiles(fullLC, fileNameStarts);

			/*int numberOfFile = 0;
			String xml = "";
			String fileName = "";
			List<LC> listLC = devideLC(fullLC);

			for (LC oLC : listLC) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = lcPojoToXmlString(oLC);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = fileNameStarts + numberOfFile + "_Of_" + listLC.size();
				logger.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml, fileName));
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		return true;
	}

	public boolean printLcFiles(LC fullLC, String fileNameStarts) {
		
		int numberOfFile = 0;
		String xml = "";
		String fileName = "";
		List<LC> listLC = devideLC(fullLC);

		try {
			for (LC oLC : listLC) {
				numberOfFile++;
				logger.info(numberOfFile + "th XML File is being Generated: ");
				xml = lcPojoToXmlString(oLC);
				// logger.info("XML File Generated: " +
				// XmlFileWritter.writeXMLFile(xml));
				fileName = fileNameStarts + numberOfFile + "_Of_" + listLC.size();
				logger.info("XML File Generated: " + XmlFileWritter.writeXMLFile(xml, fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Exception: ", e);
			return false;
		}

		return true;
	}

	private List<LC> devideLC(LC oLC) {
		logger.debug("Deviding LC");
		List<LC> listLc = new ArrayList<LC>();
		LC partLC = null;
		int max = 0;

		try {

			List<LC_INFO> listLcInfo = oLC.getLc_info();
			int sizeOfLcInfo = listLcInfo.size();
			logger.debug("Total LC INFO: " + sizeOfLcInfo);

			for (int i = 0; i <= sizeOfLcInfo; i = i + 1000) {
				if (i + 1000 < sizeOfLcInfo)
					max = i + 1000;
				else
					max = sizeOfLcInfo;

				partLC = new LC();
				partLC.setLc_info(listLcInfo.subList(i, max));
				listLc.add(partLC);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("Exception: ", e);
		}

		return listLc;
	}

	// Json to POJO to XML
	private String lcPojoToXmlString(LC oLC) {

		// https://github.com/FasterXML/jackson-dataformat-xml
		logger.debug("Converting LC to  XML");
		// Important: create XmlMapper; it will use proper factories,
		// workarounds
		ObjectMapper xmlMapper = new XmlMapper();
		// field name
		xmlMapper.setPropertyNamingStrategy(new JsonNameManager());
		String xml = "";

		try {
			xml = xmlMapper.writeValueAsString(oLC);

			logger.debug("xml generated");
			// logger.trace("xml" + xml);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml;
	}

}
