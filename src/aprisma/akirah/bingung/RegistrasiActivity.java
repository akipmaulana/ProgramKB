package aprisma.akirah.bingung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class RegistrasiActivity extends Activity {

	public RegistrasiActivity() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrasi_activity);
		
		getActionBar().hide();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	
	/*
	 * Button Action Registrasi
	 */
	public void Registrasi(View view){
		Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
	}
}
