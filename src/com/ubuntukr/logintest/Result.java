package com.ubuntukr.logintest;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class Result extends Activity {
	
	TextView result;
	Intent data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		result = (TextView)findViewById(R.id.result);
		try{
			data = getIntent();
			result.setText(data.getStringExtra("result") + "님이(가) 로그인하셨습니다.");
		}catch(Exception e){
			e.printStackTrace();
			result.setText("다시로그인해주세요.");
		}
	}
}