package com.sivaram.expensemanagement.repository.dto;

import java.math.BigDecimal;

public class Summary {
	private int year;
	private int month;
	private BigDecimal totalExpense;
	private BigDecimal totalIncome;
	private BigDecimal totalBalance;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public BigDecimal getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(BigDecimal expense) {
		this.totalExpense = expense;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal income) {
		this.totalIncome = income;
	}
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(BigDecimal balance) {
		this.totalBalance = balance;
	}
}
