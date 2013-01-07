package com.connyay.squarzpies.mail;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.connyay.squarzpies.R;


public class MailSenderActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestions);

        final Button send = (Button) this.findViewById(R.id.subscribe);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {   
                    GMailSender sender = new GMailSender("username@gmail.com", "password");
                    sender.sendMail("This is Subject",   
                            "This is Body",   
                            "user@gmail.com",   
                            "user@yahoo.com");   
                } catch (Exception e) {  
                    Log.e("SendMail", e.getMessage(), e);   
                } 

            }
        });

    }
}
