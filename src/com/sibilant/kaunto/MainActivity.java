package com.sibilant.kaunto;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	public static TextView  OutputText ;	
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView  text = (EditText) findViewById(R.id.currencyview);
		 SharedPreferences  prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	     String currencyvalue = prefs.getString("Currency","£");	     
		 text.setText(currencyvalue);
					
	}
	public void settings(View view)
	{
		startActivity(new Intent(MainActivity.this , SettingsActivity.class) );
		//setContentView(R.layout.activity_settings);
	}
 	public void stats(View view)
 	{
 		startActivity(new Intent(MainActivity.this, TestActivity.class));
 	}
	public void OnCalculate(View view)
	{
		//Hide keyboard 
		InputMethodManager imm = (InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);	
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		
		Calculate();
		ShowResult();
		

		
	}
	
	private void Calculate() {
		
		/* Start - Set Parameters */ 
		EditText txtName=(EditText)this.findViewById(R.id.amount);
		int[] hArray = {50, 35, 25, 16, 14};
		int[] nArray = {100, 100, 100, 100, 100};
		int[] qArray = {0, 0, 0, 0, 0};
		int[] rArray = {0, 0, 0, 0, 0};
		int i = 0, k = 0, btemp = 0, B = 0;
		B=Integer.parseInt(txtName.getText().toString());
		int j = hArray.length;
		int test = 0;
		int n = 0;
		int a = 0, b = 0, t = 0, d = 0, e = 0, x = 0, l = 0, h = 0, p = 0;
		int[][] resultArray = new int[100][6];
		int z = 0, y = 0;
		

/* End - Set Parameters */ 

		for(i = k; i < j; i++) {
			
			MainActivity.loadResultMethod(B, qArray, j, p, h, resultArray);
			
			h = h + 1;
			qArray[i] = B / hArray[i];
			rArray[i] = B % hArray[i];
			
			if (qArray[i] > nArray[i]) {

				rArray[i] = rArray[i] + ((qArray[i] - nArray[i]) * hArray[i]);
				qArray[i] = nArray[i];
				
			} else if (qArray[i] <= nArray[i] & rArray[i] == 0) {
				
				B = 0;
				MainActivity.loadResultMethod(B, qArray, j, p, h, resultArray);
				for (z = 0; z < h + 1; z++) {
					for (y = 0; y < j + 1; y++) {
					
						//MainActivity.OutputText.setText(" resultArray[" + z + "][" + y + "]:" + resultArray[z][y]);
						
						
					}
				}
				MainActivity.resultMethod(i, k, j, B, qArray, hArray, resultArray);

			} else if (qArray[i] <= nArray[i] & rArray[i] != 0) {

				btemp = B;
				B = rArray[i];
				
				if (B < 14){
				
					for(n = j - 1 ; n > 0 ; n--) {
					
						if (qArray[n] > 0) {
							
							if ((n == j - 1) & (qArray[n] > 0)) {
					
								B = (hArray[n] * qArray[n]) + B;
								qArray[n] = 0;
								k = n + 1;
							
							} else {
							
								qArray[n] = qArray[n] - 1;
								B = hArray[n] + B;
								k = n + 1;
								i = n;
								n = -1;
							}
						}
					}
					
					x = 0;
					
					for(e = j - 1 ; e > -1; e--) {

						if (qArray[e] > 0) {

						    if ((e > 0) | (k < j)) {
								x = 1;
							}
							
							if ((qArray[0] > 0) & (x == 0)) {
								l = 1;
							}
							
							if ((qArray[0] > 0) & (x == 0) & (l == 1)) {
								l = 0;
								qArray[0] = qArray[0] - 1;
								B = hArray[0] + B;
								i = k = 0;
							}
						}
					}
					x = 0;
				}
			} else {
				System.out.println("Un-Trapped Condition");
			}
		}
		System.out.println("here : " + i);		
		MainActivity.resultMethod(i, k, j, B, qArray, hArray, resultArray);
	}
	

protected static void resultMethod(int i, int k, int j, int B,
	int[] qArray, int[] hArray, int[][] resultArray) {
System.out.println("Give ");
i = 0;
k = 0;
String tempPrint, finalPrint = "";
for(i = k ; i < j ;i = i + 1) {
	tempPrint = qArray[i] + " tokens of denomination " + hArray[i] + "\n";
	
	finalPrint = finalPrint + tempPrint;
	
}
finalPrint = finalPrint + " and a cash amount of " + B;

//MainActivity.OutputText.setText(finalPrint);
 
//MainActivity.ShowResult();

}


protected static void loadResultMethod(int B, int[] qArray, int j, int p,
	int h, int[][] resultArray) {
System.out.println(" here sir: " + B + " : " + j + " : " + p + " : " + h);
for (p = 0; p < j + 1; p++) {
	if (p == j){
		System.out.println(" here sir:");
		resultArray[h][p] = B;
	} else {
		resultArray[h][p] = qArray[p];
	}
}

}


protected  void ShowResult() {

     CharSequence items [] = {"option1" ,"option2" ,"option3"};
     
   //  CharSequence item[] = new CharSequence[items.length+1];
     
    //  items.add("opion4");
	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setTitle("Denomination");
	     builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
	      @Override
	      public void onClick(DialogInterface dialog, int Choice)
	      {	                
	    	  dialog.dismiss();


	      }
	          
	    });
	     
	     AlertDialog alert = builder.create();
	        alert.show();
	
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
