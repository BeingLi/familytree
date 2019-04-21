package abcreate.fb.common.constant;

public class Consts {
	/**
	 * 已执行状态值
	 */
	public static final String EXECUTESTATE = "2";
	/**
	 * 单位体检计划审核状态值
	 */
	public static final String CHECKIN = "0";
	public static final String CHECKPASS = "1";
	public static final String CHECKOUT = "2";
	public static final String CHECKIN_NAME = "审核中";
	public static final String CHECKPASS_NAME = "审核通过";
	public static final String CHECKOUT_NAME = "审核不通过";
	/**
	 * 冠新接口
	 */
	private static final String HOST = "http://gxgg.greatsoft.net:8082/sx-s-health";
	public static final String LOGIN = HOST + "/app/bjdxLogin"; // 登录
	public static final String FIND_PERSON_BY_ROLE = HOST + "/thirdApi/findPersonByRole/"; // 根据角色查询所有人员
	public static final String FIND_HEALTHCARETARGET_BY_CONDITION = HOST + "/thirdApi/findHealthcareTargetByCondition/"; // 根据条件查询保健对象
	public static final String FIND_HEALTHCARETARGET_BY_IDCARD = HOST + "/thirdApi/findHealthcareTargetByIdCard/"; // 根据身份证号获取保健对象
	public static final String FIND_HEALTHCARETARGET_FOR_WORKER = HOST + "/thirdApi/findHealthcareTargetForWorker/"; // 获取保健人员签约的保健对象
	public static final String FIND_HEALTHCAREWORKER_FOR_TARGET = HOST + "/thirdApi/findHealthcareWorkerForTarget/"; // 根据保健对象获取所有的保健人员
	public static final String FIND_ALL_ORGANIZATIONS = HOST + "/thirdApi/findAllOrganizations"; // 获取所有的保健单位
	public static final String FIND_USER_BY_TOKEN = HOST + "/thirdApi/findUserByToken/"; // 根据token获取用户信息
	public static final String FIND_ALL_HEALTHCARE_INSTITUTION = HOST + "/thirdApi/findAllHealthcareInstitution"; // 获取所有的保健机构
	public static final String FIND_HEALTHCARE_WORKER = HOST + "/thirdApi/findHealthcareWorkerByHealthcareInstitutionId/"; // 根据保健机构ID获取保健人员
	public static final String FIND_WORKER_BY_WORKID = HOST + "/thirdApi/findHealthcareWorkerById/"; // 根据workID获取保健人员

	/**
	 * 宏数接口
	 */
	private static final String HONGSHU_HOST = "http://120.76.244.191:8158/HSPS/api/";
	public static final String TOKEN = HONGSHU_HOST + "token/token"; // 获取token
	public static final String DEPT_LIST = HONGSHU_HOST + "patient/deptList"; // 机构科室
	public static final String PATIENT_LIST = HONGSHU_HOST + "patient/patientList"; // 患者列表
	public static final String PATIENT_INFO = HONGSHU_HOST + "patient/patientInfo"; // 患者详情
	public static final String EHR_RECORD = HONGSHU_HOST + "patient/ehrRecord"; // 患者历次诊疗
	public static final String IN_HOSPITAL = HONGSHU_HOST + "patient/inHospital"; // 入院记录
	public static final String FIRST_COURSE = HONGSHU_HOST + "patient/firstCourse"; // 首次病程
	public static final String DAILY_COURSE = HONGSHU_HOST + "patient/dailyCourse"; // 日常病程
	public static final String CHECKIN_RECORD = HONGSHU_HOST + "patient/checkInRecord"; // 查房记录
	public static final String EXAM_LIST_BY_EHRID = HONGSHU_HOST + "patient/examListByEhrId"; // 医学影像/详情
	public static final String LABLIS_DATA = HONGSHU_HOST + "patient/labLisData"; // 检验列表
	public static final String LABDATA_DETAIL = HONGSHU_HOST + "patient/labDataDetail"; // 检验详情
	public static final String DOCTOR_ADVICE = HONGSHU_HOST + "patient/doctorAdvice"; // 医嘱/详情
	public static final String EHRIDS = HONGSHU_HOST + "patient/ehrIds"; // 获取历次诊疗EHRID
	public static final String GET_RECORDCOUNT_BY_MONTH = HONGSHU_HOST + "patient/getRecordCountByMonth"; // 统计六个月的诊疗记录数
	public static final String FIND_PHYSICALITEM = HONGSHU_HOST + "patient/findPhysicalItem"; // 获取体检项目列表
	public static final String FIND_PHYSICALSUBITEM = HONGSHU_HOST + "patient/findPhysicalSubItem"; // 获取体检子项目列表

	/**
	 * 健康风险
	 */
	public static final String HR = "lifestyle_health_risk";
	public static final String HR_SMOKING_ID = "1";// 抽烟
	public static final String HR_SMOKING_SECONDHAND_ID = "2";// 被动吸烟（吸二手烟）
	public static final String HR_DRINK_EXCESSIVE_ID = "3";// 饮酒过量
	public static final String HR_SLEEPTIME_INSUFFICIENT_ID = "4";// 睡眠时间过少
	public static final String HR_LATENIGHT_ID = "5";// 熬夜晚睡
	public static final String HR_SLEEPQUALITY_LOW_ID = "6";// 睡眠质量低
	public static final String HR_SPORT_INSUFFICIENT_ID = "7";// 运动不足
	public static final String HR_SEDENTARY_ID = "8";// 久坐不动
	public static final String HR_SALT_EXCESSIVE_ID = "9";// 食盐摄入过多
	public static final String HR_GARDENSTUFF_INSUFFICIENT_ID = "10";// 果蔬摄入过少
	public static final String HR_FRUMENTUM_INSUFFICIENT_ID = "11";// 谷类摄入过少
	public static final String HR_AQUATICFOOD_INSUFFICIENT_ID = "12";// 水产类摄入过少
	public static final String HR_MILK_INSUFFICIENT_ID = "13";// 奶制品摄入少
	public static final String HR_SOY_INSUFFICIENT_ID = "14";// 豆类及豆制品摄入少
	public static final String HR_REDMEET_EXCESSIVE_ID = "15";// 红肉摄入过多
	public static final String HR_PUFFEDFOOD_EXCESSIVE_ID = "16";// 甜点以及膨化食品摄入过多
	public static final String HR_PICKLEDSMOKEDFOOD_EXCESSIVE_ID = "17";// 腌制、熏制类食物摄入过多
	public static final String HR_SLEEPTIME_EXCESSIVE_ID = "18";// 睡眠时间过多
	public static final String DELIMITER = ";"; //分隔符 
	
	public static final String DELIMITER_COMMA = ","; //分隔符
}