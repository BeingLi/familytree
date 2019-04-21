package abcreate.fb.modules.fb.web;



import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import abcreate.fb.common.web.BaseController;
import abcreate.fb.modules.fb.entity.Dictionary;
import abcreate.fb.modules.fb.entity.Member;
import abcreate.fb.modules.fb.service.DictionaryService;
import abcreate.fb.modules.fb.service.MemberService;

@Controller
@RequestMapping(value = "${adminPath}/familybook")
public class FamilyBookController extends BaseController{
	@Resource
	private DictionaryService dictionaryService;
	
	@Resource
	private MemberService memberService;
	
	private String UTF = "UTF-8";
	private String ISO = "ISO-8859-1";
	private String GBK = "GBK";

	@ResponseBody
	@RequestMapping(value = "getlevelNumber")
    public String getLevelName(int levelNumber){
    	
    	Dictionary dic = dictionaryService.findByDictValue(String.valueOf(levelNumber), "LEVEL");
    	return dic==null?null:dic.getDictShow();
    }
    
	@ResponseBody
    @RequestMapping(value = "/memberListByLevel", method = RequestMethod.GET)
    public List<Member> memberListByLevel(int levelNumber) {
    	
        List<Member> list = memberService.listMemberByLevel(levelNumber);
        return list;
    }
    @RequestMapping(value = "/memberListByName", method = RequestMethod.GET)
    @ResponseBody
    public List<Member> memberListByName(String userName) {
    	
        List<Member> list = new ArrayList<Member>();
			list = memberService.findByName(userName);

        return list;
    }
    @RequestMapping(value = "/memberDetail", method = RequestMethod.POST)
    @ResponseBody
    public Member memberDetail(String memberIndex) {
    	Member member = memberService.getById(memberIndex);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	if(member.getBirthDay()!=null){
    		member.setBirthDayString(format.format(member.getBirthDay()));
    	}
    	if(member.getDeathDay()!=null){
    		member.setDeathDayString(format.format(member.getDeathDay()));
    	}
    	return member;
    }
    
    
    @RequestMapping(value = "/levelList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> levelList(String locale) {
    	
    	List<Dictionary> dicList = dictionaryService.findByCode("LEVEL");
    	List<String> res = new ArrayList<String>();
    	for(int i=0;i<dicList.size();i++){
    		res.add(dicList.get(i).getDictShow());
    	}
    	return res;
    }
    @RequestMapping(value = "/workList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> workList() {
    	List<Dictionary> dicList = dictionaryService.findByCode("WORK");
    	List<String> res = new ArrayList<String>();
    	for(int i=0;i<dicList.size();i++){
    		res.add(dicList.get(i).getDictShow());
    	}
    	return res;
    }
    @RequestMapping(value = "/addmember", method = RequestMethod.GET)
    @ResponseBody
    public boolean addmember(String name,String sex,String work,String home,String father,String mother,String mobile,String children,String level,String birthday,String deathday,int status) {
    	
    	Member member = new Member();
    	Member fatherDto=null;
    	if(Integer.valueOf(level)!=1){
    		fatherDto = memberService.getByLevelAndName(Integer.valueOf(level)-1,father);
    	}
		
		
    	int levelnumber = Integer.valueOf(level);
    	String indexCode="";
    	for(int i=0;i<levelnumber;i++){
    		indexCode+="0";
    	}
    	member.setIndexCode(indexCode);
    	if(fatherDto!=null){
    		List<String> childrenIndexList= memberService.getChildrenIndexListByFatherIndex(fatherDto.getIndexCode());
    		int flag=1;
    		if(childrenIndexList!=null){
    			for (String index:childrenIndexList){
        			int temp = Integer.valueOf(index.substring(fatherDto.getIndexCode().length()));
        			if(temp>flag){
        				flag=temp;
        			}
        		}
    			flag++;
    		}
    		String index = fatherDto.getIndexCode()+String.valueOf(flag);
    		member.setIndexCode(index);
    	}
    	member.setSex(sex);
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	try {
			member.setBirthDay(sdf.parse(birthday));
			if(deathday!=null&&deathday.length()!=0){
				member.setDeathDay(sdf.parse(deathday));
			}
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    		member.setUsername(name);
			member.setChildren(children);
			member.setWork(work);
	    	member.setFather(father);
	    	member.setMother(mother);
	    	member.setHome(home);
		
    	member.setLevelCode(Integer.valueOf(level));
    	member.setMobile(mobile);
    	memberService.save(member);
    	return true;
    }
    @RequestMapping(value = "/updatemember", method = RequestMethod.GET)
    @ResponseBody
    public Boolean updatemember(String memberIndex,String name,String sex,String work,String home,String father,String mother,String mobile,String children,String level,String birthday,String deathday,int status) {
    	
    	Member member = memberService.getByIndex(memberIndex);
    	member.setSex(sex);
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	try {
			member.setBirthDay(sdf.parse(birthday));
			if(deathday!=null&&deathday.length()!=0){
				member.setDeathDay(sdf.parse(deathday));
			}
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    		member.setUsername(name);
			member.setChildren(children);
			member.setWork(work);
	    	member.setFather(father);
	    	member.setMother(mother);
	    	member.setHome(home);
	    	member.setStatus(status);

    	member.setLevelCode(Integer.valueOf(level));
    	member.setMobile(mobile);
    	beanValidator(member);
    	memberService.update(member);
    	return true;
    }
    

   


    
}
