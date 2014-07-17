package aprisma.akirah.bingung.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import aprisma.akirah.bingung.MainActivity;
import aprisma.akirah.bingung.R;

public class AkunActivity extends Activity {

	private TextView old_pass;
	private TextView new_pass;
	private TextView conf_pass;

	private String temp_old_pass;
	private String temp_new_pass;
	private String temp_conf_pass;

	private AlertDialog.Builder alert;

	private static String TITLE = "";

	private MenuItem ok_done;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.akun_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		old_pass = (TextView) findViewById(R.id.old_pass);
		new_pass = (TextView) findViewById(R.id.new_pass);
		conf_pass = (TextView) findViewById(R.id.conf_pass);

		temp_old_pass = old_pass.getText().toString();
		temp_new_pass = new_pass.getText().toString();
		temp_conf_pass = conf_pass.getText().toString();

	}

	private void popUpEditText() {
		alert = new AlertDialog.Builder(this);

		alert.setTitle(TITLE);
		alert.setMessage("\n\n");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		input.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		input.setHint(TITLE);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (TITLE.equals("Kata Sandi Lama")) {
					old_pass.setText(input.getText().toString());
				} else if (TITLE.equals("Kata Sandi Baru")) {
					new_pass.setText(input.getText().toString());
				} else {
					conf_pass.setText(input.getText().toString());
				}

				// Activate Ok MenuItem
				if (!temp_old_pass.equals(old_pass.getText().toString())
						&& !temp_new_pass.equals(new_pass.getText().toString())
						&& !temp_conf_pass.equals(conf_pass.getText()
								.toString())) {
					ok_done.setEnabled(true);
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.show();
	}

	public void showPopUp(View view) {
		TITLE = view.getTag().toString();
		popUpEditText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_edit, menu);
		ok_done = menu.findItem(R.id.ok_done);
		ok_done.setEnabled(false);
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
		case R.id.ok_done:
			intent = new Intent(getApplicationContext(), ProfilActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			return true;
		case R.id.action_settings:
			intent = new Intent(getApplicationContext(),
					PengaturanActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_in);
			return true;
		case R.id.setlang:
			Toast.makeText(getApplicationContext(), "SET LANG",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.logout:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
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

}
