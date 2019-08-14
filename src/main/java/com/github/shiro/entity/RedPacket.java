package com.github.shiro.entity;

import java.io.Serializable;
import java.sql.Timestamp;


import lombok.Data;

//@Entity
@Data
public class RedPacket implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Double amount;
	private Timestamp sendDate;
	private Integer total;
	private Double unitAmount;
	private Integer stock;
	private Integer version;
	private String note;

	public RedPacket() {
		super();
	}

}
