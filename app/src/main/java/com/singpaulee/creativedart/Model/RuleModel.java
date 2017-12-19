package com.singpaulee.creativedart.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 16/12/2017.
 */

public class RuleModel {
	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("nama_depan")
	@Expose
	private String nama_depan;

	@SerializedName("nama_belakang")
	@Expose
	private String nama_belakang;

	@SerializedName("username")
	@Expose
	private String username;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("password")
	@Expose
	private String password;

	@SerializedName("status")
	@Expose
	private String status;

	@SerializedName("foto_profil")
	@Expose
	private String url_photo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNama_depan() {
		return nama_depan;
	}

	public void setNama_depan(String nama_depan) {
		this.nama_depan = nama_depan;
	}

	public String getNama_belakang() {
		return nama_belakang;
	}

	public void setNama_belakang(String nama_belakang) {
		this.nama_belakang = nama_belakang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl_photo() {
		return url_photo;
	}

	public void setUrl_photo(String url_photo) {
		this.url_photo = url_photo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
