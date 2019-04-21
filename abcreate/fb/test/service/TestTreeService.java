package abcreate.fb.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.TreeService;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.test.dao.TestTreeDao;
import abcreate.fb.test.entity.TestTree;

/**
 * 树结构生成Service
 */
@Service
@Transactional(readOnly = true)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

	public TestTree get(String id) {
		return super.get(id);
	}

	public List<TestTree> findList(TestTree testTree) {
		if (StringUtils.isNotBlank(testTree.getParentIds())) {
			testTree.setParentIds("," + testTree.getParentIds() + ",");
		}
		return super.findList(testTree);
	}

	@Transactional(readOnly = false)
	public void save(TestTree testTree) {
		super.save(testTree);
	}

	@Transactional(readOnly = false)
	public void delete(TestTree testTree) {
		super.delete(testTree);
	}

}