package abcreate.fb.test.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import abcreate.fb.common.persistence.DataEntity;

/**
 * 单表生成Entity
 */
@TableName("test_data")
public class TestData extends DataEntity<TestData> {

	private static final long serialVersionUID = 1L;
	private String officeId; // 部门
	private String name; // 名称
	private String sex; // 性别
	private Date inDate; // 加入日期
	@TableField(exist = false)
	private Date beginInDate; // 开始加入日期
	@TableField(exist = false)
	private Date endInDate; // 结束加入日期

	public TestData() {
		super();
	}

	public TestData(String id) {
		super(id);
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 1, message = "性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Date getBeginInDate() {
		return beginInDate;
	}

	public void setBeginInDate(Date beginInDate) {
		this.beginInDate = beginInDate;
	}

	public Date getEndInDate() {
		return endInDate;
	}

	public void setEndInDate(Date endInDate) {
		this.endInDate = endInDate;
	}

}