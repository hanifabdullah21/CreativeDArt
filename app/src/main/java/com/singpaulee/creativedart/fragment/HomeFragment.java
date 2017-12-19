package com.singpaulee.creativedart.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.singpaulee.creativedart.Adapter.HomeAdapter;
import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.singpaulee.creativedart.R;
import com.singpaulee.creativedart.rest.ApiRequest;
import com.singpaulee.creativedart.rest.Config;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


	private SliderLayout mSlider;
	private PagerIndicator mCustomIndicator;
	private PagerIndicator mCustomIndicator2;
	private TabHost mTabhost;
	private TabWidget mTabs;
	private FrameLayout mTabcontent;
	private ScrollView mSvSemua;
	private ScrollView mSvPatung;
	private RecyclerView mRvPatung;
	private ScrollView mSvLukisan;
	private ScrollView mSvLainlain;
	private RecyclerView mRvSemua;
	private RecyclerView mRvLukisan;
	private RecyclerView mRvLain;

	public HomeFragment() {
		// Required empty public constructor
	}

	ArrayList<GaleriDataModel> listSemua = new ArrayList<>();
	ArrayList<GaleriDataModel> listPatung = new ArrayList<>();
	ArrayList<GaleriDataModel> listLukisan = new ArrayList<>();
	ArrayList<GaleriDataModel> listLain = new ArrayList<>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_home, container, false);
		initView(v);

		ambilData();

		//TODO Setting RecyclerView
		/*Setting of RecyclerView*/
		mRvPatung.setHasFixedSize(true);
		mRvPatung.setLayoutManager(new GridLayoutManager(getActivity(), 2));

		mRvSemua.setHasFixedSize(true);
		mRvSemua.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		/*Setting of RecyclerView*/

		mRvLukisan.setHasFixedSize(true);
		mRvLukisan.setLayoutManager(new GridLayoutManager(getActivity(), 2));

		mRvLain.setHasFixedSize(true);
		mRvLain.setLayoutManager(new GridLayoutManager(getActivity(), 2));
		//TODO Setting Tab Layout
		/*Setting of Tab Layout*/
		mTabhost.setup();
		//Setting Tab Layout
		TabHost.TabSpec mTabSpec = mTabhost.newTabSpec("SEMUA");
		//tab semua
		mTabSpec.setContent(R.id.sv_semua);
		mTabSpec.setIndicator("SEMUA");
		mTabhost.addTab(mTabSpec);
		//tab patung
		mTabSpec = mTabhost.newTabSpec("PATUNG");
		mTabSpec.setContent(R.id.sv_patung);
		mTabSpec.setIndicator("PATUNG");
		mTabhost.addTab(mTabSpec);
		//tab lukisan
		mTabSpec = mTabhost.newTabSpec("LUKISAN");
		mTabSpec.setContent(R.id.sv_lukisan);
		mTabSpec.setIndicator("LUKISAN");
		mTabhost.addTab(mTabSpec);
		//tab lain2
		mTabSpec = mTabhost.newTabSpec("LAIN-LAIN");
		mTabSpec.setContent(R.id.sv_lainlain);
		mTabSpec.setIndicator("LAIN-LAIN");
		mTabhost.addTab(mTabSpec);
		/*Setting of Tab Layout*/

		//TODO Setting Slider
		/*Setting Slider*/
		HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
		file_maps.put("Icon", R.drawable.logo_kotak);
		file_maps.put("Semicolon", R.drawable.semicolon);

		for (String name : file_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(getActivity());
			// initialize a SliderLayout
			textSliderView
					.description(name)
					.image(file_maps.get(name))
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(this);

			//add your extra information
			textSliderView.bundle(new Bundle());
			textSliderView.getBundle()
					.putString("extra", name);

			mSlider.addSlider(textSliderView);
		}
		mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
		mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mSlider.setCustomAnimation(new DescriptionAnimation());
		mSlider.setDuration(4000);
		mSlider.addOnPageChangeListener(this);
		/*Setting Slider*/

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
				listSemua = response.body();
				mRvSemua.setAdapter(new HomeAdapter(getActivity(), listSemua));
				for (int i = 0; i < listSemua.size(); i++) {
					if (listSemua.get(i).getKategori().equalsIgnoreCase("patung")) {
						GaleriDataModel model = new GaleriDataModel();
						model.setKode(listSemua.get(i).getKode());
						model.setIdSeniman(listSemua.get(i).getIdSeniman());
						model.setNamaSeni(listSemua.get(i).getNamaSeni());
						model.setNamaSeniman(listSemua.get(i).getNamaSeniman());
						model.setDeskripsi(listSemua.get(i).getDeskripsi());
						model.setUrlPhoto(listSemua.get(i).getUrlPhoto());
						model.setKategori(listSemua.get(i).getKategori());
						model.setHargaSeni(listSemua.get(i).getHargaSeni());
						listPatung.add(model);
						mRvPatung.setAdapter(new HomeAdapter(getActivity(), listPatung));
					} else if (listSemua.get(i).getKategori().equalsIgnoreCase("lukisan")) {
						GaleriDataModel model = new GaleriDataModel();
						model.setKode(listSemua.get(i).getKode());
						model.setIdSeniman(listSemua.get(i).getIdSeniman());
						model.setNamaSeni(listSemua.get(i).getNamaSeni());
						model.setNamaSeniman(listSemua.get(i).getNamaSeniman());
						model.setDeskripsi(listSemua.get(i).getDeskripsi());
						model.setUrlPhoto(listSemua.get(i).getUrlPhoto());
						model.setKategori(listSemua.get(i).getKategori());
						model.setHargaSeni(listSemua.get(i).getHargaSeni());
						listLukisan.add(model);
						mRvLukisan.setAdapter(new HomeAdapter(getActivity(), listLukisan));
					} else if (listSemua.get(i).getKategori().equalsIgnoreCase("lain-lain")) {
						GaleriDataModel model = new GaleriDataModel();
						model.setKode(listSemua.get(i).getKode());
						model.setIdSeniman(listSemua.get(i).getIdSeniman());
						model.setNamaSeni(listSemua.get(i).getNamaSeni());
						model.setNamaSeniman(listSemua.get(i).getNamaSeniman());
						model.setDeskripsi(listSemua.get(i).getDeskripsi());
						model.setUrlPhoto(listSemua.get(i).getUrlPhoto());
						model.setKategori(listSemua.get(i).getKategori());
						model.setHargaSeni(listSemua.get(i).getHargaSeni());
						listLain.add(model);
						mRvLain.setAdapter(new HomeAdapter(getActivity(), listLain));
					}
				}
			}

			@Override
			public void onFailure(Call<ArrayList<GaleriDataModel>> call, Throwable t) {
				Toast.makeText(getActivity(), "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
				loading.dismiss();
			}
		});
	}

	private void initView(View v) {
		mSlider = v.findViewById(R.id.slider);
		mCustomIndicator = v.findViewById(R.id.custom_indicator);
		mCustomIndicator2 = v.findViewById(R.id.custom_indicator2);
		mTabhost = v.findViewById(R.id.tabhost);
//		mTabs = v.findViewById(R.id.tabs);
//		mTabcontent = v.findViewById(R.id.tabcontent);
		mSvSemua = v.findViewById(R.id.sv_semua);
		mSvPatung = v.findViewById(R.id.sv_patung);
		mRvPatung = v.findViewById(R.id.rv_patung);
		mSvLukisan = v.findViewById(R.id.sv_lukisan);
		mSvLainlain = v.findViewById(R.id.sv_lainlain);
		mRvSemua = v.findViewById(R.id.rv_semua);
		mRvLukisan = v.findViewById(R.id.rv_lukisan);
		mRvLain = v.findViewById(R.id.rv_lain);
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
