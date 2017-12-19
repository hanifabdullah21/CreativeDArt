package com.singpaulee.creativedart.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.singpaulee.creativedart.Adapter.GaleriAdapter;
import com.singpaulee.creativedart.Adapter.HomeAdapter;
import com.singpaulee.creativedart.AddItemActivity;
import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.singpaulee.creativedart.R;
import com.singpaulee.creativedart.SharedPreference.SharedPrefManager;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaleriFragment extends Fragment {


	private Button mBtnTambah;
	private RecyclerView mRvGaleri;
	ArrayList<GaleriDataModel> list;
	ArrayList<GaleriDataModel> listSeni;
	SharedPrefManager pref;

	public GaleriFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_galeri, container, false);
		initView(v);

		list = new ArrayList<>();
		listSeni = new ArrayList<>();
		pref = new SharedPrefManager(getActivity());

		mBtnTambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getActivity(), AddItemActivity.class));
			}
		});

		mRvGaleri.setHasFixedSize(true);
		mRvGaleri.setLayoutManager(new LinearLayoutManager(getActivity()));

		ambilData();

		return v;

	}
	public void ambilData() {
		final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading", "Harap tunggu...", false, false);
		ApiRequest api = Config.getRetrofit().create(ApiRequest.class);
		Call<ArrayList<GaleriDataModel>> call = api.getGaleri();
		call.enqueue(new Callback<ArrayList<GaleriDataModel>>() {
			@Override
			public void onResponse(Call<ArrayList<GaleriDataModel>> call, Response<ArrayList<GaleriDataModel>> response) {
				loading.dismiss();
				list = response.body();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIdSeniman().equals(pref.getID())) {
						GaleriDataModel model = new GaleriDataModel();
						model.setKode(list.get(i).getKode());
						model.setIdSeniman(list.get(i).getIdSeniman());
						model.setNamaSeni(list.get(i).getNamaSeni());
						model.setNamaSeniman(list.get(i).getNamaSeniman());
						model.setDeskripsi(list.get(i).getDeskripsi());
						model.setUrlPhoto(list.get(i).getUrlPhoto());
						model.setKategori(list.get(i).getKategori());
						listSeni.add(model);
						mRvGaleri.setAdapter(new GaleriAdapter(getActivity(),listSeni));
					}
				}
			}

			@Override
			public void onFailure(Call<ArrayList<GaleriDataModel>> call, Throwable t) {
				Toast.makeText(getActivity(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initView(View v) {
		mBtnTambah = v.findViewById(R.id.btn_tambah);
		mRvGaleri = v.findViewById(R.id.rv_galeri);
	}
}
