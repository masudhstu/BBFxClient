package bb.org.bd.model.exp;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class EXP_INFO {
	
	private List<Exp_Detail> exp_detail;

	@JacksonXmlElementWrapper(useWrapping = false) //to not adding this as XML tag
	public List<Exp_Detail> getExp_detail() {
		return exp_detail;
	}

	public void setExp_detail(List<Exp_Detail> exp_detail) {
		this.exp_detail = exp_detail;
	}
}
