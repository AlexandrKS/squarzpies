package com.connyay.squarzpies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.connyay.squarzpies.mail.GMailSender;

public class Suggestions extends Activity {
	EditText name, email, body;

	String emailText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggestions);
		name = (EditText) findViewById(R.id.name1);
		email = (EditText) findViewById(R.id.email1);
		body = (EditText) findViewById(R.id.body);

		final Button send = (Button) this.findViewById(R.id.send);

		send.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (!isInternetOn()) {
					Toast toast = Toast.makeText(Suggestions.this,
							"Couldn't establish internet connection...",
							Toast.LENGTH_SHORT);
					toast.show();
					return;
				}

				if (name.getText().toString().equals("")) {
					Toast toast = Toast.makeText(Suggestions.this,
							"Your name can't be blank!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				} else if (email.getText().toString().equals("")) {
					Toast toast = Toast.makeText(Suggestions.this,
							"Email can't be blank!", Toast.LENGTH_SHORT);
					toast.show();
					return;
				} else if (body.getText().toString().equals("")) {
					Toast toast = Toast.makeText(Suggestions.this,
							"You can't have a blank suggestion!",
							Toast.LENGTH_SHORT);
					toast.show();
					return;
				}

				Resources res = getResources();

				emailText = name.getText().toString() + " ["
						+ email.getText().toString() + "] \n\nSays: "
						+ body.getText().toString();
				try {
					GMailSender sender = new GMailSender(res
							.getString(R.string.email), res
							.getString(R.string.password));
					sender.sendMail("New Suggestion via Android App from "
							+ name.getText().toString(), emailText,
							"squarzpies.android@gmail.com",
							"info@squarzpies.com");
				} catch (Exception e) {

					return;
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(
						Suggestions.this);
				builder.setMessage("Your suggestion has been sent!");
				builder.setCancelable(false);
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Intent intent = new Intent();
								intent.setClass(Suggestions.this,
										Dashboard.class);
								startActivity(intent);
								finish();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			}
		});

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