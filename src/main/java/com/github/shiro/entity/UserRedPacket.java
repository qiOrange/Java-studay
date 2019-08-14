package com.github.shiro.entity;

import java.io.Serializable;
import java.sql.Timestamp;


import lombok.Data;

@Data
//@Entity
public class UserRedPacket implements Serializable {

	private Long id;
	private Long redPacketId;
	private Long userId;
	private Double amount;
	private Timestamp grabTime;
	private String note;
	
	private static final long serialVersionUID = -5617482065991830143L;
    
}