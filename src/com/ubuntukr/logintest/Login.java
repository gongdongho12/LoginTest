package com.ubuntukr.logintest;

import java.io.IOException;
import java.net.URLDecoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	LoginPost login;
	EditText id;
	EditText passwd;
	Button loginbt;
	String URL;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    id = (EditText)findViewById(R.id.id);
	    passwd = (EditText)findViewById(R.id.passwd);
	    loginbt = (Button)findViewById(R.id.login);
	    URL = "http://dongholab.com/test/LoginSession.php";
		loginbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(id.getText().toString().length() == 0){
					Toast.makeText(Login.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
				}else if(passwd.getText().toString().length() == 0){
					Toast.makeText(Login.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
				}else{
					login = new LoginPost();
					login.execute(URL);
				}
			}
		});
	}
	
	class LoginPost extends AsyncTask<String, Void, String>{
		Document doc;
		String data = null;
		ProgressDialog progressDialog;
		Boolean error = false;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Login.this);
	        progressDialog.setMessage("잠시만 기다려주세요...");
	        progressDialog.setCancelable(false);
	        progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params){
			try {
				Connection.Response res = Jsoup.connect(URL).data("user_id", id.getText().toString(), "pw", passwd.getText().toString()).method(Method.POST).execute();
				doc = res.parse();
				try{
					data = URLDecoder.decode(res.cookie("hwi"), "utf-8");
					error = false;
				}catch(Exception e){
					e.printStackTrace();
					error = true;
					data = doc.text();
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
				Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
			}else{
				Intent i = new Intent(Login.this, Result.class);
				i.putExtra("result", result);
				startActivity(i);
			}
		}
	}
}