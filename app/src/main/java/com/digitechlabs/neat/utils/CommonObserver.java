package com.digitechlabs.neat.utils;


import java.util.Observable;

import android.content.Context;
public class CommonObserver extends Observable{
	private static final String TAG = "DEBUG";
	private String name = "First time i have this Text";
	private double j=0, totalAmount=0;
	private int i=0;
	Context ctx;
	//LocalDb localdb= new LocalDb();
	
	public CommonObserver() {
		//Log.d(TAG, "CommonObserver");
	//this.i++;
	//this.j=0;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		//Log.d(TAG, "CommonObserver getValue"+ this.j);
		//MyNetDatabase db = new MyNetDatabase(ctx);
		//db.open();
		//localdb.getPriceFromDB();
		if(j>0.0) 
			{
				return j;
			}
		else return j=0;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void deductValue(double i){
		
		j = i;
		//db.updateTotalCost(id, "minus", i);
		setChanged();
		notifyObservers();
	}
	
	public void setValue(double i) {
		
	    //this.i++;
	   j = i;
	    //db.updateTotalCost(id, "plus", i);
	    //this.totalAmount= this.j;
		//Log.d(TAG, "CommonObserver setValue of j: " + this.j);
		setChanged();
		notifyObservers();
	}
}
