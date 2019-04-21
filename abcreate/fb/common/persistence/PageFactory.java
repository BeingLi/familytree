package abcreate.fb.common.persistence;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.common.web.Servlets;

/**
 * BootStrap Table默认的分页参数创建
 */
public class PageFactory<T> {

	public Page<T> defaultPage() {
		HttpServletRequest request = Servlets.getRequest();
		int limit = Integer.valueOf(request.getParameter("limit"));
		int offset = Integer.valueOf(request.getParameter("offset"));
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if (StringUtils.isEmpty(sort)) {
			Page<T> page = new Page<>((offset / limit + 1), limit);
			page.setOpenSort(false);
			return page;
		} else {
			Page<T> page = new Page<>((offset / limit + 1), limit, sort);
			if ("asc".equals(order)) {
				page.setAsc(true);
			} else {
				page.setAsc(false);
			}
			return page;
		}
	}
}