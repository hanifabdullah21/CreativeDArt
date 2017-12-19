package com.singpaulee.creativedart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.singpaulee.creativedart.Model.RuleModel;
import com.singpaulee.creativedart.SharedPreference.SharedPrefManager;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

	private EditText edtLoginEmail;
	private EditText edtLoginPassword;
	private TextView tvLoginDaftar;
	private Button btnLoginMasuk;

	ArrayList<RuleModel> list;
	String id,first_name,last_name,email,username,password,status,photo;

	SharedPrefManager pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();

		list = new ArrayList<>();
		pref =new SharedPrefManager(this);

		if (pref.getSudahLogin() && pref.getStatus().equalsIgnoreCase("user")){
			startActivity(new Intent(LoginActivity.this,KontenActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
		}else if(pref.getSudahLogin() && pref.getStatus().equalsIgnoreCase("seniman")){
			startActivity(new Intent(LoginActivity.this,KontenSenimanActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
		}

		tvLoginDaftar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent daftar = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(daftar);
			}
		});

		btnLoginMasuk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!verifikasi()) {
					return;
				}
				login();
			}
		});
	}

	private void preference() {
		pref.savePrefBoolean(SharedPrefManager.LOGIN,true);
		pref.savePref(SharedPrefManager.ID, id );
		pref.savePref(SharedPrefManager.FIRST_NAME,first_name);
		pref.savePref(SharedPrefManager.LAST_NAME,last_name);
		pref.savePref(SharedPrefManager.USERNAME,username);
		pref.savePref(SharedPrefManager.EMAIL,email);
		pref.savePref(SharedPrefManager.PASSWORD,password);
		pref.savePref(SharedPrefManager.STATUS,status);
		pref.savePref(SharedPrefManager.PHOTO,photo);
	}

	public boolean verifikasi() {
		if (edtLoginEmail.toString().isEmpty()) {
			edtLoginEmail.setError("Harap Masukkan Email");
			edtLoginEmail.requestFocus();
			return false;
		}
		if (edtLoginPassword.toString().isEmpty()) {
			edtLoginPassword.setError("Harap Masukkan Password");
			edtLoginPassword.requestFocus();
			return false;
		}
		return true;
	}

	private void initView() {
		edtLoginEmail = findViewById(R.id.edt_login_email);
		edtLoginPassword = findViewById(R.id.edt_login_password);
		tvLoginDaftar = findViewById(R.id.tv_login_daftar);
		btnLoginMasuk = findViewById(R.id.btn_login_masuk);
	}

	public void login() {
		final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Loading", "Harap tunggu...", false, false);
		ApiRequest api = Config.getRetrofit().create(ApiRequest.class);
		retrofit2.Call<ArrayList<RuleModel>> call = api.getRuleLogin();
		call.enqueue(new Callback<ArrayList<RuleModel>>() {
			@Override
			public void onResponse(Call<ArrayList<RuleModel>> call, Response<ArrayList<RuleModel>> response) {
				list = response.body();
				for (int i = 0; i<list.size();i++) {
					String u = list.get(i).getUsername();
					String p = list.get(i).getPassword();
					String st = list.get(i).getStatus();
					if (edtLoginEmail.getText().toString().equalsIgnoreCase(u) && edtLoginPassword.getText().toString().equalsIgnoreCase(p)) {
						loading.dismiss();
						id = list.get(i).getId();
						first_name = list.get(i).getNama_depan();
						last_name = list.get(i).getNama_belakang();
						username = list.get(i).getUsername();
						email = list.get(i).getEmail();
						status = list.get(i).getStatus();
						password = list.get(i).getPassword();
						photo = list.get(i).getUrl_photo();
						if (st.equalsIgnoreCase("User")) {
							preference();
							startActivity(new Intent(getApplicationContext(), KontenActivity.class));
						} else {
							preference();
							startActivity(new Intent(getApplicationContext(), KontenSenimanActivity.class));
						}
					}else{
//						Toast.makeText(LoginActivity.this, "Cek Username atau Password", Toast.LENGTH_SHORT).show();
					}
				}
//				Toast.makeText(LoginActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
				loading.dismiss();
				edtLoginEmail.setText(null);
				edtLoginPassword.setText(null);
			}

			@Override
			public void onFailure(Call<ArrayList<RuleModel>> call, Throwable t) {
				Toast.makeText(LoginActivity.this, "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
