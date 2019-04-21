package abcreate.fb.modules.sys.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import abcreate.fb.common.utils.Exceptions;
import abcreate.fb.common.utils.SpringContextHolder;
import abcreate.fb.common.utils.StringUtils;
import abcreate.fb.modules.sys.dao.LogDao;
import abcreate.fb.modules.sys.entity.Log;

/**
 * 字典工具类
 */
public class LogUtils {
	
	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request){
		saveLog(request, null);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request,Exception ex){
		Log log = new Log();
		log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
		log.setRemoteAddr(StringUtils.getRemoteAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setRequestUri(request.getRequestURI());
		log.setParams(request.getParameterMap());
		log.setMethod(request.getMethod());
		log.setException(Exceptions.getStackTraceAsString(ex));
		log.setCreateBy(UserUtils.getUserId());
		log.setCreateDate(new Date());
		// 异步保存日志
		new SaveLogThread(log).start();
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;
		
		public SaveLogThread(Log log){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
		}
		
		@Override
		public void run() {
			// 保存日志信息
			logDao.insert(log);
		}
	}
	
}