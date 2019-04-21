package abcreate.fb.test.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.common.web.BaseController;
import abcreate.fb.test.entity.TestTree;
import abcreate.fb.test.service.TestTreeService;

/**
 * 树结构生成Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testTree")
public class TestTreeController extends BaseController {

	@Autowired
	private TestTreeService testTreeService;

	@ModelAttribute
	public TestTree get(@RequestParam(required = false) String id) {
		TestTree entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = testTreeService.get(id);
		}
		if (entity == null) {
			entity = new TestTree();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(TestTree testTree, Model model) {
		List<TestTree> list = testTreeService.findList(testTree);
		model.addAttribute("list", list);
		return "modules/test/testTreeList";
	}

	@RequestMapping(value = "form")
	public String form(TestTree testTree, Model model) {
		if (testTree.getParent() != null && StringUtils.isNotBlank(testTree.getParent().getId())) {
			testTree.setParent(testTreeService.get(testTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(testTree.getId())) {
				TestTree testTreeChild = new TestTree();
				testTreeChild.setParent(new TestTree(testTree.getParent().getId()));
				List<TestTree> list = testTreeService.findList(testTree);
				if (list.size() > 0) {
					testTree.setSort(list.get(list.size() - 1).getSort());
					if (testTree.getSort() != null) {
						testTree.setSort(testTree.getSort() + 30);
					}
				}
			}
		}
		if (testTree.getSort() == null) {
			testTree.setSort(30);
		}
		model.addAttribute("testTree", testTree);
		return "modules/test/testTreeForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseEntity<?> save(TestTree testTree) {
		beanValidator(testTree);
		testTreeService.save(testTree);
		return renderSuccess();
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<?> delete(TestTree testTree) {
		testTreeService.delete(testTree);
		return renderSuccess();
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestTree> list = testTreeService.findList(new TestTree());
		for (int i = 0; i < list.size(); i++) {
			TestTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId())
					&& e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

}