package es.uco.ism.system796.business.dto;

import java.io.Serializable;

public class NFC implements Serializable {

	private String id;

	public NFC(String id) {
		setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String newId) {
		id = newId;
	}

}