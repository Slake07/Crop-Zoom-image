package com.croptutorial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MainActivity extends ActionBarActivity {

	ImageLoader imageLoader;
	Bitmap croppedImage;
	CropImageView img;
	Button btnRotate, btnCrop;
	int degree = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop_layout);
		
		
		img = (CropImageView) findViewById(R.id.CropImageView);
		
		img.setDrawable(getResources().getDrawable(R.drawable.androidify),300,300);
		btnRotate = (Button)findViewById(R.id.btnRotate);
		btnCrop = (Button)findViewById(R.id.btnCrop);
		
		
		btnRotate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RotateImg(degree);
			}
		});
		
		btnCrop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CropImg().execute();
			}
		});
	}

	
	public void RotateImg(int deg){
		 
		Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.androidify);
		
		 Matrix mat = new Matrix();
		 degree = deg + 90;
			mat.postRotate(degree);
			Bitmap bMapRotate = Bitmap.createBitmap(bmap, 0,
					0, bmap.getWidth(),
					bmap.getHeight(), mat, true);
			Drawable d = new BitmapDrawable(getResources(),
					bMapRotate);
			
			img.setDrawable(d, 300, 300);
	        
	 }
	
	private class CropImg extends AsyncTask<Void, Void, String> {

		private MyProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new MyProgressDialog(MainActivity.this);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {

			croppedImage = img.getCropImage();
			
			
			 try {
			Random ran = new Random();
			
			File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "Croptutorial");
			if(!sdCardDirectory.exists())
					sdCardDirectory.mkdirs();
			
			String imgNm = "IMG_" + String.valueOf(ran.nextInt(1000)) + System.currentTimeMillis() + ".png";
			
			File image = new File(sdCardDirectory, imgNm);
		    FileOutputStream outStream;

				outStream = new FileOutputStream(image);
				croppedImage.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
			    outStream.flush();
			    outStream.close();
				
			    
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (pDialog != null)
				pDialog.dismiss();
			
			Toast.makeText(MainActivity.this, "Cropped", Toast.LENGTH_LONG).show();
		    
		}
	}
}
