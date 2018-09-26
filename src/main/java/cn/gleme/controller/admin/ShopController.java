package cn.gleme.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/shop")
public class ShopController {

	@RequestMapping(value = "/ajax")
	@ResponseBody
	public Map<String, Object> ajax() {
		Map<String, Object> result = new HashMap<>();
		result.put("commission_agent_status0_total", "0");
		result.put("commission_agent_total", "5373");
		result.put("commission_apply_status1_total", "0");
		result.put("commission_apply_status2_total", "0");
		result.put("finance_total", "0");
		result.put("goods_totals", "0");
		result.put("url", "http://localhost/web/index.php?c=site&a=entry&m=ewei_shopv2&do=web");
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		map.put("status", 1);
		return map;
	}

	@RequestMapping(value = "/ajaxGoods")
	@ResponseBody
	public Map<String, Object> ajaxGoods(Long day) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

}
