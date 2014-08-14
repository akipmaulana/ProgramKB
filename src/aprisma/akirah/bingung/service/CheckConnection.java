package aprisma.akirah.bingung.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import aprisma.akirah.bingung.R;

public class CheckConnection {

	private TextView connectLay;
	private boolean isRunning = true;
	private Handler mHandler = new Handler();
	private Thread thread;
	private ConnectivityManager cm;
	private Context ctx;
	private boolean isConnect = false;

	public CheckConnection(TextView connectLay, ConnectivityManager cm, Context ctx) {
		this.connectLay = connectLay;
		this.cm = cm;
		this.ctx = ctx;
		init();
	}

	private void init() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
					try {
						Thread.sleep(3000);
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								displayData();
							}
						});
					} catch (Exception e) {
					}
				}
			}
		});

		thread.start();
	}

	private void displayData() {
		NetworkInfo nf = cm.getActiveNetworkInfo();
		Animation animation = null;
		if (nf != null && nf.isConnected() == true) {
			isConnect = true;
			connectLay.setAnimation(null);
			connectLay.setVisibility(View.INVISIBLE);
		} else {
			isConnect = false;
			connectLay.setVisibility(View.VISIBLE);
			animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_bottom);
			connectLay.setAnimation(animation);
			connectLay.animate();
		}
	}
	
	public boolean getConnect() {
		return isConnect;
	}
	
	public void Destroy(){
		isRunning = false;
		thread.interrupt();
		thread = null;
	}

}
