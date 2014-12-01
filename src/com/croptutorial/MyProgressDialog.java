package com.croptutorial;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class MyProgressDialog extends Dialog {

	public MyProgressDialog(Context context) {

		super(context, R.style.Theme_CustomDialog_new);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.row_progress);
		// this.addContentView(new ProgressBar(getContext()), new
		// LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		this.setCancelable(false);
		this.show();

	}

}
