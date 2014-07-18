package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;
import aprisma.akirah.bingung.holder.User;

@SuppressLint("NewApi")
public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		PengaturanActivity.SetMenu(menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
			return true;
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(), PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			return true;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.logout:
			User.ISLOGIN = false;
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
	}
	
	public void comment(View view){
		
		Intent intent = null;
		if (User.ISLOGIN){
			intent = new Intent(this, CommentActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_bottom, R.anim.slide_bottom);
		}else{
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
	}

}
