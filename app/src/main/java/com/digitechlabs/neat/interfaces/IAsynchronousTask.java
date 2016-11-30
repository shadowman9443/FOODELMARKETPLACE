package com.digitechlabs.neat.interfaces;



public interface IAsynchronousTask {
	void showProgressLoader();

	void hideProgressLoader();
	
	Object doBackgroundPorcess();

	void processDataAfterDownload(Object data);	

}
