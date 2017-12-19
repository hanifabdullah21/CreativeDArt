package com.singpaulee.creativedart.Model;

import java.util.List;

/**
 * Created by ASUS on 13/12/2017.
 */

public class ResponseModel {

	String kode,pesan;
	List<DataModel> user;

	public List<DataModel> getUser() {
		return user;
	}

	public void setUser(List<DataModel> user) {
		this.user = user;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getPesan() {
		return pesan;
	}

	public void setPesan(String pesan) {
		this.pesan = pesan;
	}
}
