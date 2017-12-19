package com.singpaulee.creativedart.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 16/12/2017.
 */

public class GaleriDataModel implements Parcelable {
	@SerializedName("kode")
	@Expose
	private String kode;
	@SerializedName("id_seniman")
	@Expose
	private String idSeniman;
	@SerializedName("nama_seniman")
	@Expose
	private String namaSeniman;

	@SerializedName("nama_seni")
	@Expose
	private String namaSeni;

	@SerializedName("harga_seni")
	@Expose
	private Integer hargaSeni;

	@SerializedName("deskripsi")
	@Expose
	private String deskripsi;

	@SerializedName("url_photo")
	@Expose
	private String urlPhoto;

	@SerializedName("kategori")
	@Expose
	private String kategori;

	public GaleriDataModel() {
	}

	protected GaleriDataModel(Parcel in) {
		kode = in.readString();
		idSeniman = in.readString();
		namaSeniman = in.readString();
		namaSeni = in.readString();
		if (in.readByte() == 0) {
			hargaSeni = null;
		} else {
			hargaSeni = in.readInt();
		}
		deskripsi = in.readString();
		urlPhoto = in.readString();
		kategori = in.readString();
	}

	public static final Creator<GaleriDataModel> CREATOR = new Creator<GaleriDataModel>() {
		@Override
		public GaleriDataModel createFromParcel(Parcel in) {
			return new GaleriDataModel(in);
		}

		@Override
		public GaleriDataModel[] newArray(int size) {
			return new GaleriDataModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(kode);
		parcel.writeString(idSeniman);
		parcel.writeString(namaSeniman);
		parcel.writeString(namaSeni);
		if (hargaSeni == null) {
			parcel.writeByte((byte) 0);
		} else {
			parcel.writeByte((byte) 1);
			parcel.writeInt(hargaSeni);
		}
		parcel.writeString(deskripsi);
		parcel.writeString(urlPhoto);
		parcel.writeString(kategori);
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getIdSeniman() {
		return idSeniman;
	}

	public void setIdSeniman(String idSeniman) {
		this.idSeniman = idSeniman;
	}

	public String getNamaSeniman() {
		return namaSeniman;
	}

	public void setNamaSeniman(String namaSeniman) {
		this.namaSeniman = namaSeniman;
	}

	public String getNamaSeni() {
		return namaSeni;
	}

	public void setNamaSeni(String namaSeni) {
		this.namaSeni = namaSeni;
	}

	public Integer getHargaSeni() {
		return hargaSeni;
	}

	public void setHargaSeni(Integer hargaSeni) {
		this.hargaSeni = hargaSeni;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public static Creator<GaleriDataModel> getCREATOR() {
		return CREATOR;
	}
}
