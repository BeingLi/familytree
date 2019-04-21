package abcreate.fb.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import abcreate.fb.common.service.BaseService;
import abcreate.fb.modules.sys.dao.LogDao;
import abcreate.fb.modules.sys.entity.Log;

/**
 * 日志Service
 */
@Service
@Transactional(readOnly = true)
public class LogService extends BaseService<LogDao, Log> {

}