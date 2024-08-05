package com.api.hateoas.model;

import lombok.Data;

@Data //crea get y setter esto lo hace lombook
public class Balance {

	private float balance;

	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Balance(float balance) {
		super();
		this.balance =balance;
	}
	public Balance() {
		super();
	}
}
