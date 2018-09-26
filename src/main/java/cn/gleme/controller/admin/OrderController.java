package cn.gleme.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController {

	/**
	 * 获取订单概述
	 */
	@RequestMapping(value = "/ajaxOrder")
	@ResponseBody
	public Map<String, Object> ajaxOrder(Long day) {
		Map<String, Object> order = new HashMap<>();
		order.put("order_count", "0");
		order.put("allorder_count", "0");
		order.put("order_price", "0.00");
		order.put("allorder_price", "0.00");
		order.put("avg", "0.00");
		Map<String, Object> result = new HashMap<>();
		result.put("order", order);
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		return map;
	}

	/**
	 * 订单数量
	 */
	@RequestMapping(value = "/list/ajaxGettotals")
	@ResponseBody
	public Map<String, Object> ajaxGettotals() {
		Map<String, Object> result = new HashMap<>();
		result.put("all", "8");
		result.put("status0", "3");
		result.put("status1", "2");
		result.put("status2", "0");
		result.put("status3", "3");
		result.put("status4", "0");
		result.put("status5", "0");
		result.put("status_1", "0");
		result.put("url", "http://localhost/web/index.php?c=site&a=entry&m=ewei_shopv2&do=web");
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		map.put("status", 1);
		return map;
	}

	/**
	 * 获取今日交易情况
	 */
	@RequestMapping(value = "/ajaxTransaction")
	@ResponseBody
	public Map<String, Object> ajaxTransaction() {
		List<Long> allcount_value = new ArrayList<>();
		allcount_value.add(0l);
		allcount_value.add(0l);
		allcount_value.add(0l);
		allcount_value.add(0l);
		allcount_value.add(0l);
		allcount_value.add(0l);
		List<Long> allprice_value = new ArrayList<>();
		allprice_value.add(0l);
		allprice_value.add(0l);
		allprice_value.add(0l);
		allprice_value.add(0l);
		allprice_value.add(0l);
		allprice_value.add(0l);
		List<Long> count_value = new ArrayList<>();
		count_value.add(0l);
		count_value.add(0l);
		count_value.add(0l);
		count_value.add(0l);
		count_value.add(0l);
		count_value.add(0l);
		List<String> price_key = new ArrayList<>();
		price_key.add("2018-09-09");
		price_key.add("2018-09-10");
		price_key.add("2018-09-11");
		price_key.add("2018-09-12");
		price_key.add("2018-09-13");
		price_key.add("2018-09-14");
		price_key.add("2018-09-15");
		List<Long> price_value = new ArrayList<>();
		price_value.add(0l);
		price_value.add(0l);
		price_value.add(0l);
		price_value.add(0l);
		price_value.add(0l);
		price_value.add(0l);
		Map<String, Object> map = new HashMap<>();
		map.put("allcount_value", allcount_value);
		map.put("allprice_value", allprice_value);
		map.put("count_value", count_value);
		map.put("price_key", price_key);
		map.put("price_value", price_value);
		return map;
	}

	/**
	 * 获取商品销售排行榜
	 */
	@RequestMapping(value = "/ajaxGoods")
	@ResponseBody
	public Map<String, Object> ajaxGoods(Long day) {
		List<Map<String,Object>> goods_rank_0 = new ArrayList<>();
		Map<String, Object> m1 = new HashMap<>();
		m1.put("count", "0");
		m1.put("id", "123");
		m1.put("money", "0.00");
		m1.put("thumb", "https://imgv3.jisuyunshang.com/images/9/2018/08/vXRw1Wy21Dx12JfWZNFFjcJ1FznBnn.jpg");
		m1.put("title", "联合合伙人特权");
		goods_rank_0.add(m1);
		Map<String, Object> obj = new HashMap<>();
		obj.put("goods_rank_0", goods_rank_0);
		Map<String, Object> result = new HashMap<>();
		result.put("obj", obj);
		result.put("url", "#");
		Map<String, Object> map = new HashMap<>();
		map.put("result", result);
		map.put("status", 1);
		return map;
	}

}
