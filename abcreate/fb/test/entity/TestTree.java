package abcreate.fb.test.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import abcreate.fb.common.persistence.TreeEntity;

/**
 * 树结构生成Entity
 */
@TableName("test_tree")
public class TestTree extends TreeEntity<TestTree> {

	private static final long serialVersionUID = 1L;
	@TableField(value = "parent_id", el = "parent, typeHandler=abcreate.fb.common.persistence.typeHandler.EntityTypeHandler")
	private TestTree parent; // 父级编号

	public TestTree() {
		super();
	}

	public TestTree(String id) {
		super(id);
	}

	@JsonBackReference
	@NotNull(message = "父级编号不能为空")
	public TestTree getParent() {
		return parent;
	}

	public void setParent(TestTree parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}