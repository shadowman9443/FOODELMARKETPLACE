package com.digitechlabs.neat.entities;

import com.google.gson.annotations.SerializedName;


public class PaymentHolder {

	@SerializedName("Payment")
    public Payment PaymentEntity;

    public PaymentHolder()
    {
          PaymentEntity = new Payment();
    }
}
