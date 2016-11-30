package com.digitechlabs.neat.home;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class Offeritem {
	private Bitmap image;
	private String url,title,description,termscondition;


	public Offeritem(Bitmap image, String url, String title,String description,String termscondition ) {
		super();
		this.image = image;

		this.url = url;
		this.title = title;
		this.description = description;
		this.termscondition = termscondition;

	}


	public Bitmap getImage() {
		return image;
	}


	public void setImage(Bitmap image) {
		this.image = image;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTermscondition() {
		return termscondition;
	}


	public void setTermscondition(String termscondition) {
		this.termscondition = termscondition;
	}

	

	
}
