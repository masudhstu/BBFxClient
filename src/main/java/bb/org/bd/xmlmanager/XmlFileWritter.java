package bb.org.bd.xmlmanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class XmlFileWritter {

	private static final Logger log = Logger.getLogger(XmlFileWritter.class);

	
	public static boolean writeXMLFile(String xml) {
		try {

			log.debug("xml in XmlGenerator" + xml);
			File file = new File("/u01/fxJava/xmls/filename.xml");

			// if file doesnt exists, then create it
			if (!file.getParentFile().exists()) {
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

			System.out.println(file.getAbsolutePath() + " writting Done");
			log.debug(file.getAbsolutePath() + " writting Done");

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean writeXMLFile(String xml, String Name) {
		try {

			log.debug("xml in XmlGenerator" + xml);
			File file = new File("/u01/fxJava/xmls/" + Name + ".xml");

			// if file doesnt exists, then create it
			if (!file.getParentFile().exists()) {
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

			System.out.println(file.getAbsolutePath() + " writting Done");
			log.debug(file.getAbsolutePath() + " writting Done");

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
