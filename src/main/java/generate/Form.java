package generate;

/**
 * 表单
 * @author Administrator
 *
 */
public class Form {
	public enum Type {

		/** text */
		text,

		/** url */
		url,

		/** textArea */
		textArea,

		/** 富文本 */
		editor,

		/** 文件类型 file */
		file,

		/** 文件类型 img */
		img,

		/** 数字类型 */
		digits,

		/** date */
		date,
		
		/** 标签选择 */
		enumSelect,
		
		/** 实体关联选择 */
		parentSelect
	}
	
	private String label;
	private String name;
	private Boolean required;
	
	private Type type;
	
	public String getLabel() {
		return label;
	}
	public String getName() {
		return name;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Form(String name, String label,Type type,Boolean required ) {
		super();
		this.label = label;
		this.name = name;
		this.type = type;
		this.required = required==null?false:required;
	}
	
	
	public Form() {
		// TODO Auto-generated constructor stub
	}
}
