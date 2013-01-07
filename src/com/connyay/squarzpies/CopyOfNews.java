package com.connyay.squarzpies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CopyOfNews extends ListActivity {
	Document doc;
	 String xml;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
      
        try {

       
		try {
			xml = new downloadXMLBack().execute(xml).get();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ExecutionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
		try {
			doc = new downloadNewsBack().execute(xml).get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        		
        		
        
                
        int numResults = NewsFunctions.numResults(doc);
        
        if((numResults <= 0)){
        	Toast.makeText(CopyOfNews.this, "Nothing new right now... Check back soon!", Toast.LENGTH_LONG).show();  
        	finish();
        }
                
		NodeList nodes = doc.getElementsByTagName("post");
					
		for (int i = 0; i < nodes.getLength(); i++) {							
			HashMap<String, String> map = new HashMap<String, String>();	
			
			Element e = (Element)nodes.item(i);
			map.put("id", NewsFunctions.getValue(e, "id"));
        	map.put("title", NewsFunctions.getValue(e, "title"));
        	map.put("date", "Posted on: " + NewsFunctions.getValue(e, "date"));
        	map.put("content", NewsFunctions.getValue(e, "content"));
        	mylist.add(map);			
		}		
       
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.posts, 
                        new String[] { "title", "date", "content" }, 
                        new int[] { R.id.item_title, R.id.item_date, R.id.item_content });
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
        		@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);	        		
        		Toast.makeText(CopyOfNews.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_LONG).show(); 

			}
		});
        
        } catch (NullPointerException error) {

			return;
		}
    }
	public class downloadXMLBack extends AsyncTask<String, Object, String> {

		@Override
		protected String doInBackground(String... params) {

			return NewsFunctions.getXML();
		}

	}  
	public class downloadNewsBack extends AsyncTask<String, Object, Document> {

		@Override
		protected Document doInBackground(String... params) {

			return NewsFunctions.XMLfromString(params[0]);
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.newsmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	
}