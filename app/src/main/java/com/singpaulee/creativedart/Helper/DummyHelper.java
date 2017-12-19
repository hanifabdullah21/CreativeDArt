package com.singpaulee.creativedart.Helper;

import com.singpaulee.creativedart.R;

/**
 * Created by ASUS on 06/12/2017.
 */

public class DummyHelper {
	private String nama[] = {
			"Kerajinan Tangan",
			"Lukisan",
			"Patung",
			"Logo",
	};

	private int gambar[] = {
			R.drawable.kerajinan_tangan,
			R.drawable.lukisan,
			R.drawable.logo_persegi_panjang2,
			R.drawable.logo_kotak,
	};

	public int panjangHelper = nama.length;

	public String getNama(int x) {
		String namax = nama[x];
		return namax;
	}

	public int getGambar(int x) {
		int gambarx = gambar[x];
		return gambarx;
	}
}
