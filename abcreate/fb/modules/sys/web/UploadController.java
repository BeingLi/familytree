package abcreate.fb.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import abcreate.fb.common.config.Global;
import abcreate.fb.common.web.BaseController;
import abcreate.fb.common.web.Servlets;

/**
 * 上传Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/upload")
public class UploadController extends BaseController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map upload(@RequestParam("file") MultipartFile[] files) {
		List<String> data = new ArrayList<String>();
		int errno = 0;//错误代码，0 表示没有错误
		for (MultipartFile file:files) {
			if (file.isEmpty()) {
				return null;
			}
			String originalFilename = file.getOriginalFilename();
			String fileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ originalFilename.substring(originalFilename.lastIndexOf("."));
			try {
				File savefile = new File(Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL, fileName);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}
				// 保存
				file.transferTo(savefile);
				data.add(Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_URL + fileName);
			} catch (IOException e) {
				errno = 1;
				e.printStackTrace();
			}
		}
		// 返回wangeditor需要的数据格式
		Map dataMap = new HashMap();
		dataMap.put("errno", errno);
		dataMap.put("data", data);
		return dataMap;
	}

}