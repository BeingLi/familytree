package abcreate.fb.modules.fb.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import abcreate.fb.common.persistence.DataEntity;

@TableName("dictionary")
public class Dictionary extends DataEntity<Dictionary>{
	private static final long serialVersionUID = 1L;

		//code
		private String dictCode;
		//值
		private String dictValue;
		//描述
		private String dictDesc;
		
		private String dictShow;


	public Dictionary() {
		super();
	}

	public Dictionary(String id) {
		super(id);
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictDesc() {
		return dictDesc;
	}

	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

	public String getDictShow() {
		return dictShow;
	}

	public void setDictShow(String dictShow) {
		this.dictShow = dictShow;
	}
	
}
