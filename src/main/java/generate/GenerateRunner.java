package generate;

import generate.Form.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author LiXiang
 * @version 4.0
 */
public class GenerateRunner {


    public static void main(String[] args){
		/*Generate.fullPath = */
		
		Map params = new HashMap<String, String>();

//		params.put("modelName", "Menu");//类名
//		params.put("name", "菜单");//业务名称
		
		
		params.put("modelName", "ImsEweiShopAdv");//类名
		params.put("name", "幻灯片");//业务名称
//		params.put("modelName", "ImsEweiShopNav");//类名
//		params.put("name", "导航图标");//业务名称
		
		List<Form> columnList = new ArrayList<Form>();
		columnList.add(new Form("id", "顺序",Type.text,true));
		columnList.add(new Form("advname", "标题",Type.text,true));
		columnList.add(new Form("link", "链接",Type.text,true));
		columnList.add(new Form("displayorder", "显示",Type.text,true));

		Generate.genDao(params, "dao.ftl", true);
		Generate.genDaoImpl(params, "daoImpl.ftl", true);
		Generate.genService(params, "service.ftl", true);
		Generate.genServiceImpl(params, "serviceImpl.ftl", true);
		Generate.genController(params, columnList, "controller.ftl", true);
		Generate.genAdd(params, columnList, "v3/add.ftl", true, "add.ftl");
		Generate.genList(params, columnList, "v3/list.ftl", true, "list.ftl");
	}
}
