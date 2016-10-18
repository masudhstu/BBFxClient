package bb.org.bd.model.lc;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class LC {
	
	//http://stackoverflow.com/questions/27143936/serialize-java-list-to-xml-using-jackson-xml-mapper
	@JacksonXmlElementWrapper(useWrapping = false) //to not adding this as XML tag
	private List<LC_INFO> lc_info;

	public List<LC_INFO> getLc_info() {
		return lc_info;
	}

	public void setLc_info(List<LC_INFO> lc_info) {
		this.lc_info = lc_info;
	}
	
}
