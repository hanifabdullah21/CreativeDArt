package com.singpaulee.creativedart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singpaulee.creativedart.DetailItemActivity;
import com.singpaulee.creativedart.Helper.DummyHelper;
import com.singpaulee.creativedart.Model.GaleriDataModel;
import com.singpaulee.creativedart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ASUS on 06/12/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
	DummyHelper dummyHelper = new DummyHelper();
	ArrayList<GaleriDataModel> list;
	Context context;

	public HomeAdapter(Context context, ArrayList<GaleriDataModel> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
//		holder.img.setImageResource(dummyHelper.getGambar(position));
		Picasso.with(context)
				.load("https://unproportioned-gara.000webhostapp.com/api/creative/image/GaleriSeni/"+list.get(position).getUrlPhoto())
				.into(holder.img);
		holder.tv.setText(list.get(position).getNamaSeni());
		holder.card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(context, DetailItemActivity.class);
				i.putParcelableArrayListExtra("list",list);
				i.putExtra("position",position);
				context.startActivity(i);
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		ImageView img;
		CardView card;
		TextView tv;
		public ViewHolder(View itemView) {
			super(itemView);
			img = (ImageView) itemView.findViewById(R.id.image);
			tv = (TextView) itemView.findViewById(R.id.textview);
			card = (CardView) itemView.findViewById(R.id.card);
		}
	}
}
