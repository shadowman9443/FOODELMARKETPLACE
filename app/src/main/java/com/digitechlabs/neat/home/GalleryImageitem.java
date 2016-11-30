package com.digitechlabs.neat.home;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class GalleryImageitem {
	private Bitmap image;
	private String url;


	public GalleryImageitem(Bitmap image, String url) {
		super();
		this.image = image;

		this.url = url;

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

	
}
