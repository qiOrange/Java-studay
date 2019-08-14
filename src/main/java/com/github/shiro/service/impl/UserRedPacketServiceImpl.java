package com.github.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.shiro.entity.RedPacket;
import com.github.shiro.entity.UserRedPacket;
import com.github.shiro.mapper.RedPacketMapper;
import com.github.shiro.mapper.UserRedPacketMapper;
import com.github.shiro.service.UserRedPacketService;


@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

	@Autowired
	private UserRedPacketMapper userRedPacketMapper;

	@Autowired
	private RedPacketMapper redPacketMapper;

	private static final int FAILED = 0;
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacket(Long redPacketId, Long userId) {
		// 获取大红包信息
		RedPacket redPacket = redPacketMapper.getRedPacketForUpdate(redPacketId);
		// 当前小红包库存大于0
		if (redPacket.getStock() > 0) {
			redPacketMapper.decreaseRedPacket(redPacketId);
			// 生成抢红包信息
			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			// 将抢红包信息插入数据库
			int result = userRedPacketMapper.grapRedPacket(userRedPacket);
			return result;
		}
		// 失败返回
		return FAILED;
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation =
	Propagation.REQUIRED)
	public int grapRedPacketForVersion(Long redPacketId, Long userId) {
		for (int i = 0; i < 5; i++) {
			// 获取红包信息，注意version值ֵ
			RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
			// 当前小红包库存大于0
			if (redPacket.getStock() > 0) {
				// 再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
				int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId,
				redPacket.getVersion());
				// 如果没有数据更新，则说明其他线程已经修改过数据，则重新抢夺
				if (update == 0) {
					continue;
				}
				// 生成抢红包信息
				UserRedPacket userRedPacket = new UserRedPacket();
				userRedPacket.setRedPacketId(redPacketId);
				userRedPacket.setUserId(userId);
				userRedPacket.setAmount(redPacket.getUnitAmount());
				userRedPacket.setNote("抢红包 " + redPacketId);
				// 将抢红包信息插入数据库
				int result = userRedPacketMapper.grapRedPacket(userRedPacket);
				return result;
			} else {
				//生成抢红包信息
				return FAILED;
			}
		}
		return FAILED;
	}

	@Override
	public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
		return null;
	}

	
}
