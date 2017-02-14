package com.google.demo3.imageloader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.lib.R;
import com.hyj.lib.adapter.utils.CommonAdapter;
import com.hyj.lib.adapter.utils.ViewHolder;
import com.hyj.lib.wechat_imageUp.ImageLoader.Type;

public class ImageAdapter extends CommonAdapter<String> {
	private final String COLOR = "#77000000";// 灰暗背景颜色

	// 当切换文件夹的时候共享数据集
	public Set<String> sSelImg = new HashSet<String>();

	private ImageLoader loader;

	public ImageAdapter(Context context, List<String> lDatas, int layoutItemID) {
		super(context, lDatas, layoutItemID);

		loader = ImageLoader.getInstance(3, Type.LIFO);
	}

	@Override
	public void getViewItem(ViewHolder holder, final String path) {
		final ImageView imgPic = holder.getView(R.id.imgupItemImageView);
		final ImageView imgSel = holder.getView(R.id.imgupItemSelect);

		// 重置状态
		imgPic.setImageResource(R.drawable.pictures_no);
		imgPic.setColorFilter(null);
		loader.loadImage(path, imgPic);
		imgSel.setImageResource(R.drawable.picture_unselected);

		if (sSelImg.contains(path)) {
			imgPic.setColorFilter(Color.parseColor(COLOR));
			imgSel.setImageResource(R.drawable.pictures_selected);
		}

		imgSel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sSelImg.contains(path)) {// 已经被选择
					sSelImg.remove(path);
					imgPic.setColorFilter(null);
					imgSel.setImageResource(R.drawable.picture_unselected);
				} else {// 未被选择
					sSelImg.add(path);
					imgPic.setColorFilter(Color.parseColor(COLOR));
					imgSel.setImageResource(R.drawable.pictures_selected);
				}
			}
		});
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();

		sSelImg.clear();// 清空之前所选择的数据
	}
}
