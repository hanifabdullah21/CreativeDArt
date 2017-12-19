package com.singpaulee.creativedart.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.singpaulee.creativedart.AddItemActivity;
import com.singpaulee.creativedart.Model.GaleriResponseModel;
import com.singpaulee.creativedart.Model.ResponseModel;
import com.singpaulee.creativedart.Model.Result;
import com.singpaulee.creativedart.R;
import com.singpaulee.creativedart.SharedPreference.SharedPrefManager;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;
import com.singpaulee.creativedart.rest.ResultConfig;
import com.singpaulee.creativedart.rest.permission.PermissionActivity;
import com.singpaulee.creativedart.rest.permission.PermissionChecker;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {


	private CircleImageView mImageProfil;
	private TextView mEdtProfilNama;
	private EditText mEdtProfilUsername;
	private EditText mEdtProfilEmail;
	private EditText mEdtProfilPassword;
	private EditText mEdtProfilVerifikasiPassword;
	private Button mBtnProfilSimpan;

	private TextView tvUnggahFoto;
	String imagePath;
	private static final String[] PERMISSION_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
	Context context;
	PermissionChecker permissionChecker;
	SharedPrefManager pref;
	String h;
	private ContentProvider contentResolver;

	String id, username, email, url, password;

	public ProfilFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_profil, container, false);
		initView(v);

		pref = new SharedPrefManager(getActivity());

		mEdtProfilNama.setText(pref.getFirstNama()+" "+pref.getLastName());
		mEdtProfilEmail.setText(pref.getEmail());
		mEdtProfilUsername.setText(pref.getUsername());
		mEdtProfilPassword.setText(pref.getPassword());
		Picasso.with(getActivity())
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/"+pref.getPhoto())
				.error(R.drawable.profil)
				.into(mImageProfil);

		permissionChecker = new PermissionChecker(getActivity());

		mImageProfil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showImagePopup(view);
			}
		});

		mBtnProfilSimpan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				id = pref.getID();
				username = mEdtProfilUsername.getText().toString().trim();
				email = mEdtProfilEmail.getText().toString().trim();
				if(!mEdtProfilVerifikasiPassword.getText().toString().equals(mEdtProfilPassword.getText().toString())){
					mEdtProfilPassword.setError("Password tidak sama");
					return;
				}else{
					password = mEdtProfilPassword.getText().toString();
				}
				uploadImage();
				url = h;
				postDatabase();

			}
		});

		return v;
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
		PermissionActivity.startActivityForResult(getActivity(), 0, permissionReadStorage);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			if (data == null) {
				Toast.makeText(getActivity(), "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
				return;

			}

			Uri selectImageUri = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor c = getActivity().getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
			if (c != null) {
				c.moveToFirst();

				int columnIndex = c.getColumnIndex(filePathColumn[0]);
				imagePath = c.getString(columnIndex);

				Picasso.with(getActivity()).load(new File(imagePath)).into(mImageProfil);
				h = new File(imagePath).getName();

				c.close();
			} else {
				Toast.makeText(getActivity(), "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void uploadImage() {
		final ProgressDialog p;
		p = new ProgressDialog(getActivity());
		p.setMessage("Proses Upload Foto");
		p.show();


		File f = new File(imagePath);
		Toast.makeText(getActivity(), "Gambar " + h, Toast.LENGTH_SHORT).show();

		ApiRequest s = ResultConfig.getService();
		RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
		final MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", f.getName(), requestFile);
		Call<Result> resultCAll = s.postIMmage(part); // Fungsing paling penting
		resultCAll.enqueue(new Callback<Result>() {
			@Override
			public void onResponse(Call<Result> call, Response<Result> response) {

				p.dismiss();
				if (response.isSuccessful()) {
					if (response.body().getResult().equals("success"))
						Toast.makeText(getActivity(), "Sukses", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getActivity(), "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "Upload Gambar Gagal", Toast.LENGTH_SHORT).show();
				}

				imagePath = "";

			}

			@Override
			public void onFailure(Call<Result> call, Throwable t) {
				Toast.makeText(getActivity(), "Gagal Upload Fail", Toast.LENGTH_SHORT).show();
				p.dismiss();
			}
		});
	}

	private void preference() {
		pref.savePref(SharedPrefManager.USERNAME,username);
		pref.savePref(SharedPrefManager.EMAIL,email);
		pref.savePref(SharedPrefManager.PASSWORD,password);
		pref.savePref(SharedPrefManager.PHOTO,url);
	}

	public void postDatabase() {
		final ProgressDialog progress = new ProgressDialog(getActivity());
		progress.setMessage("send data .....");
		progress.setCancelable(false);
		progress.show();

		ApiRequest request = Config.getRetrofit().create(ApiRequest.class);

		Call<ResponseModel> insert = request.updateProfil(
				id,
				username,
				email,
				password,
				url
		);
		Log.d("Retrofit", "onClick: " + insert);
		insert.enqueue(new Callback<ResponseModel>() {
			@Override
			public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//						Log.d("RETRO", "response : " + response.body().toString());
				Toast.makeText(getActivity(), "response", Toast.LENGTH_SHORT).show();
				String kode = response.body().getKode();
				String pesan = response.body().getPesan();

				if(response.isSuccessful()){

				}
				preference();
				Toast.makeText(context, ""+pref.getPhoto(), Toast.LENGTH_SHORT).show();
				progress.dismiss();

				if (kode.equals("1")) {
					Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), pesan, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Call<ResponseModel> call, Throwable t) {
				progress.dismiss();
				Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
				Log.d("RETRO", "Falure : " + t.getMessage());
			}
		});
	}

	private void initView(View v) {
		mImageProfil = v.findViewById(R.id.image_profil);
		mEdtProfilNama = v.findViewById(R.id.edt_profil_nama);
		mEdtProfilUsername = v.findViewById(R.id.edt_profil_username);
		mEdtProfilEmail = v.findViewById(R.id.edt_profil_email);
		mEdtProfilPassword = v.findViewById(R.id.edt_profil_password);
		mEdtProfilVerifikasiPassword = v.findViewById(R.id.edt_profil_verifikasi_password);
		mBtnProfilSimpan = v.findViewById(R.id.btn_profil_simpan);
	}
}
