package com.sivaram.expensemanagement.repository.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Record {
	private int id;
	private int userId;
	private int categoryId;
	private String categoryName;
	private BigDecimal amount;
	private LocalDate expenseDate;
    private LocalDateTime createdAt;
    private String description;
    
	public Record(int id, int userId, int categoryId, String categoryName, BigDecimal amount, LocalDate expenseDate,
			LocalDateTime createdAt, String description) {
		this.id = id;
		this.userId = userId;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.amount = amount;
		this.expenseDate = expenseDate;
		this.createdAt = createdAt;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDate getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
