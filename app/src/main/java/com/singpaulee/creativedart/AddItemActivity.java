package com.singpaulee.creativedart;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.singpaulee.creativedart.Model.GaleriResponseModel;
import com.singpaulee.creativedart.Model.Result;
import com.singpaulee.creativedart.Model.ResultGaleri;
import com.singpaulee.creativedart.SharedPreference.SharedPrefManager;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;
import com.singpaulee.creativedart.rest.ResultConfigGaleri;
import com.singpaulee.creativedart.rest.permission.PermissionActivity;
import com.singpaulee.creativedart.rest.permission.PermissionChecker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

	private CircleImageView mCivGaleri;
	private EditText mEdtNamaSeni;
	private EditText mEdtDeskripsi;
	private Button mBtnUpload;
	private CircleImageView mGetPhoto;
	private TextView mTvKategori;
	private Spinner mSpinner;

	String imagePath;
	private static final String[] PERMISSION_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
	Context context;
	PermissionChecker permissionChecker;
	String h;
	String nama_seni, nama_seniman, url_photo, deskripsi, id_seniman, kategori;
	int harga_seni;
	String kode = "";
	SharedPrefManager pref;


	List<String> categories;
	String selectedItemText;
	private EditText mEdtHarga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		initView();

		categories = new ArrayList<String>();
		categories.add("Lain-Lain");
		categories.add("Patung");
		categories.add("Lukisan");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(dataAdapter);

		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (i != 0) {
					kategori = (String) adapterView.getItemAtPosition(i);
				}
				mTvKategori.setText("" + adapterView.getItemAtPosition(i));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		mTvKategori.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				mSpinner.performClick();
				return true;

			}
		});

		mTvKategori.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				mSpinner.performClick();
			}
		});

		pref = new SharedPrefManager(this);

		permissionChecker = new PermissionChecker(this);
		mCivGaleri.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		mGetPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showImagePopup(view);
			}
		});

		mBtnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				uploadImage();

				String CharSet = "asdfghjkl1234567890";
				for (int a = 1; a <= 5; a++) {
					kode += CharSet.charAt(new Random().nextInt(CharSet.length()));
				}
				id_seniman = pref.getID();
				nama_seniman = pref.getFirstNama() + " " + pref.getLastName();
				nama_seni = mEdtNamaSeni.getText().toString().trim();
				deskripsi = mEdtDeskripsi.getText().toString();
				harga_seni = Integer.valueOf(mEdtHarga.getText().toString());
				url_photo = h;
				kategori = mTvKategori.getText().toString();
				postDatabase();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				}, 2000);
			}
		});
	}

	private void initView() {
		mCivGaleri = findViewById(R.id.civ_galeri);
		mEdtNamaSeni = findViewById(R.id.edt_nama_seni);
		mEdtDeskripsi = findViewById(R.id.edt_deskripsi);
		mBtnUpload = findViewById(R.id.btn_upload);
		mGetPhoto = findViewById(R.id.getPhoto);
		mTvKategori = findViewById(R.id.tv_kategori);
		mSpinner = findViewById(R.id.spinner);
		mEdtHarga = findViewById(R.id.edt_harga);
	}

	public void showImagePopup(View v) {
		if (permissionChecker.lacksPermissions(PERMISSION_READ_STORAGE)) {
			startPermissionActivity(PERMISSION_READ_STORAGE);
		} else {
			Intent qq = new Intent(Intent.ACTION_PICK);
			qq.setType("image/*");
			startActivityForResult(Intent.createChooser(qq, "Pilih Foto"), 100);
		}
	}

	private void startPermissionActivity(String[] permissionReadStorage) {
		PermissionActivity.startActivityForResult(this, 0, permissionReadStorage);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			if (data == null) {
				Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
				return;
			}
			Uri selectImageUri = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor c = this.getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
			if (c != null) {
				c.moveToFirst();

				int columnIndex = c.getColumnIndex(filePathColumn[0]);
				imagePath = c.getString(columnIndex);

				Picasso.with(this).load(new File(imagePath)).into(mCivGaleri);
				h = new File(imagePath).getName();
				c.close();
			} else {
				Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void uploadImage() {
		final ProgressDialog p;
		p = new ProgressDialog(this);
		p.setMessage("Proses Upload Foto");
		p.show();

		File f = new File(imagePath);
		Toast.makeText(this, "Gambar " + h, Toast.LENGTH_SHORT).show();

		ApiRequest s = ResultConfigGaleri.getService();
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
		final MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestFile);
		Call<ResultGaleri> resultCAll = s.postImage(part); // Fungsing paling penting
		resultCAll.enqueue(new Callback<ResultGaleri>() {
			@Override
			public void onResponse(Call<ResultGaleri> call, Response<ResultGaleri> response) {

				p.dismiss();
				if (response.isSuccessful()) {
					if (response.body().getResult().equals("success"))
						Toast.makeText(AddItemActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(AddItemActivity.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddItemActivity.this, "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
				}

				imagePath = "";
			}

			@Override
			public void onFailure(Call<ResultGaleri> call, Throwable t) {
				Toast.makeText(AddItemActivity.this, "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
				p.dismiss();
				return;
			}
		});
	}

	public void postDatabase() {
		final ProgressDialog progress = new ProgressDialog(AddItemActivity.this);
		progress.setMessage("send data .....");
		progress.setCancelable(false);
		progress.show();

		ApiRequest request = Config.getRetrofit().create(ApiRequest.class);

		Call<GaleriResponseModel> insert = request.addItem(
				kode,
				id_seniman,
				nama_seni,
				nama_seniman,
				deskripsi,
				url_photo,
				kategori,
				harga_seni
		);
		Log.d("Retrofit", "onClick: " + insert);
		insert.enqueue(new Callback<GaleriResponseModel>() {
			@Override
			public void onResponse(Call<GaleriResponseModel> call, Response<GaleriResponseModel> response) {
				Toast.makeText(AddItemActivity.this, "response", Toast.LENGTH_SHORT).show();
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
			public void onFailure(Call<GaleriResponseModel> call, Throwable t) {
				progress.dismiss();
				Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("RETRO", "Falure : " + t.getMessage());
			}
		});
	}
}
