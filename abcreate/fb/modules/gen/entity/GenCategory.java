package abcreate.fb.modules.gen.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import abcreate.fb.modules.sys.entity.Dict;

/**
 * 生成方案Entity
 */
@XmlRootElement(name="category")
public class GenCategory extends Dict {
	
	private static final long serialVersionUID = 1L;
	private List<String> template;			// 主表模板
	
	public static String CATEGORY_REF = "category-ref:";

	public GenCategory() {
		super();
	}

	@XmlElement(name = "template")
	public List<String> getTemplate() {
		return template;
	}

	public void setTemplate(List<String> template) {
		this.template = template;
	}
	
}