package com.connyay.squarzpies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.connyay.squarzpies.mail.GMailSender;

public class CopyOfOrder extends Activity implements OnClickListener {

	EditText name, email, phone, special;
	Button submit;

	EditText appleSingle, appleDouble;
	EditText bbqSingle, bbqDouble;
	EditText breakfastSingle, breakfastDouble;
	EditText chickenSingle, chickenDouble;
	EditText chickenMushSingle, chickenMushDouble;
	EditText lentilSingle, lentilDouble;
	EditText pizzaSingle, pizzaDouble;
	EditText spinSingle, spinDouble;
	EditText shepSingle, shepDouble;

	EditText potmSingle, potmDouble;
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

		new downloadBack().execute();

		ArrayAdapter<CharSequence> ddAdapter = ArrayAdapter.createFromResource(
				this, R.array.dailydeal, android.R.layout.simple_spinner_item);
		ddAdapter
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

		appleSingle = (EditText) findViewById(R.id.appleSingle);
		appleDouble = (EditText) findViewById(R.id.appleDouble);

		bbqSingle = (EditText) findViewById(R.id.bbqSingle);
		bbqDouble = (EditText) findViewById(R.id.bbqDouble);

		breakfastSingle = (EditText) findViewById(R.id.breakfastSingle);
		breakfastDouble = (EditText) findViewById(R.id.breakfastDouble);

		chickenSingle = (EditText) findViewById(R.id.chickenSingle);
		chickenDouble = (EditText) findViewById(R.id.chickenDouble);

		chickenMushSingle = (EditText) findViewById(R.id.chickenMushSingle);
		chickenMushDouble = (EditText) findViewById(R.id.chickenMushDouble);

		lentilSingle = (EditText) findViewById(R.id.lentilSingle);
		lentilDouble = (EditText) findViewById(R.id.lentilDouble);

		pizzaSingle = (EditText) findViewById(R.id.pizzaSingle);
		pizzaDouble = (EditText) findViewById(R.id.pizzaDouble);

		spinSingle = (EditText) findViewById(R.id.spinSingle);
		spinDouble = (EditText) findViewById(R.id.spinDouble);

		shepSingle = (EditText) findViewById(R.id.shepSingle);
		shepDouble = (EditText) findViewById(R.id.shepDouble);

		potmSingle = (EditText) findViewById(R.id.potmSingle);
		potmDouble = (EditText) findViewById(R.id.potmDouble);

		pickupGroup = (RadioGroup) findViewById(R.id.pickup);

		couponCode = (EditText) findViewById(R.id.couponCodeET);

		submit = (Button) this.findViewById(R.id.submitOrder);

		submit.setOnClickListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		int selectedId = pickupGroup.getCheckedRadioButtonId();
		pickupButton = (RadioButton) findViewById(selectedId);

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

		orderEmailText = name.getText().toString() + "\n"
				+ phone.getText().toString() + "\n"
				+ email.getText().toString() + "\n\n" + "Pickup Location: "
				+ pickupButton.getText().toString()
				+ "\nSpecial Instructions: " + special.getText().toString()

				+ "\n\n DailyDeal Coupon: " + dailyDeal + "\n Squarz Coupon: "
				+ couponCode.getText().toString()

				+ "\n\n Apple Singles: " + appleSingle.getText().toString()
				+ "\n Apple Doubles: " + appleDouble.getText().toString()

				+ "\n\n BBQ Pork Singles: " + bbqSingle.getText().toString()
				+ "\n BBQ Pork Doubles: " + bbqDouble.getText().toString()

				+ "\n\n Breakfast Singles: "
				+ breakfastSingle.getText().toString()
				+ "\n Breakfast Doubles: "
				+ breakfastDouble.getText().toString()

				+ "\n\n Chicken Singles: " + chickenSingle.getText().toString()
				+ "\n Chicken Doubles: " + chickenDouble.getText().toString()

				+ "\n\n Chicken & Mushroom Singles: "
				+ chickenMushSingle.getText().toString()
				+ "\n Chicken & Mushroom Doubles: "
				+ chickenMushDouble.getText().toString()

				+ "\n\n Lentil Curry Singles: "
				+ lentilSingle.getText().toString()
				+ "\n Lentil Curry Doubles: "
				+ lentilDouble.getText().toString()

				+ "\n\n Pepperoni Pizza Singles: "
				+ pizzaSingle.getText().toString()
				+ "\n Pepperoni Pizza Doubles: "
				+ pizzaDouble.getText().toString()

				+ "\n\n Spinach & Feta Singles: "
				+ spinSingle.getText().toString()
				+ "\n Spinach & Feta Doubles: "
				+ spinDouble.getText().toString()

				+ "\n\n Shepherd's Pie Singles: "
				+ shepSingle.getText().toString()
				+ "\n Shepherd's Pie Doubles: "
				+ shepDouble.getText().toString()

				+ "\n\n" + potmTitleOrder + " Singles: "
				+ potmSingle.getText().toString() + "\n" + potmTitleOrder
				+ " Doubles: " + potmDouble.getText().toString();

		AlertDialog.Builder builder = new AlertDialog.Builder(CopyOfOrder.this);
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
			AlertDialog.Builder builder = new AlertDialog.Builder(CopyOfOrder.this);
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

							Intent intent = new Intent(CopyOfOrder.this,
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

		String emailText;
		emailText = name.getText().toString() + " ["
				+ email.getText().toString() + "] \n\nSays: ";
		try {
			GMailSender sender = new GMailSender(
					"squarzpies.android@gmail.com", "squarz123");
			sender.sendMail("New Order via Android App from "
					+ name.getText().toString(), orderEmailText,
					"squarzpies.android@gmail.com", "info@squarzpies.com");
		} catch (Exception e) {
			Log.e("SendMail", e.getMessage(), e);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(CopyOfOrder.this);
		builder.setMessage("Your order has been sent!");
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent();
				intent.setClass(CopyOfOrder.this, Dashboard.class);
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
						CopyOfOrder.this);
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
}
