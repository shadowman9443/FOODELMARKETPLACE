package com.digitechlabs.neat.home;

import java.util.ArrayList;

public class GalleryConfig {
	private ArrayList<String> gallery_list = new ArrayList<String>();

	public ArrayList<String> getGallery_list() {
		return gallery_list;
	}

	public void setGallery_list(ArrayList<String> gallery_list) {
		this.gallery_list = gallery_list;
	}

	public GalleryConfig() {
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/ara.png");
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/cikti5.png");
		gallery_list
				.add("http://www.minare.net/wp-content/uploads/2011/01/android_ipod_touch_4.jpg");
		gallery_list
				.add("http://blog.fommy.com/wp-content/uploads/2008/10/android-wallpaper1_1024x768.png");
		gallery_list
				.add("http://www.buynetbookcomputer.com/android-netbook-images/android-netbook-big.jpg");
		gallery_list
				.add("http://www.sharepointhoster.com/wp-content/uploads/2011/03/android_apps.jpeg");
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/customList.png");
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/handler.png");
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/xml.png");
		gallery_list
				.add("http://www.ethemsulan.com/wp-content/uploads/progessbar.png");
	}
}
