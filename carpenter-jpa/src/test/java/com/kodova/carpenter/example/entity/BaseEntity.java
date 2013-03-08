package com.kodova.carpenter.example.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 *
 */
@Embeddable
public class BaseEntity {

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
