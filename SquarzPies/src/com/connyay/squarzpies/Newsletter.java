package com.connyay.squarzpies;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.MergeFieldListUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Newsletter extends Activity implements OnClickListener {

	Button subscribe;
	EditText name, email;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.subscribe); // bind the layout to the activity

		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		// attach handler to the subscribe button
		subscribe = (Button) this.findViewById(R.id.subscribe);
		subscribe.setOnClickListener(this);
	}

	public void onClick(View v) {
		Log.d(this.getClass().getName(), "Clicked: " + v.toString());
		
		if (name.getText().toString().equals("")) {
			// Name is required
			showResult("Name is required. Please try again");
			return;
		}
		else if (email.getText().toString().equals("")) {
			// Email is required
			showResult("Email is required. Please try again");
			return;
		}
			
		

		if (v == subscribe) {
			// show progress dialog
			final ProgressDialog progressDialog = ProgressDialog.show(this,
					"Subscribing now", "Please wait...");

			Runnable run = new Runnable() {
				public void run() {
					
					
					if (!email.getText().toString().equals("")
							&& email.getText().toString().trim().length() > 0) {
						addToList(email.getText().toString(), progressDialog);
					}
					else {
						String message;
						message = "Signup failed: Email can not be blank. Please try again.";
						progressDialog.dismiss();
						showResult(message);
					}
				}
			};
			(new Thread(run)).start();
		}

	}

	private void addToList(String emailAddy, final ProgressDialog progressDialog) {
		
		String[] fullName = name.getText().toString().split(" ");
		
		
		MergeFieldListUtil merges = new MergeFieldListUtil();
		merges.addField("FNAME", fullName[0]);
		if (fullName.length > 1)
			merges.addField("LNAME", fullName[1]);
		
		

		ListMethods listMethods = new ListMethods(getResources().getText(
				R.string.mc_api_key));
		String message = "Signup successful!";
		
		try {
			listMethods.listSubscribe(getText(R.string.mc_list_id).toString(),
					emailAddy, merges);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp",
					"Exception subscribing person: " + e.getMessage());
			String test = e.getMessage();
			if (test.contains("already subscribed")) {
				message = "Signup failed: it appears that you are already subscribed";
			}
			else if (test.contains("Invalid Email")) {
				message = "Signup failed: You have entered an invalid email. Please try again.";
			}
			else {
				message = "Signup failed: " + e.getMessage();
			}
		} finally {
			progressDialog.dismiss();
			showResult(message);
		}
		}
			

	

	private void showResult(final String message) {
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(Newsletter.this);
				builder.setMessage(message).setPositiveButton("OK", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		};
		runOnUiThread(run);
	}
}