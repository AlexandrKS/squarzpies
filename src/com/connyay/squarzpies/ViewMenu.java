package com.connyay.squarzpies;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewMenu extends Activity {
	TextView potmHead, potmCopy;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		if (isInternetOn()) {
			new downloadBack().execute();

			new downloadImageBack().execute();
		}
		

	}

	public class downloadBack extends AsyncTask<Object, Integer, Element> {

		@Override
		protected Element doInBackground(Object... params) {

			try {
				String xml = MenuFunctions.getXML();
				Document doc = MenuFunctions.XMLfromString(xml);

				NodeList nodes = doc.getElementsByTagName("POTM");

				Element e = (Element) nodes.item(0);

				return e;

			} catch (NullPointerException error) {

			}
			return null;

		}

		protected void onPostExecute(Element result) {
			potmHead = (TextView) findViewById(R.id.potmHead);
			potmCopy = (TextView) findViewById(R.id.potmCopy);
			potmHead.setText(MenuFunctions.getValue(result, "title"));
			potmCopy.setText(MenuFunctions.getValue(result, "description"));

		}

	}

	public class downloadImageBack extends AsyncTask<String, Bitmap, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			try {

				Bitmap bitmap = BitmapFactory
						.decodeStream((InputStream) new URL(
								"http://squarzpies.com/android/POTM.jpg")
								.getContent());
				return bitmap;
			} catch (MalformedURLException error) {
				return null;
			} catch (IOException error) {
				return null;
			} catch (NullPointerException error) {
				return null;
			}

		}

		protected void onPostExecute(Bitmap result) {
			ImageView i = (ImageView) findViewById(R.id.potmIV);
			i.setImageBitmap(result);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, Order.class);
		startActivity(intent);
		finish();
		return super.onOptionsItemSelected(item);
	}
	
	public final boolean isInternetOn() {
		ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			// MESSAGE TO SCREEN FOR TESTING (IF REQ)
			// Toast.makeText(this, connectionType + ” connected”,
			// Toast.LENGTH_SHORT).show();
			return true;
		} else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			// System.out.println(“Not Connected”);
			return false;
		}
		return false;
	}
}