package com.connyay.squarzpies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.connyay.squarzpies.ViewMenu.downloadBack;
import com.connyay.squarzpies.ViewMenu.downloadImageBack;
import com.connyay.squarzpies.mail.GMailSender;

public class Order extends Activity implements OnClickListener {

	EditText name, email, phone, special;
	Button submit;

	Spinner appleSingle, appleDouble;
	Spinner bbqSingle, bbqDouble;
	Spinner breakfastSingle, breakfastDouble;
	Spinner chickenSingle, chickenDouble;
	Spinner chickenMushSingle, chickenMushDouble;
	Spinner lentilSingle, lentilDouble;
	Spinner pizzaSingle, pizzaDouble;
	Spinner spinSingle, spinDouble;
	Spinner shepSingle, shepDouble;

	Spinner potmSingle, potmDouble;
	TextView potmTV;

	EditText couponCode;

	RadioGroup pickupGroup;
	RadioButton pickupButton;

	String orderEmailText;
	String pickupLocation;
	String dailyDeal;

	String potmTitleOrder;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order);

		if (isInternetOn()) {
			new downloadBack().execute();
		}
		

		ArrayAdapter<CharSequence> ddAdapter = ArrayAdapter.createFromResource(
				this, R.array.dailydeal, android.R.layout.simple_spinner_item);
		ddAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> numAdapter = ArrayAdapter
				.createFromResource(this, R.array.numPies,
						R.layout.centerspinner);
		numAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner s = (Spinner) findViewById(R.id.ddSpin);
		s.setAdapter(ddAdapter);

		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				dailyDeal = (String) parent.getItemAtPosition(pos);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				dailyDeal = "None";
			}
		});

		name = (EditText) findViewById(R.id.orderNameET);
		email = (EditText) findViewById(R.id.orderEmailET);
		phone = (EditText) findViewById(R.id.orderPhoneET);
		special = (EditText) findViewById(R.id.specialET);

		appleSingle = (Spinner) findViewById(R.id.appleSingle);
		appleSingle.setAdapter(numAdapter);

		appleDouble = (Spinner) findViewById(R.id.appleDouble);
		appleDouble.setAdapter(numAdapter);

		bbqSingle = (Spinner) findViewById(R.id.bbqSingle);
		bbqSingle.setAdapter(numAdapter);

		bbqDouble = (Spinner) findViewById(R.id.bbqDouble);
		bbqDouble.setAdapter(numAdapter);

		breakfastSingle = (Spinner) findViewById(R.id.breakfastSingle);
		breakfastSingle.setAdapter(numAdapter);

		breakfastDouble = (Spinner) findViewById(R.id.breakfastDouble);
		breakfastDouble.setAdapter(numAdapter);

		chickenSingle = (Spinner) findViewById(R.id.chickenSingle);
		chickenSingle.setAdapter(numAdapter);

		chickenDouble = (Spinner) findViewById(R.id.chickenDouble);
		chickenDouble.setAdapter(numAdapter);

		chickenMushSingle = (Spinner) findViewById(R.id.chickenMushSingle);
		chickenMushSingle.setAdapter(numAdapter);

		chickenMushDouble = (Spinner) findViewById(R.id.chickenMushDouble);
		chickenMushDouble.setAdapter(numAdapter);

		lentilSingle = (Spinner) findViewById(R.id.lentilSingle);
		lentilSingle.setAdapter(numAdapter);

		lentilDouble = (Spinner) findViewById(R.id.lentilDouble);
		lentilDouble.setAdapter(numAdapter);

		pizzaSingle = (Spinner) findViewById(R.id.pizzaSingle);
		pizzaSingle.setAdapter(numAdapter);

		pizzaDouble = (Spinner) findViewById(R.id.pizzaDouble);
		pizzaDouble.setAdapter(numAdapter);

		spinSingle = (Spinner) findViewById(R.id.spinSingle);
		spinSingle.setAdapter(numAdapter);

		spinDouble = (Spinner) findViewById(R.id.spinDouble);
		spinDouble.setAdapter(numAdapter);

		shepSingle = (Spinner) findViewById(R.id.shepSingle);
		shepSingle.setAdapter(numAdapter);

		shepDouble = (Spinner) findViewById(R.id.shepDouble);
		shepDouble.setAdapter(numAdapter);

		potmSingle = (Spinner) findViewById(R.id.potmSingle);
		potmSingle.setAdapter(numAdapter);

		potmDouble = (Spinner) findViewById(R.id.potmDouble);
		potmDouble.setAdapter(numAdapter);

		pickupGroup = (RadioGroup) findViewById(R.id.pickup);

		couponCode = (EditText) findViewById(R.id.couponCodeET);

		submit = (Button) this.findViewById(R.id.submitOrder);

		submit.setOnClickListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		int selectedId = pickupGroup.getCheckedRadioButtonId();
		pickupButton = (RadioButton) findViewById(selectedId);

		if (!isInternetOn()) {
			showResult("Couldn't establish internet connection...");
			return;
		}
		
		if (name.getText().toString().equals("")) {
			showResult("Name is required. Please try again");
			return;
		} else if (email.getText().toString().equals("")) {
			showResult("Email is required. Please try again");
			return;
		} else if (phone.getText().toString().equals("")) {
			showResult("Phone number is required. Please try again");
			return;
		} else if (selectedId == -1) {
			showResult("Pickup Location is required. Please try again");
			return;
		}

		orderEmailText = "Name: " + name.getText().toString() + "\n"
				+ "Number: " + phone.getText().toString() + "\n"
				+ "Email: " + email.getText().toString() + "\n\n" + "Pickup Location: "
				+ pickupButton.getText().toString()
				+ "\nSpecial Instructions: " + special.getText().toString()

				+ "\n\n DailyDeal Coupon: " + dailyDeal + "\n Squarz Coupon: "
				+ couponCode.getText().toString()

				+ "\n\n Apple Singles: "
				+ appleSingle.getSelectedItem().toString()
				+ "\n Apple Doubles: "
				+ appleDouble.getSelectedItem().toString()

				+ "\n\n BBQ Pork Singles: "
				+ bbqSingle.getSelectedItem().toString()
				+ "\n BBQ Pork Doubles: "
				+ bbqDouble.getSelectedItem().toString()

				+ "\n\n Breakfast Singles: "
				+ breakfastSingle.getSelectedItem().toString()
				+ "\n Breakfast Doubles: "
				+ breakfastDouble.getSelectedItem().toString()

				+ "\n\n Chicken Singles: "
				+ chickenSingle.getSelectedItem().toString()
				+ "\n Chicken Doubles: "
				+ chickenDouble.getSelectedItem().toString()

				+ "\n\n Chicken & Mushroom Singles: "
				+ chickenMushSingle.getSelectedItem().toString()
				+ "\n Chicken & Mushroom Doubles: "
				+ chickenMushDouble.getSelectedItem().toString()

				+ "\n\n Lentil Curry Singles: "
				+ lentilSingle.getSelectedItem().toString()
				+ "\n Lentil Curry Doubles: "
				+ lentilDouble.getSelectedItem().toString()

				+ "\n\n Pepperoni Pizza Singles: "
				+ pizzaSingle.getSelectedItem().toString()
				+ "\n Pepperoni Pizza Doubles: "
				+ pizzaDouble.getSelectedItem().toString()

				+ "\n\n Spinach & Feta Singles: "
				+ spinSingle.getSelectedItem().toString()
				+ "\n Spinach & Feta Doubles: "
				+ spinDouble.getSelectedItem().toString()

				+ "\n\n Shepherd's Pie Singles: "
				+ shepSingle.getSelectedItem().toString()
				+ "\n Shepherd's Pie Doubles: "
				+ shepDouble.getSelectedItem().toString()

				+ "\n\n" + potmTitleOrder + " Singles: "
				+ potmSingle.getSelectedItem().toString() + "\n"
				+ potmTitleOrder + " Doubles: "
				+ potmDouble.getSelectedItem().toString();

		AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
		builder.setMessage("Are you ready to submit your order?");
		builder.setCancelable(false);

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				setUpEmail();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.order, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, ViewMenu.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
			builder.setTitle("You havn't completed your order!");
			builder.setMessage("Do you want to go back to the main screen? \n\nYour order will be lost...");
			builder.setCancelable(true);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							finish();
						}
					});
			builder.setNeutralButton("View Menu",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							Intent intent = new Intent(Order.this,
									ViewMenu.class);
							startActivity(intent);

						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();

						}
					});

			AlertDialog alert = builder.create();
			alert.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setUpEmail() {
		Resources res = getResources();
		String emailText;
		emailText = name.getText().toString() + " ["
				+ email.getText().toString() + "] \n\nSays: ";
		try {
			GMailSender sender = new GMailSender(
					res.getString(R.string.email), res.getString(R.string.password));
			sender.sendMail("New Order via Android App from "
					+ name.getText().toString(), orderEmailText,
					"squarzpies.android@gmail.com", "info@squarzpies.com");
		} catch (Exception e) {
			Log.e("SendMail", e.getMessage(), e);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(Order.this);
		builder.setMessage("Your order has been sent!");
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent();
				intent.setClass(Order.this, Dashboard.class);
				startActivity(intent);
				finish();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showResult(final String message) {
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Order.this);
				builder.setMessage(message).setPositiveButton("OK",
						new Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		};
		runOnUiThread(run);
	}

	public class downloadBack extends AsyncTask<Object, Object, Element> {

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
			potmTitleOrder = MenuFunctions.getValue(result, "title");

			potmTV = (TextView) findViewById(R.id.potmTV);
			potmTV.setText(MenuFunctions.getValue(result, "title"));

		}

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
