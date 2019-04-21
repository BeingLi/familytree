package abcreate.fb.modules.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import abcreate.fb.common.config.Global;
import abcreate.fb.common.constant.Consts;
import abcreate.fb.common.mapper.JsonMapper;
import abcreate.fb.common.rest.HongshuResult;
import abcreate.fb.common.rest.Result;
import abcreate.fb.common.utils.CacheUtils;
import abcreate.fb.common.utils.CookieUtils;
import abcreate.fb.common.utils.HttpUtils;
import abcreate.fb.common.utils.SpringContextHolder;
import abcreate.fb.common.web.Servlets;
import abcreate.fb.modules.sys.dao.MenuDao;
import abcreate.fb.modules.sys.entity.Menu;

/**
 * 用户工具类
 */
public class UserUtils {

	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

	public static final String CACHE_MENU_LIST = "menuList";

	/**
	 * 获取当前登陆用户ID
	 */
	public static String getUserId() {
		String userId = "System";
		return userId;
	}

	/**
	 * 获取菜单
	 */
	public static List<Menu> getMenuList() {
		List<Menu> menuList = (List<Menu>) CacheUtils.get(CACHE_MENU_LIST);
		if (menuList == null) {
			menuList = menuDao.findAllList(new Menu());
			CacheUtils.put(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	/**
	 * 根据parentId获取下级菜单
	 */
	public static List<Menu> getSubMenuList(String parentId) {
		List<Menu> menuList = getMenuList();
		List<Menu> SubMenuList = Lists.newArrayList();
		for (Menu menu : menuList) {
			if (parentId.equals(menu.getParentId()) && Global.SHOW.equals(menu.getIsShow())) {
				SubMenuList.add(menu);
			}
		}
		return SubMenuList;
	}

	/**
	 * 获取宏数云平台授权token
	 */
	public synchronized static String getHongshuToken() {
		String token = (String) CacheUtils.get("hongshuTokenCache", "token");
		if (token == null) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("client_id", "498402061-0af6ce91c25c-232a-cfe4-5141-4d3bd669-192017102");
			param.put("client_secret", "857802061-1eb2bf461739-e888-e994-1c62-15503d4f-192017102");
			String tokenInfo = HttpUtils.doPost(Consts.TOKEN, null, param, true);
			Map resultMap = (Map) JsonMapper.fromJsonString(tokenInfo, Map.class);
			Map tokenMap = (Map) resultMap.get("info");
			token = (String) tokenMap.get("access_token");
			CacheUtils.put("hongshuTokenCache", "token", token);
		}
		return token;
	}

	/**
	 * 获取患者历次诊疗ID
	 */
	public static List<String> getEhrIds(String idCard, String ehrDate) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("idCard", idCard);
		param.put("ehrDate", ehrDate);
		HongshuResult result = HttpUtils.postForHongshuResult(Consts.EHRIDS, param);
		List<String> list = new ArrayList<String>();
		if (result != null) {
			list = result.getList();
		}
		return list;
	}

	/**
	 * 根据患者诊疗id,查看患者检查列表
	 */
	public static List<Map> getLabList(String ehrId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ehrId", ehrId);
		HongshuResult result = HttpUtils.postForHongshuResult(Consts.LABLIS_DATA, param);
		List<Map> list = new ArrayList<Map>();
		if (result != null) {
			list = result.getList();
		}
		return list;
	}

	/**
	 * 根据患者诊疗id、检验id，查看患者检查详情
	 */
	public static List<Map> getLabDetail(String ehrId, String labId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ehrId", ehrId);
		param.put("labId", labId);
		param.put("hasGraph", "N");
		HongshuResult result = HttpUtils.postForHongshuResult(Consts.LABDATA_DETAIL, param);
		List<Map> list = new ArrayList<Map>();
		if (result != null) {
			list = result.getList();
		}
		return list;
	}
	
	/**
	 * 根据患者ehrId查看体检数据项目列表
	 */
	public static List<Map> getPhysicalItemList(String ehrId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ehrId", ehrId);
		HongshuResult result = HttpUtils.postForHongshuResult(Consts.FIND_PHYSICALITEM, param);
		List<Map> list = new ArrayList<Map>();
		if (result != null) {
			list = result.getList();
		}
		return list;
	}
	
	/**
	 * 根据患者ehrId和体检项目id查看子项目数据列表
	 */
	public static List<Map> getPhysicalSubItemList(String ehrId, String itemId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("ehrId", ehrId);
		param.put("itemId", itemId);
		HongshuResult result = HttpUtils.postForHongshuResult(Consts.FIND_PHYSICALSUBITEM, param);
		List<Map> list = new ArrayList<Map>();
		if (result != null) {
			list = result.getList();
		}
		return list;
	}

	/**
	 * 获取当前登陆保健人员管理的所有的保健对象
	 */
	public static List<Map> getAllCareTargetByWorker() {
		Map map = getCareTarget("", "", "", "", "", UserUtils.getUserId(), 1, 0);
		List<Map> list = Lists.newArrayList();
		if (map != null) {
			list = (List<Map>) map.get("rows");
		}
		return list;
	}

	/**
	 * 获取系统中所有的保健对象
	 */
	public static List<Map> getAllCareTarget() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("roleId", "7");// 角色7代表保健对象
		Result result = HttpUtils.postForResult(Consts.FIND_PERSON_BY_ROLE + "1/100000", param);
		if (result.getCode() != 0) {
			return null;
		}
		Map resultMap = (Map) result.getResult();
		List<Map> list = Lists.newArrayList();
		if (resultMap != null) {
			list = (List<Map>) resultMap.get("rows");
		}
		return list;
	}

	/**
	 * 获取保健对象
	 * 
	 * @param sex 性别
	 * @param grade 职级
	 * @param startAge 起始年龄
	 * @param endAge 结束年龄
	 * @param orgId 单位ID
	 * @param workerId 保健(登录)人员ID
	 * @param pageNo 分页页码
	 * @param pageSize 每页大小(传0表示不分页)
	 * 
	 */
	public static Map getCareTarget(String sex, String grade, String startAge, String endAge, String orgId,
			String workerId, Integer pageNo, Integer pageSize) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sexCode", sex);
		param.put("gradeCode", grade);
		param.put("startAge", startAge);
		param.put("endAge", endAge);
		param.put("orgId", orgId);
		param.put("workerId", workerId);
		if (pageSize == 0) {
			pageSize = 100000;// 因冠新不提供获取所有数据接口，此处把pageSize设置的大一点
		}
		Result result = HttpUtils.postForResult(Consts.FIND_HEALTHCARETARGET_BY_CONDITION + pageNo + "/" + pageSize,
				param);
		if (result.getCode() != 0) {
			return null;
		}
		Map resultMap = (Map) result.getResult();
		return resultMap;
	}
	
	/**
	 * 根据保健对象身份证号获取手机号
	 * 
	 * @param idCards 身份证号列表，多个号码使用","分隔
	 * @return 手机号码列表，多个号码使用","分隔
	 */
	public static String getTelephones(String idCards) {
		StringBuffer s = new StringBuffer();
		String[] ids = idCards.split(Consts.DELIMITER_COMMA);
		for (String idCard : ids) {
			Result result = HttpUtils.getForResult(Consts.FIND_HEALTHCARETARGET_BY_IDCARD + idCard);
			if (result != null && result.getCode() == 0) {
				Map resultMap = (Map) result.getResult();
				if (resultMap != null) {
					s.append(resultMap.get("tel").toString()).append(Consts.DELIMITER_COMMA);
				}
			}
		}
		if (null != s && s.length() > 0) {
			return s.toString().substring(0, s.length() - 1);
		} else {
			return null;
		}
	}
}