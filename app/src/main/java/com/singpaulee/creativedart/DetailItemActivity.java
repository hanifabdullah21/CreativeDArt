package com.singpaulee.creativedart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailItemActivity extends AppCompatActivity {

	private TextView mNamaSeni;
	private ImageView mDetailGambarSeni;
	private TextView mDetailNamaSeniman;
	private TextView mDetailDeskripsi;
	private TextView mDetailHarga;
	private TextView mTvBeli;

	ArrayList<GaleriDataModel> list;
	int posisi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_item);

		initView();

		list = getIntent().getParcelableArrayListExtra("list");
		posisi = getIntent().getIntExtra("position",0);

		mNamaSeni.setText(""+list.get(posisi).getNamaSeni());
		mDetailNamaSeniman.setText(""+list.get(posisi).getNamaSeniman());
		mDetailDeskripsi.setText(""+list.get(posisi).getDeskripsi());
		mDetailHarga.setText("Rp. "+list.get(posisi).getHargaSeni());
		Picasso.with(this)
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/GaleriSeni/"+list.get(posisi).getUrlPhoto())
				.into(mDetailGambarSeni);

		mTvBeli.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(DetailItemActivity.this, "Belum bisa beli :(", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initView() {
		mNamaSeni = findViewById(R.id.nama_seni);
		mDetailGambarSeni = findViewById(R.id.detail_gambar_seni);
		mDetailNamaSeniman = findViewById(R.id.detail_nama_seniman);
		mDetailDeskripsi = findViewById(R.id.detail_deskripsi);
		mDetailHarga = findViewById(R.id.detail_harga);
		mTvBeli = findViewById(R.id.tv_beli);
	}
}
