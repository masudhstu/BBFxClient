package bb.org.bd.model.lc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class LC_DETAIL implements Serializable {
	
	//http://stackoverflow.com/questions/27143936/serialize-java-list-to-xml-using-jackson-xml-mapper
	@JacksonXmlElementWrapper(useWrapping = false) 
	private List<LC_COMMODITY> lc_commodity;

	public List<LC_COMMODITY> getLc_commodity() {
		if(lc_commodity == null)
			lc_commodity = new ArrayList<LC_COMMODITY>();
		return lc_commodity;
	}

	public void setLc_commodity(List<LC_COMMODITY> lc_commodity) {
		this.lc_commodity = lc_commodity;
	}
	
}
