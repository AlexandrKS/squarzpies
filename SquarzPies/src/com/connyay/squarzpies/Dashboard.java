package com.connyay.squarzpies;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends Activity implements OnClickListener {
	Button news, findUs, about, menu, suggestions, newsLetter;
	TextView order;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);

		news = (Button) findViewById(R.id.news);
		news.setOnClickListener(this);
		findUs = (Button) findViewById(R.id.findUs);
		findUs.setOnClickListener(this);
		about = (Button) findViewById(R.id.about);
		about.setOnClickListener(this);
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		suggestions = (Button) findViewById(R.id.suggestion);
		suggestions.setOnClickListener(this);
		newsLetter = (Button) findViewById(R.id.newsLetter);
		newsLetter.setOnClickListener(this);

		order = (TextView) findViewById(R.id.order);
		order.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (v == news) {
			Intent intent = new Intent(this, News.class);
			startActivity(intent);
		} else if (v == findUs) {
			Intent intent = new Intent(this, FindUs.class);
			startActivity(intent);
		} else if (v == about) {
			Intent intent = new Intent(this, About.class);
			startActivity(intent);
		} else if (v == menu) {
			Intent intent = new Intent(this, ViewMenu.class);
			startActivity(intent);
		} else if (v == suggestions) {
			Intent intent = new Intent(this, Suggestions.class);
			startActivity(intent);
		} else if (v == newsLetter) {
			Intent intent = new Intent(this, Newsletter.class);
			startActivity(intent);
		} else if (v == order) {
			Intent intent = new Intent(this, Order.class);
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.connmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String connyay_url = "http://connorhindley.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(connyay_url));
		startActivity(i);
		return true;

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	// Fires after the OnStop() state
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			trimCache(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void trimCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void clearApplicationData() {
		File cache = getCacheDir();
		File appDir = new File(cache.getParent());
		if (appDir.exists()) {
			String[] children = appDir.list();
			for (String s : children) {
				if (!s.equals("lib")) {
					deleteDir(new File(appDir, s));
				}
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
}