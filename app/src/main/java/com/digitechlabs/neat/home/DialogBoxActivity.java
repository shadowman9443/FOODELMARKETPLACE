package com.digitechlabs.neat.home;




import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.dgtech.neat.R;

public class DialogBoxActivity extends HeaderFooterActivity implements DialogInterface{
	
	EditText mssgField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// savedInstanceState = getIntent().getExtras();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		init();
	}
	public void init(){
		mssgField = (EditText) findViewById(R.id.mssgField);
		
		
		
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		
	}

}
