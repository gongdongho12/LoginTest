package com.ubuntukr.logintest;

import java.io.IOException;
import java.net.URLDecoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class Resister extends Activity {
	RegisterPost register;
	EditText id;
	EditText passwd;
	EditText name;
	EditText email;
	EditText introduce;
	Button registerbt;
	String URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resister);
		id = (EditText)findViewById(R.id.id);
	    passwd = (EditText)findViewById(R.id.passwd);
	    name = (EditText)findViewById(R.id.name);
	    email = (EditText)findViewById(R.id.email);
	    introduce = (EditText)findViewById(R.id.introduce);
	    registerbt = (Button)findViewById(R.id.register);
	    URL = "http://dongholab.com/test/RegisterSession.php";
	    registerbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(id.getText().toString().length() == 0){
					Toast.makeText(Resister.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
				}else if(passwd.getText().toString().length() == 0){
					Toast.makeText(Resister.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
				}else if(name.getText().toString().length() == 0){
					Toast.makeText(Resister.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
				}else{
					register = new RegisterPost();
					register.execute(URL);
				}
			}
		});
	}
	
	class RegisterPost extends AsyncTask<String, Void, String>{
		Document doc;
		String data = null;
		ProgressDialog progressDialog;
		Boolean error = false;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Resister.this);
	        progressDialog.setMessage("잠시만 기다려주세요...");
	        progressDialog.setCancelable(false);
	        progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params){
			try {
				Connection.Response res = Jsoup.connect(URL).data("user_id", id.getText().toString(), "pw", passwd.getText().toString(), "name", name.getText().toString(), "email", email.getText().toString(), "memo", introduce.getText().toString()).method(Method.POST).execute();
				doc = res.parse();
				try{
					data = URLDecoder.decode(res.cookie("hwi"), "utf-8");
					data = "회원가입이 완료되었습니다.\n" + data;
					error = false;
				}catch(Exception e){
					e.printStackTrace();
					data = doc.text();
					error = true;
				}
				Log.d("AsyncTask doInBackground","URL : " + params[0]);
			}catch(IOException e){
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
			if(error){
				Toast.makeText(Resister.this, result, Toast.LENGTH_SHORT).show();
			}else{
				Intent i = new Intent(Resister.this, Result.class);
				i.putExtra("result", result);
				startActivity(i);
			}
		}
	}
}