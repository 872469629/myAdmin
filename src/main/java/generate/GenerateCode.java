package generate;

import java.util.ArrayList;
import java.util.List;

public class GenerateCode {
	private List<Form> forms = new ArrayList<Form>();
	private String modelName ;
	private String packagePath;
	private String fullPath;
	public List<Form> getForms() {
		return forms;
	}
	public String getModelName() {
		return modelName;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	
}
