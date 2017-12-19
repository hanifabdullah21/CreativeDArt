package com.singpaulee.creativedart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.singpaulee.creativedart.Model.ResponseModel;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

	private ImageView mLogoAtas;
	private ScrollView mSvRegister;
	private EditText mEdtNamaDepan;
	private EditText mEdtNamaBelakang;
	private EditText mEdtUsername;
	private EditText mEdtEmail;
	private RadioGroup mRadio;
	private RadioButton mRbLaki;
	private RadioButton mRbPerempuan;
	private EditText mEdtPassword;
	private EditText mEdtVerifikasiPassword;
	private Button mBtnRegisterDaftar;
	private RadioGroup mRadioStatus;
	private RadioButton mRbUser;
	private RadioButton mRbSeniman;

	String namaDepan, namaBelakang, username,email, jenisKelamin, password, status;
	int radio,radio2;
	RadioButton jk,rb_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();

		mBtnRegisterDaftar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!verifikasiData()){
					return;
				}
				namaDepan = mEdtNamaDepan.getText().toString().trim();
				namaBelakang = mEdtNamaBelakang.getText().toString().trim();
				username = mEdtUsername.getText().toString().trim();
				email = mEdtEmail.getText().toString().trim();
				jenisKelamin = jk.getText().toString().trim();
				password = mEdtPassword.getText().toString();
				status = rb_status.getText().toString().trim();
				postDatabase();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				},2000);
			}
		});
	}

	private boolean verifikasiData() {
		if(mEdtNamaDepan.getText().toString().isEmpty()){
			mEdtNamaDepan.setError("Masukkan Nama");
			mEdtNamaDepan.requestFocus();
			return false;
		}
		if(mEdtNamaBelakang.getText().toString().isEmpty()){
			mEdtNamaBelakang.setError("Masukkan Nama Belakang");
			mEdtNamaBelakang.requestFocus();
			return false;
		}
		if(mEdtUsername.getText().toString().isEmpty()){
			mEdtUsername.setError("Masukkan username");
			mEdtUsername.requestFocus();
			return false;
		}
		if(!mEdtVerifikasiPassword.getText().toString().equals(mEdtPassword.getText().toString())){
			mEdtVerifikasiPassword.setError("Verifiaksi Password salah");
			return false;
		}else if(mEdtPassword.getText().toString().isEmpty()){
			mEdtPassword.setError("Masukkan password");
			mEdtPassword.requestFocus();
			return false;
		}
		if(mEdtEmail.getText().toString().isEmpty()){
			mEdtEmail.setError("Masukkan email");
			mEdtEmail.requestFocus();
			return false;
		}
		radio = mRadio.getCheckedRadioButtonId();
		jk = (RadioButton) findViewById(radio);
		radio2 = mRadioStatus.getCheckedRadioButtonId();
		rb_status = (RadioButton) findViewById(radio2);

		if(mEdtNamaDepan.getText().toString().isEmpty()){
			mEdtNamaDepan.setError("Field ini harus diisi!");
			mEdtNamaDepan.requestFocus();
			return false;
		}
		if(mEdtNamaBelakang.getText().toString().isEmpty()){
			mEdtNamaBelakang.setError("Field ini harus diisi!");
			mEdtNamaBelakang.requestFocus();
			return false;
		}
		if(mEdtUsername.getText().toString().isEmpty()){
			mEdtUsername.setError("Field ini harus diisi!");
			mEdtUsername.requestFocus();
			return false;
		}
		if(radio < 0){
			Toast.makeText(this, "Silahkan masukkan pilihan", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(radio2 < 0){
			Toast.makeText(this, "Silahkan masukkan pilihan", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(mEdtEmail.getText().toString().isEmpty()){
			mEdtEmail.setError("Field ini harus diisi!");
			mEdtEmail.requestFocus();
			return false;
		}
		if(mEdtPassword.getText().toString().isEmpty()){
			mEdtPassword.setError("Harap masukkan Password");
			mEdtPassword.requestFocus();
			return false;
		}else if(mEdtVerifikasiPassword.getText().toString().isEmpty()){
			mEdtVerifikasiPassword.setError("Masukkan Verifikasi Password");
			mEdtVerifikasiPassword.requestFocus();
			return false;
		}else if(!mEdtVerifikasiPassword.getText().toString().equals(mEdtPassword.getText().toString()) ){
			mEdtVerifikasiPassword.setError("Verifikasi Password salah!");
			mEdtVerifikasiPassword.requestFocus();
			return false;
		}
		return true;
	}

	static public String getToken(int chars) {
		String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
		String Token = "";
		for (int a = 1; a <= chars; a++) {
			Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
		}
		return Token;
	}

	private void postDatabase(){
		int radio = mRadio.getCheckedRadioButtonId();
		RadioButton btnradio = (RadioButton) findViewById(radio);

		String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
		String id = "";
		for (int a = 1; a <= 15; a++) {
			id += CharSet.charAt(new Random().nextInt(CharSet.length()));
		}

		//int x =

		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("send data .....");
		progress.setCancelable(false);
		progress.show();

		ApiRequest request = Config.getRetrofit().create(ApiRequest.class);

		Call<ResponseModel> insert = request.registerUser(
				id,
				namaDepan,
				namaBelakang,
				username,
				email,
				jenisKelamin,
				password,
				status);
		Log.d("Retrofit", "onClick: " + insert);
		insert.enqueue(new Callback<ResponseModel>() {
			@Override
			public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//						Log.d("RETRO", "response : " + response.body().toString());
				Toast.makeText(RegisterActivity.this, "response", Toast.LENGTH_SHORT).show();
				String kode = response.body().getKode();
				String pesan = response.body().getPesan();

				progress.dismiss();

				if (kode.equals("1")) {
					Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Call<ResponseModel> call, Throwable t) {
				progress.dismiss();
				Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("RETRO", "Falure : " + t.getMessage());
			}
		});
	}

	private void initView() {
		mLogoAtas = findViewById(R.id.logo_atas);
		mSvRegister = findViewById(R.id.sv_register);
		mEdtNamaDepan = findViewById(R.id.edt_nama_depan);
		mEdtNamaBelakang = findViewById(R.id.edt_nama_belakang);
		mEdtUsername = findViewById(R.id.edt_username);
		mEdtEmail = findViewById(R.id.edt_email);
		mRadio = findViewById(R.id.radio);
		mRbLaki = findViewById(R.id.rb_laki);
		mRbPerempuan = findViewById(R.id.rb_perempuan);
		mEdtPassword = findViewById(R.id.edt_password);
		mEdtVerifikasiPassword = findViewById(R.id.edt_verifikasi_password);
		mBtnRegisterDaftar = findViewById(R.id.btn_register_daftar);
		mRadioStatus = findViewById(R.id.radio_status);
		mRbUser = findViewById(R.id.rb_user);
		mRbSeniman = findViewById(R.id.rb_seniman);
	}
}
