package generate;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import generate.Form.Type;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码生成器
 *
 * @author LiXiang
 * @version 4.0
 */
public class Generate {
	public static String fullPath = "c:\\gener_code\\";
	private final static String baseDao = "net.gleme.dao.BaseDao";
	private final static String baseDaoImpl = "net.gleme.dao.impl.BaseDaoImpl";
	private final static String baseService = "net.gleme.service.BaseService";
	private final static String baseServiceImpl = "net.gleme.service.impl.BaseServiceImpl";
	private final static String baseAction = "com.framework.common.action.base.BaseAdminAction";
	
	public final static String PACKAGE_NAME = "package";
	public final static String PACKAGE_Path = "package_path";
	public final static String GENERATOR_PACKAGE_PATH = "com.framework.";//成功包路径
	private static ApplicationContext application = null;
	static {
		// application =  new ClassPathXmlApplicationContext("applicationContext_test.xml");
	}
	public static  void createFile(Map data,String path,String fileName,String templateName) {
		try{
			
			//ApplicationContext application = SpringUtils.getApplicationContext();
			String filePath = path + fileName;
			Configuration cfg = FreemarkerManager.getConfiguration();//初始化状态
			Template template = cfg.getTemplate(templateName);//获取模板..
			File file = new File(filePath);
			File directory = file.getParentFile();
			if (!directory.exists()) {
				directory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			template.process(data, out, ObjectWrapper.BEANS_WRAPPER);
			System.out.println("生成："+file.getAbsolutePath());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 需要设置包名和实体名
	 * "dao.ftl"
	 */
	public static void genDao(Map<String, String> data, String fltPath, boolean isNeedBase) {
		if (StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {//如果无提供包名.使用实体名
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".dao");
			data.put("importModel", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".entity");
		} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path) + ".dao");
			data.put("importModel", data.get(Generate.PACKAGE_Path) + ".entity");
		}
		if (isNeedBase)
			data.put("importBaseDAO", baseDao);
		data.put("name", data.get("name"));
		data.put("model", data.get("modelName"));
		createFile(data, fullPath + "\\" + data.get("modelName").toLowerCase() + "\\dao\\", data.get("modelName") + "Dao.java", fltPath);
	}
	//"daoImpl.ftl"
	public static void genDaoImpl(Map<String, String> data, String fltPath, boolean isNeedBase) {
		String modelName = data.get("modelName");
		if (StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".dao.impl");
			data.put("importDao", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".dao");
			data.put("importModel", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".entity");
		} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path) + ".dao.impl");
			data.put("importDao", data.get(Generate.PACKAGE_Path) + ".dao");
			data.put("importModel", data.get(Generate.PACKAGE_Path) + ".entity");
		}
		if (isNeedBase)
			data.put("importAbstractDaoImpl", baseDaoImpl);

		data.put("model", modelName);
		data.put("name", data.get("name"));
		String lowerModelName = modelName.toLowerCase();
		data.put("repository", lowerModelName.substring(0, 1) + modelName.substring(1));
		createFile(data, fullPath + "\\" + data.get("modelName").toLowerCase() + "\\dao\\impl\\", modelName + "DaoImpl.java", fltPath);
	}
	//"service.ftl"
	public static void  genService(Map<String,String> data,String fltPath,boolean isNeedBase) {
		String modelName = data.get("modelName");
		if (StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".entity");
		} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path) + ".service");
			data.put("importModel", data.get(Generate.PACKAGE_Path) + ".entity");
		}
		if (isNeedBase)
			data.put("importBaseService", baseService);
		data.put("name", data.get("name"));
		data.put("model", modelName);
		data.put("upperModel", modelName.toUpperCase());
		createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\service\\", modelName+"Service.java", fltPath);
	}
	//"serviceImpl.ftl"
	public static void genServiceImpl(Map<String, String> data , String fltPath, boolean isNeedBase) {
		String modelName = data.get("modelName");
		if (StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".service.impl");
			data.put("importDao", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".dao");
			data.put("imorptService", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH + data.get("modelName").toLowerCase() + ".entity");
		} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path) + ".service.impl");
			data.put("importDao", data.get(Generate.PACKAGE_Path) + ".dao");
			data.put("importService", data.get(Generate.PACKAGE_Path) + ".service");
			data.put("importModel", data.get(Generate.PACKAGE_Path) + ".entity");
		}
		if (isNeedBase)
			data.put("imorptAbstractServiceImpl", baseServiceImpl);
		data.put("model", modelName);
		String lowerModelName = modelName.toLowerCase();
		data.put("name", data.get("name"));
		data.put("lowerModel", lowerModelName);
		data.put("repository", lowerModelName.substring(0, 1) + modelName.substring(1));
		createFile(data, fullPath + "\\" + data.get("modelName").toLowerCase() + "\\service\\impl\\", modelName + "ServiceImpl.java", fltPath);
	}
	public static void  genController( Map<String,String> params,List<Form> columnList,String fltPath,boolean isNeedBase) {
		Map<String,Object> data = new HashMap<String, Object>();
		String modelName = params.get("modelName");
		if (StringUtils.isEmpty(params.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH + params.get("modelName").toLowerCase() + ".controller.admin");
			data.put("imorptService", GENERATOR_PACKAGE_PATH + params.get("modelName").toLowerCase() + ".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH + params.get("modelName").toLowerCase() + ".entity");
		} else {
			data.put(Generate.PACKAGE_NAME, params.get(Generate.PACKAGE_Path) + ".controller.admin");
			data.put("importService", params.get(Generate.PACKAGE_Path) + ".service");
			data.put("importModel", params.get(Generate.PACKAGE_Path) + ".entity");
		}
		if (isNeedBase)
			data.put("model", modelName);

		List<Form> parentSelects = new ArrayList<>();
		for(Form column:columnList){
			if(Type.parentSelect == column.getType()){
				parentSelects.add(column);
			}
		}

		String lowerModelName = modelName.toLowerCase();
		data.put("name", params.get("name"));
		data.put("lowerModel", lowerModelName);
		data.put("lowerModelName", lowerModelName.substring(0, 1) + modelName.substring(1));
		data.put("repository", camel4underline(lowerModelName.substring(0, 1) + modelName.substring(1)));
		data.put("columnList",columnList);
		data.put("parentSelects",parentSelects);
		createFile(data, fullPath + "\\"+params.get("modelName").toLowerCase()+"\\controller\\admin\\", modelName+"Controller.java",fltPath);
	}
	
	public static void genAdd(Map<String,String> params,List<Form> columnList,String fltPath,boolean isNeedBase,String fltName) {
		Map<String,Object> data = new HashMap<String, Object>();
		String modelName = params.get("modelName");
		data.put("model", modelName);
		data.put("name", params.get("name"));
		String lowerModelName = modelName.toLowerCase();
		data.put("lowerModel", lowerModelName);
		data.put("repository", camel4underline(lowerModelName.substring(0, 1)+modelName.substring(1)));

		data.put("messagehome","${message(\"admin.breadcrumb.home\")}");
		data.put("editId","${(bean.id?c)!}");
		data.put("optype","[#if bean??]编辑[#else]添加[/#if]");
		data.put("base","${base}");
		data.put("smbol","${");
		data.put("columnList",columnList);

		createFile(data, fullPath+"\\"+params.get("modelName").toLowerCase()+"\\"+camel4underline(lowerModelName.substring(0, 1)+modelName.substring(1))+"\\", fltName, fltPath);
    }
	
	

	public static void genList(Map<String,String> params,List<Form> columnList,String fltPath,boolean isNeedBase,String fltName) {
		Map<String,Object> data = new HashMap<String, Object>();
		String modelName = params.get("modelName");
		data.put("name", params.get("name"));
		if(isNeedBase)
		data.put("model", modelName);
		data.put("modelName", modelName);
		String lowerModelName = modelName.toLowerCase();
		data.put("lowerModel", lowerModelName);
		String repository = camel4underline(lowerModelName.substring(0, 1)+modelName.substring(1));
		data.put("repository",repository );
		data.put("smbol","${");
		data.put("columnList",columnList);
		data.put("editId","${(bean.id?c)!}");
		data.put("base","${base}");
		createFile(data, fullPath+"\\"+params.get("modelName").toLowerCase()+"\\"+repository+"\\", fltName, fltPath);
    }
	public static String firstLetterUpper(String str) {
		if(str==null) return null;
		char [] charArray = str.toCharArray();
		if(charArray.length>0) {
			charArray[0] = Character.toUpperCase(charArray[0]);
		}
		return new String(charArray);
	}
	public static String firstLetterLower(String str) {
		if(str==null) return null;
		char [] charArray = str.toCharArray();
		if(charArray.length>0) {
			charArray[0] = Character.toLowerCase(charArray[0]);
			if(charArray.length>1) {
				charArray[1] = Character.toLowerCase(charArray[1]);
			}
		}
		return new String(charArray);
	}
	
	
	public static String camel4underline(String param){  
        Pattern  p=Pattern.compile("[A-Z]");  
        if(param==null ||param.equals("")){  
            return "";  
        }  
        StringBuilder builder=new StringBuilder(param);  
        Matcher mc=p.matcher(param);  
        int i=0;  
        while(mc.find()){  
            builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());  
            i++;  
        }  
  
        if('_' == builder.charAt(0)){  
            builder.deleteCharAt(0);  
        }  
        return builder.toString();  
    } 
	
	
	
	}



