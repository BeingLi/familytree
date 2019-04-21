package abcreate.fb.modules.fb.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import abcreate.fb.common.persistence.DataEntity;
@TableName("member")
public class Member extends DataEntity<Member>{
	private static final long serialVersionUID = 1L;
	private String username;
	private String mobile;
	private String work;
	private String home;
	private String sex;
	private String father;
	private String mother;
	private String children;
	private Integer levelcode;
	private Date birthday;
	private Date deathday;
	@TableField(exist = false)
	private String BirthDayString;
	@TableField(exist = false)
	private String DeathDayString;
	private int status;
	private String indexcode;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
	public String getMother() {
		return mother;
	}
	public void setMother(String mother) {
		this.mother = mother;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public Integer getLevelCode() {
		return levelcode;
	}
	public void setLevelCode(Integer levelCode) {
		this.levelcode = levelCode;
	}
	public Date getBirthDay() {
		return birthday;
	}
	public void setBirthDay(Date birthDay) {
		birthday = birthDay;
	}
	public Date getDeathDay() {
		return deathday;
	}
	public void setDeathDay(Date deathDay) {
		deathday = deathDay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIndexCode() {
		return indexcode;
	}
	public void setIndexCode(String indexCode) {
		this.indexcode = indexCode;
	}
	public String getBirthDayString() {
		return BirthDayString;
	}
	public void setBirthDayString(String birthDayString) {
		BirthDayString = birthDayString;
	}
	public String getDeathDayString() {
		return DeathDayString;
	}
	public void setDeathDayString(String deathDayString) {
		DeathDayString = deathDayString;
	}
	
   


}
