package com.github.shiro.service;

public interface UserRedPacketService {
	
	public int grapRedPacket(Long redPacketId, Long userId);

	public int grapRedPacketForVersion(Long redPacketId, Long userId);
	

	public Long grapRedPacketByRedis(Long redPacketId, Long userId);
	
	
}
