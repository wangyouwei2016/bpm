package com.dstz.demo.core.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.vo.ApiResponse;
import com.dstz.base.common.constats.AbAppRestConstant;
import com.dstz.base.common.utils.IdGeneratorUtils;
import com.dstz.base.common.utils.JsonUtils;
import com.dstz.base.web.controller.AbCrudController;
import com.dstz.bpm.api.dto.invoke.BpmUrlFormSaveDTO;
import com.dstz.bpm.api.vo.BpmUrlFormResponseDTO;
import com.dstz.demo.core.entity.BizApplyOrder;
import com.dstz.demo.core.manager.BizApplyOrderManager;

import cn.hutool.core.util.StrUtil;

@RestController

@RequestMapping(AbAppRestConstant.DEMO_SERVICE_PREFIX + "/applyOrder")
public class BizApplyOrderController extends AbCrudController<BizApplyOrder> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbCrudController.class);
	@Autowired
	private BizApplyOrderManager orderMananger;
	
	
	/**
	 * Url 表单处理器实例
	 * @param urlFormSaveDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("formHandler")
	public ApiResponse<BpmUrlFormResponseDTO> formHandler(@RequestBody BpmUrlFormSaveDTO urlFormSaveDto) throws Exception {
		LOGGER.debug("DEMO 表单处理器执行保存动作===》{}", JsonUtils.toJSONString(urlFormSaveDto.getBizData()));

		BizApplyOrder order = JsonUtils.parseObject(JsonUtils.toJSONNode(urlFormSaveDto.getBizData()), BizApplyOrder.class) ;
		
		BpmUrlFormResponseDTO bpmUrlFormResponseDTO = new BpmUrlFormResponseDTO();
		
		// 第一次保存，构建业务主键关联
		if (StrUtil.isEmpty(order.getId())) {
			String id = IdGeneratorUtils.nextId();
			// 这里必须返回 业务主键
			bpmUrlFormResponseDTO.setBizId(id);
			order.setId(id);
			orderMananger.create(order);

			Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("startVariable", order.getQdjl());
			// 启动时候设置一些流程变量，请看 act_ru_variable 表、在整个流程声明周期您都可以使用该流程变量，可以用于分支判断等等
			bpmUrlFormResponseDTO.setVariables(hashMap);
		} else {
			// 更新情况
			orderMananger.update(order);
			// 变量设置可忽略
			Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("doTaskVariable", order.getQdjl() + "-" + urlFormSaveDto.getNodeKey());
			bpmUrlFormResponseDTO.setVariables(hashMap);
		}

		return ApiResponse.success(bpmUrlFormResponseDTO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected String getEntityDesc() {
		return "demoController";
	}

	
	
	
	
	
	
	
	
	
	
	

}