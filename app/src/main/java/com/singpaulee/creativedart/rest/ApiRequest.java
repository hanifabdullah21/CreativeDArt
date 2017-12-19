package com.singpaulee.creativedart.rest;

import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.singpaulee.creativedart.Model.GaleriResponseModel;
import com.singpaulee.creativedart.Model.ResponseModel;
import com.singpaulee.creativedart.Model.Result;
import com.singpaulee.creativedart.Model.ResultGaleri;
import com.singpaulee.creativedart.Model.RuleModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ASUS on 13/12/2017.
 */

public interface ApiRequest {

	@FormUrlEncoded
	@POST("register.php")
	Call<ResponseModel> registerUser(
									@Field("id") String id,
									@Field("nama_depan") String nama_depan,
									@Field("nama_belakang") String nama_belakang,
									@Field("username") String username,
									@Field("email") String email,
									@Field("jenis_kelamin") String jenis_kelamin,
									@Field("password") String password,
									@Field("status") String status);

	@FormUrlEncoded
	@POST("update_profil.php")
	Call<ResponseModel> updateProfil(
			@Field("id") String id,
			@Field("username") String username,
			@Field("email") String email,
			@Field("password") String password,
			@Field("foto_profil") String foto_profil);

	@GET("login.php")
	Call<ArrayList<RuleModel>> getRuleLogin();

	@GET("getseni.php")
	Call<ArrayList<GaleriDataModel>> getGaleri();

	@Multipart
	@POST("upload_photo.php")
	Call<Result> postIMmage(@Part MultipartBody.Part image);

	@FormUrlEncoded
	@POST("add_item.php")
	Call<GaleriResponseModel> addItem(
			@Field("kode") String kode,
			@Field("id_seniman") String id_seniman,
			@Field("nama_seni") String nama_seni,
			@Field("nama_seniman") String nama_seniman,
			@Field("deskripsi") String deskripsi,
			@Field("url_photo") String url_photo,
			@Field("kategori") String kategori,
			@Field("harga_seni") int harga_seni);

	@Multipart
	@POST("galeriseni.php")
	Call<ResultGaleri> postImage(@Part MultipartBody.Part image);
}
