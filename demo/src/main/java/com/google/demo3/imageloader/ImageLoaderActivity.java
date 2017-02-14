package com.google.demo3.imageloader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib.R;
import com.hyj.lib.wechat_imageUp.DirPopupWindow.onDirSelectedListener;

@SuppressLint("HandlerLeak")
public class ImageLoaderActivity extends Activity {
	private final int DATA_LOADED = 0X110;// 数据加载完成
	private final String SEPERATOR = ",";// 分隔符

	private GridView mGridView;
	private List<String> lImgs;
	private ImageAdapter adapter;

	private TextView tvDirName;
	private TextView tvDirCount;

	private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
	private ProgressDialog mProgressDialog;// 扫描图片进度条
	private DirPopupWindow mDirPopupWindow;// 弹出窗口

	/**
	 * 文件名过滤器
	 */
	private FilenameFilter filter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String filename) {
			if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
					|| filename.endsWith(".png")) {
				return true;
			}
			return false;
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == DATA_LOADED) {
				// 绑定数据到view中
				FolderBean folder = (FolderBean) msg.obj;
				data2View(folder);

				mProgressDialog.dismiss();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageup_main);

		myInit();
	}

	private void myInit() {
		initView();
		initData();
		initListener();
		initDirPopupWindow();
	}

	private void initView() {
		lImgs = new ArrayList<String>();
		adapter = new ImageAdapter(this, lImgs, R.layout.wechat_imageup_item);
		mGridView = (GridView) findViewById(R.id.imgupGridView);
		mGridView.setAdapter(adapter);

		tvDirName = (TextView) findViewById(R.id.imgupTvDirName);
		tvDirCount = (TextView) findViewById(R.id.imgupTvDirCount);
	}

	private void initData() {
		// 利用ContentProvider扫描手机中的所有图片
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			Toast.makeText(this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
			return;
		}

		mProgressDialog = ProgressDialog.show(this, null, "正在加载……");

		// 扫描手机中的图片
		new Thread(new Runnable() {

			public void run() {
				Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = ImageLoaderActivity.this
						.getContentResolver();

				String selection = MediaStore.Images.Media.MIME_TYPE
						+ "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?";
				String[] selectionArgs = new String[] { "image/jpeg",
						"image/png" };
				String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;
				Cursor cursor = cr.query(mImgUri, null, selection,
						selectionArgs, sortOrder);

				// 存储遍历过的parentFile，防止重复遍历
				Set<String> mDirPaths = new HashSet<String>();
				FolderBean folder = null;
				while (cursor.moveToNext()) {
					// 拿到图片路径
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					File parentFile = new File(path).getParentFile();
					if (null == parentFile) {
						continue;
					}

					String dirPath = parentFile.getAbsolutePath();
					if (mDirPaths.contains(dirPath)) {
						continue;
					}

					// 获取文件夹下所有图片的名字
					String[] fileNames = parentFile.list(filter);
					if (null == fileNames) {
						continue;
					}

					mDirPaths.add(dirPath);
					folder = new FolderBean();
					folder.setDirPath(dirPath);
					folder.setFirstImgPath(path);
					folder.setImgCount(fileNames.length);
					mFolderBeans.add(folder);
				}
				cursor.close();

				folder = new FolderBean();
				folder.setDirName("所有图片");
				folder.setFirstImgPath(mFolderBeans.get(0).getFirstImgPath());
				String imgPath = "";
				int imgCount = 0;
				for (FolderBean bean : mFolderBeans) {
					imgPath += bean.getDirPath() + SEPERATOR;
					imgCount += bean.getImgCount();
				}
				folder.setDirPath(imgPath.substring(0, imgPath.length() - 1));
				folder.setImgCount(imgCount);

				mFolderBeans.add(0, folder);
				gridViewDatas(folder);

				// 通知handler扫描图片完成
				Message msg = mHandler.obtainMessage();
				msg.what = DATA_LOADED;
				msg.obj = folder;
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	private void initListener() {
		tvDirName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDirPopupWindow.setAnimationStyle(R.style.popupwindow_anim);
				// 设置显示位置
				mDirPopupWindow.showAsDropDown(tvDirName, 0, 0);

				// 设置显示内容区域变暗
				lightSwitch(0.3f);
			}
		});
	}

	/**
	 * 内容区域明暗度设置
	 */
	private void lightSwitch(float alpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = alpha;
		getWindow().setAttributes(lp);
	}

	/**
	 * 把扫描完成的数据显示在GridView中
	 */
	private void data2View(FolderBean bean) {
		if (TextUtils.isEmpty(bean.getDirPath())) {
			Toast.makeText(this, "未扫描到任何图片", Toast.LENGTH_SHORT).show();
			return;
		}

		adapter.notifyDataSetChanged();
		tvDirName.setText(bean.getDirName());
		tvDirCount.setText(bean.getImgCountStr());
	}

	private void initDirPopupWindow() {
		mDirPopupWindow = new DirPopupWindow(this, mFolderBeans);
		mDirPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				lightSwitch(1.0f);
			}
		});

		mDirPopupWindow.setOnDirSelectedLisetner(new onDirSelectedListener() {
			@Override
			public void onSelected(FolderBean folder) {
				gridViewDatas(folder);
				data2View(folder);
				mDirPopupWindow.dismiss();
			}
		});
	};

	/**
	 * GridView上显示的数据
	 * 
	 * @param FolderBean
	 */
	private void gridViewDatas(FolderBean folder) {
		lImgs.clear();
		String[] dirPaths = folder.getDirPath().split(SEPERATOR);

		File selDir;
		String[] fileNames;
		for (String path : dirPaths) {
			selDir = new File(path);
			fileNames = selDir.list(filter);

			for (String stra : fileNames) {
				lImgs.add(path + File.separator + stra);
			}
		}
	}
}
