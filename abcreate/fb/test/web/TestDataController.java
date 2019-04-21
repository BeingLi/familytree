package abcreate.fb.test.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import abcreate.fb.common.persistence.PageFactory;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.common.web.BaseController;
import abcreate.fb.test.entity.TestData;
import abcreate.fb.test.service.TestDataService;

/**
 * 单表生成Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testData")
public class TestDataController extends BaseController {

	@Autowired
	private TestDataService testDataService;

	@ModelAttribute
	public TestData get(@RequestParam(required = false) String id) {
		TestData entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = testDataService.get(id);
		}
		if (entity == null) {
			entity = new TestData();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/test/testDataList";
	}

	@ResponseBody
	@RequestMapping(value = "data")
	public Map listData(TestData testData) {
		Page<TestData> page = testDataService.findPage(new PageFactory<TestData>().defaultPage(), testData);
		return jsonPage(page);
	}

	@RequestMapping(value = "form")
	public String form(TestData testData, Model model) {
		model.addAttribute("testData", testData);
		return "modules/test/testDataForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseEntity<?> save(TestData testData) {
		beanValidator(testData);
		testDataService.save(testData);
		return renderSuccess();
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<?> delete(TestData testData) {
		testDataService.delete(testData);
		return renderSuccess();
	}

}