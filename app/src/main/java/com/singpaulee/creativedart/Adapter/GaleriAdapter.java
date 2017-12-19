package com.singpaulee.creativedart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.singpaulee.creativedart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ASUS on 16/12/2017.
 */

public class GaleriAdapter extends RecyclerView.Adapter<GaleriAdapter.ViewHolder> {
	Context context;
	ArrayList<GaleriDataModel> list;

	public GaleriAdapter(Context context,ArrayList<GaleriDataModel> list) {

		this.context = context;
		this.list = list;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.list_galeri,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tv.setText(list.get(position).getNamaSeni());
		Picasso.with(context)
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/GaleriSeni/"+list.get(position).getUrlPhoto())
				.into(holder.iv);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tv;
		ImageView iv;

		public ViewHolder(View itemView) {
			super(itemView);
			tv = itemView.findViewById(R.id.list_nama_seni);
			iv= itemView.findViewById(R.id.list_image);
		}
	}
}
