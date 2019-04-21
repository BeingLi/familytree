package abcreate.fb.modules.fb.service;



import abcreate.fb.common.service.BaseService;
import abcreate.fb.modules.fb.dao.DictionaryDAO;
import abcreate.fb.modules.fb.dao.MemberDAO;
import abcreate.fb.modules.fb.entity.Dictionary;
import abcreate.fb.modules.fb.entity.Member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @ClassName MemberService
 * @Description 
 * @author lpt
 * @data 2017年11月24日
 */



	@Service
	@Transactional(readOnly = true)
public class MemberService extends BaseService<MemberDAO, Member>{

    @Resource
    private MemberDAO memberDAO;


    public List<Member> findByName(String Name) {
        return memberDAO.findByName(Name);
    }

    public Member getById(String id) {
        return memberDAO.get(id);
    }
    public Member getByIndex(String index) {
        return memberDAO.getByIndex(index);
    }
    public List<String> getChildrenIndexListByFatherIndex(String fatherIndex) {
        return memberDAO.getChildrenIndexListByFatherIndex(fatherIndex);
    }
    @Transactional
    public void save(Member member) {
//    	memberDAO.save(member);
    	super.insertOrUpdate(member);
    }

    @Transactional
    public void update(Member user) {
    	super.insertOrUpdate(user);
    }

    public List<Member> listMemberByLevel(int levelnumber) {
        List<Member> result = memberDAO.listMemberByLevel(levelnumber);
       
        return result;
    }
    public Member getByLevelAndName(Integer level,String name) {
        return memberDAO.getByLevelAndName(level,name);
    }
}
