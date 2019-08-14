package com.github.shiro.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.shiro.service.UserRedPacketService;


@RestController
public class controllers {
	@Autowired
	private UserRedPacketService userRedPacketService;
	
	@RequestMapping(value = "/grapRedPacket")
	public Map<String, Object> grapRedPacket(Long redPacketId, Long userId) {
		int result = userRedPacketService.grapRedPacket(redPacketId, userId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		boolean flag = result > 0;
		retMap.put("success", flag);
		retMap.put("message", flag ? "成功" : "失败");
		return retMap;
	}
	
	
	
	
	@RequestMapping("/uploadfile")
	public String upLoad(@RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException, FileUploadException {
		// 创建File对象，指定文件的目标保存路径
		File dest = new File("d:/" + file.getOriginalFilename());
		if (file.isEmpty()) {
			throw new RuntimeException("上传的文件为空");
		}
		String content = file.getContentType();
		List<String> types = new ArrayList<>();

		if (types.contains(content)) {

		}
		file.transferTo(dest);

		return "文件上传成功";
	}
}
