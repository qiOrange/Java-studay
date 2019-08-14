package com.github.shiro.service;

import com.github.shiro.entity.RedPacket;

public interface RedPacketService {
	
	public RedPacket getRedPacket(Long id);

	public int decreaseRedPacket(Long id);
	
}