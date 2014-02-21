package com.ubuntukr.logintest;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {
	
	Button login;
	Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		login = (Button)findViewById(R.id.login);
		register = (Button)findViewById(R.id.register);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.login:
				startActivity(new Intent(this, Login.class));
				break;
			case R.id.register:
				startActivity(new Intent(this, Resister.class));
				break;
			default:
				break;
		}
	}
}