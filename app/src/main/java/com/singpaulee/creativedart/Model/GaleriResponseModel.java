package com.singpaulee.creativedart.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 17/12/2017.
 */

public class GaleriResponseModel {
	String kode,pesan;
	ArrayList<GaleriDataModel> galeri;

	public ArrayList<GaleriDataModel> getGaleri() {
		return galeri;
	}

	public void setGaleri(ArrayList<GaleriDataModel> galeri) {
		this.galeri = galeri;
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
