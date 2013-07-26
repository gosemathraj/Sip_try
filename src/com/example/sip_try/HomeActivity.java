package com.example.sip_try;

//import android.os.Build;

import java.text.ParseException;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
//import android.app.Activity;
//import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//import com.example.sip_try.UserSettingsFragment;
import com.example.sip_try.UserSettingsFragment.UserSettingsInterface;

public class HomeActivity extends FragmentActivity implements UserSettingsInterface{
	
	public final static int USER_SETTINGS = 1;
	public final static int ADVANCED_SETTINGS = 2;

	private String username;
	private String pass;
	private String domain;
	private String server;
	
    public SipManager manager = null;
    public SipProfile me = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
                
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        initializeManager();
        
    }

    @Override
    public void onStart() {
        super.onStart();
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        initializeManager();
    }
    
    public void onMyButtonClick(View view){
    	//UserSettingsFragment fragment = (UserSettingsFragment) getSupportFragmentManager().findFragmentById(R.id.user_settings_view);
    	
    	Toast.makeText(this, "POTOI " + username, Toast.LENGTH_SHORT).show();
    }
    
    public void click_register(View view){
    	//Toast.makeText(this, "Register clicked!", Toast.LENGTH_SHORT).show();
    	//Intent intent = new Intent(this, RegisterActivity.class);
    	//startActivity(intent);
    	
    	//Replace one fragment with another
    	
        // Create an instance of UserSettingsFragment
    	UserSettingsFragment usersettings = new UserSettingsFragment();

    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    	// Replace whatever is in the fragment_container view with this fragment,
    	// and add the transaction to the back stack so the user can navigate back
    	transaction.replace(R.id.container, usersettings);
    	transaction.addToBackStack(null);

    	// Commit the transaction
    	transaction.commit();
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
    	menu.add(0, USER_SETTINGS, 0, "User Settings");
    	menu.add(0, ADVANCED_SETTINGS, 0, "Advanced Settings");
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case USER_SETTINGS:
        	//
        	
        	break;
        case ADVANCED_SETTINGS:
        	//
        	break;
        }
        return true;
    }

    public void setData(String u, String  p, String  d , String s){
    	this.username = u;
    	this.pass = p;
    	this.domain = d;
    	this.server = s;
    }
    public void setUsername(String u){
    	this.username = u;
    }
    public void setPass(String p){
    	this.pass = p;
    }
    public void setDomain(String d){
    	this.domain = d;
    }
    public void setServer(String s){
    	this.server = s;
    }
    
    public void initializeManager() {
        if(manager == null) {
          manager = SipManager.newInstance(this);
        }
    }
    
    public void initiateProfile(){
    	if(username.length()==0 || pass.length()==0 || domain.length()==0 || server.length()==0){
    		Toast.makeText(this, "Update settings", Toast.LENGTH_SHORT).show();
    	}else{
	    	try {
	    		username = "bob";
	    		pass = "abc456";
	    		domain = "cicore219.icaro.ciens.ucv.ve";
	    		server = "190.169.74.219";
	            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
	            builder.setPassword(pass);
	            me = builder.build();
	            
	            Intent i = new Intent();
	            i.setAction("android.SipDemo.INCOMING_CALL");
	            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
	            manager.open(me, pi, null);
	            
	            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
	                public void onRegistering(String localProfileUri) {
	                    
	                }
	
	                public void onRegistrationDone(String localProfileUri, long expiryTime) {
	                    
	                }
	
	                public void onRegistrationFailed(String localProfileUri, int errorCode,
	                        String errorMessage) {
	                    
	                }
	            });
	            
	    	}catch(ParseException pe){
	            
	        }catch (SipException se) {
	          
	        }
    	}
    }
    
	@Override
	public void onSaveClick() {
		// TODO Auto-generated method stub
		//UserSettingsFragment fragment = (UserSettingsFragment) getSupportFragmentManager().findFragmentById(R.id.user_settings_view);
		EditText aux = (EditText) findViewById(R.id.edit_username);
		setUsername(aux.getText().toString());
		aux = (EditText) findViewById(R.id.edit_password);
		setPass(aux.getText().toString());
		aux = (EditText) findViewById(R.id.edit_domain);
		setDomain(aux.getText().toString());
		aux = (EditText) findViewById(R.id.edit_server);
		setServer(aux.getText().toString());
		
		//Toast.makeText(this, "POTOI " + username, Toast.LENGTH_SHORT).show();
		initiateProfile();
		
	}   
    
}
