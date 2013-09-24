package com.sibilant.kaunto;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity
{
	private LinearLayout lay;
	HorizontalListView listview;
	private int highest;
	private int[] netheight;

	private Integer[] netSal = {12,13,14,5,10,9,12,13,14,10,10,5};
	private String[] denomination = {"50","35","25","16","14","10","75","85","5","45","15","40"};


	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_test);
		lay = (LinearLayout)findViewById(R.id.linearlay);
		listview = (HorizontalListView) findViewById(R.id.listview);
        

    

        highest = (int) Collections.max(Arrays.asList(netSal));
        netheight = new int[netSal.length];

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
            

          tvcol2.setHeight(netheight[position]);
           title.setText(denomination[position]);
            

            nt.setText(df.format(netSal[position]/1));
                    
            tvcol2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Toast.makeText(TestActivity.this, "Denomination: "+title.getText().toString()+"\nQuantity: "+netSal[position], Toast.LENGTH_SHORT).show();
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
			h = (int) (lay.getHeight()*0.65);
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
		for(int i=0;i<netSal.length;i++) 
    	{
			netheight[i] = (int)((h*netSal[i])/highest);
    
    	}
    	listview.setAdapter(new bsAdapter(this,denomination));
	

	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	

}
	
