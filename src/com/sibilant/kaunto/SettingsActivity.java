package com.sibilant.kaunto;




import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import com.sibilant.kaunto.TestActivity.bsAdapter;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
 
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;



public class SettingsActivity extends Activity {

    private LinearLayout lay;
	HorizontalListView listview;
	private int highest;
	private int[] netheight;
  // private Integer[] quantity = {9,13,14,5,10,9,12,13,14,10,10,5};
//	private String[] denomination = {"5","35","25","16","14","50","35","25","16","14","16","14"};
	private Integer[] quantity ;
	private String[] denomination ;
	
	
	private int i, j;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// updateGraph();
		final SeekBar seekBardeno = (SeekBar)findViewById(R.id.seekBarDeno); 	  
		final EditText seekBardenoValue = (EditText)findViewById(R.id.editTextDeno);  
		final SeekBar seekBarqty = (SeekBar)findViewById(R.id.seekBarQty); 
		final EditText seekBarqtyValue = (EditText)findViewById(R.id.editTextqty);
		//Currency spinner code
		  ArrayAdapter<CharSequence> adapterCurrency = ArrayAdapter.createFromResource(this,
			       R.array.Currency , android.R.layout.simple_spinner_item );
		  adapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  final  Spinner Currency = (Spinner) findViewById(R.id.Currencyspinner);
			Currency.setAdapter(adapterCurrency); 
			String savedValue=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Currency","");
			for(int i=0;i<4;i++)
		    if(savedValue.equals(Currency.getItemAtPosition(i).toString())){
		    	Currency.setSelection(i);
		         break;
		    }
		   Context mContext = this;
		    SharedPreferences prefs = mContext .getSharedPreferences("Denomination", 0); 
		    //prefs.edit().clear().commit(); 
		    int sizeOfGraph = prefs.getAll().size();
		    if (sizeOfGraph !=0)
		    {
		     quantity = new Integer[sizeOfGraph/2];
		     denomination =new String[sizeOfGraph/2];				
	    	 for (i = 0; i < sizeOfGraph; i = i + 2)
	          {	    			
	    		quantity[j] = prefs.getInt("Qty_"+ i, 0);
		        denomination[j] = prefs.getString("Deno_" + i, "0");
	            j = j + 1;
	          }
		    }
		    else
		    {		    	
		        quantity = new Integer[1];
			    denomination =new String[1];		
		        quantity[0] =10;
		        denomination[0] = "50";		    	
		    }
	  
		    lay = (LinearLayout)findViewById(R.id.linearlay);
			listview = (HorizontalListView) findViewById(R.id.listview);
		    highest = (int) Collections.max(Arrays.asList(quantity));
		    netheight = new int[quantity.length];
		    Currency.setOnItemSelectedListener(new OnItemSelectedListener() 
			 {
	                public void onItemSelected(
	                        AdapterView<?> parent, View view, int position, long id)
	                            {
	                             	 Object obj = parent.getItemAtPosition(position);               	 
	                             	 SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());                           
	                             	 prefs.edit().putString("Currency", obj.toString()).commit();                	
	                             }
	                
	                @Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
				   }});
		          //Denomination seekbar code	   
		          seekBardeno.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 
		     	   @Override 
		     	   public void onProgressChanged(SeekBar seekBar, int progress, 
		     	     boolean fromUser) {    		   	 
		     	       
		     		 seekBardenoValue.setText(String.valueOf(progress));
	 
		     	   } 
		     	  @Override 
		   	   public void onStartTrackingTouch(SeekBar seekBar) { 
		   	    // TODO Auto-generated method stub 
		   	   } 

		   	   @Override 
		   	   public void onStopTrackingTouch(SeekBar seekBar) { 
		   	    // TODO Auto-generated method stub 
		   	   } 
		   	       }); 
		   //Denomination EditText code
		     seekBardenoValue.addTextChangedListener(new TextWatcher(){
		    	 @Override
		    	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	    }
		    	    @Override
		    	    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	    }
		    	    @Override
		    	    public void afterTextChanged(Editable s) {
		    	        try{
		    	            //Update Seekbar value after entering a number
		    	        	seekBardeno.setProgress(Integer.parseInt(s.toString()));
		    	        	int textlengh = seekBardenoValue.getText().length();
		    	        	seekBardenoValue.setSelection(textlengh, textlengh);
		    	        	if (seekBardenoValue.getText().length() >100)
		    	        	{
		    	        		seekBardenoValue.setText(100);
		    	        	}
		    	        		
		    	        } catch(Exception ex) {}
		    	    }
		    	});   
		         
		   	  
		//Quantity seekbar code 	  
	    seekBarqty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 
	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar2, int progress, 
	    	     boolean fromUser) {    		   	 
	    	       seekBarqtyValue.setText(String.valueOf(progress));

	    	   } 
	    	  @Override 
	  	   public void onStartTrackingTouch(SeekBar seekBar2) { 
	  	    // TODO Auto-generated method stub 
	  	   } 

	  	   @Override 
	  	   public void onStopTrackingTouch(SeekBar seekBar2) { 
	  	    // TODO Auto-generated method stub 
	  	   } 
	  	       }); 
	  	   
		//Quantity EditText code
	    seekBarqtyValue.addTextChangedListener(new TextWatcher(){
	   	 @Override
	   	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	   	    }
	   	    @Override
	   	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	   	    }
	   	    @Override
	   	    public void afterTextChanged(Editable s) {
	   	        try{
	   	            //Update Seekbar value after entering a number
	   	        	seekBarqty.setProgress(Integer.parseInt(s.toString()));
	   	        	int textlengh = seekBarqtyValue.getText().length();
	   	        	seekBarqtyValue.setSelection(textlengh, textlengh);
	   	        	if (seekBarqtyValue.getText().length() >100)
		        	{
	   	        		seekBarqtyValue.setText(100);
		        	}
	   	        } catch(Exception ex) {}
	   	    }
	   	});      
 
	}       
	  
	
	public class bsAdapter extends BaseAdapter
    {
        Activity cntx;
        String[] array;

        public bsAdapter(Activity context,String[] arr)

        {

            this.cntx=context;
            this.array = arr;
        }

        public int getCount()
        {

            return array.length;
        }

        public Object getItem(int position)
        {

            return array[position];
        }

        public long getItemId(int position)
        {

            return array.length;
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View row=null;
            LayoutInflater inflater=cntx.getLayoutInflater();
            row=inflater.inflate(R.layout.simplerow, null);
            
            DecimalFormat df = new DecimalFormat("#.##");
            final TextView title	=	(TextView)row.findViewById(R.id.title);

            TextView tvcol2	=	(TextView)row.findViewById(R.id.colortext02);          

            TextView nt		=	(TextView)row.findViewById(R.id.text02);
          //  Button DeleteBar		=	(Button)row.findViewById(R.id.Deletebutton);            

          tvcol2.setHeight(netheight[position]);
           title.setText(denomination[position]);            

            nt.setText(df.format(quantity[position]/1));
                    
            tvcol2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {						  					 
					Toast.makeText(SettingsActivity.this, "Denomination: "+title.getText().toString()+"\nQuantity: "+quantity[position], Toast.LENGTH_SHORT).show();					
				//	Button DeleteBar=	(Button)v.findViewById(R.id.Deletebutton);
					//DeleteBar.setVisibility(v.VISIBLE);
		            
				}		
				
				
			});
            
        return row;
        }
    }

	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
     
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }
	private void updateSizeInfo() {

		int h;
		if(getResources().getConfiguration().orientation == 1)
		{
			h = (int) (lay.getHeight()*0.60);
			if(h == 0)
			{
				h = 200;
			}
		}
		else
		{
			h = (int) (lay.getHeight()*0.5);
			if(h == 0)
			{
				h = 130;
			}
		}
		for(int i=0;i<quantity.length;i++) 
    	{
			netheight[i] = (int)((h*quantity[i])/highest);
    
    	}
    	listview.setAdapter(new bsAdapter(this,denomination));	

	}
	
	public void update(View view)
	{
	
	AdddenotosharedPref();	
	 Intent intent = getIntent();
	    finish();
	    startActivity(intent);	
	} 	

	public  void AdddenotosharedPref()
	{
	 ContextWrapper mContext = this;
	 final EditText seekBardenoValue = (EditText)findViewById(R.id.editTextDeno);
	 String denovalue = seekBardenoValue.getText().toString();		 
	 final EditText seekBarqtyValue = (EditText)findViewById(R.id.editTextqty);
     int qtyvalue = Integer.valueOf(seekBarqtyValue.getText().toString()); 
	  SharedPreferences prefs = mContext .getSharedPreferences("Denomination", 0);
    //	prefs.edit().clear().commit();  	    	 
	   SharedPreferences.Editor editor = prefs.edit(); 
	    int count = prefs.getAll().size();
	    for (i = 0; i < count; i = i + 2)
        {	    			
  		 String duplicate = prefs.getString("Deno_" + i, "0");
  		   if(duplicate.equals(denovalue))
  		   {
  			int updateqty = prefs.getInt("Qty_" + i, 0);
  			int newupdateqty = updateqty + qtyvalue;
  			 editor.putInt("Qty_" + i +"", newupdateqty);
  			 editor.commit(); 
  			return ;
  		   }
  		   
          j = j + 1;
        }	    
	    editor.putString("Deno_" + count + "", denovalue);		 
        editor.putInt("Qty_" + count + "", qtyvalue);
	     count++;
	      editor.commit(); 
  	   
	}

 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	//Go to Main activity on back Button
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this, MainActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


	
	



}


