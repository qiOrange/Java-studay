package com.github.shiro.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.shiro.entity.RedPacket;

public interface RedPacketMapper {
	
	RedPacket getRedPacket(Long id);
	
	Integer decreaseRedPacket(Long id);
	
	RedPacket getRedPacketForUpdate(Long id);
	
	Integer decreaseRedPacketForVersion(@Param("id") Long id, @Param("version") Integer version);
}
