package abcreate.fb.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import abcreate.fb.common.service.BaseService;
import abcreate.fb.test.dao.TestDataDao;
import abcreate.fb.test.entity.TestData;

/**
 * 单表生成Service
 */
@Service
@Transactional(readOnly = true)
public class TestDataService extends BaseService<TestDataDao, TestData> {

	public TestData get(String id) {
		return super.get(id);
	}

	public List<TestData> findList(TestData testData) {
		return super.findList(testData);
	}

	public Page<TestData> findPage(Page<TestData> page, TestData testData) {
		return super.findPage(page, testData);
	}

	@Transactional(readOnly = false)
	public void save(TestData testData) {
		super.insertOrUpdate(testData);
	}

	@Transactional(readOnly = false)
	public void delete(TestData testData) {
		super.delete(testData);
	}

}