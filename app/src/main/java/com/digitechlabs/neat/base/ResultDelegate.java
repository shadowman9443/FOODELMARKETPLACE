package com.digitechlabs.neat.base;

import com.paypal.android.MEP.PayPalResultDelegate;

import java.io.Serializable;


public class ResultDelegate implements PayPalResultDelegate,Serializable {
	private static final long serialVersionUID = 10001L;

	public void onPaymentSucceeded(String payKey, String paymentStatus) {
	
		/*Intent intent = new Intent(ProfilePaymentActivity.this,HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);*/
	}

	public void onPaymentFailed(String paymentStatus, String correlationID,
			String payKey, String errorID, String errorMessage) {

	}

	public void onPaymentCanceled(String paymentStatus) {

	}
}
