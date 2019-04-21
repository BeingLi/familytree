package abcreate.fb.modules.fb.dao;


import abcreate.fb.common.persistence.CrudDao;
import abcreate.fb.common.persistence.annotation.MyBatisDao;
import abcreate.fb.modules.fb.entity.Dictionary;
import abcreate.fb.modules.fb.entity.Member;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Project:health-backend
 * Created By zhang tengfei
 * Date:2016/9/20
 * Time:9:57
 */
@MyBatisDao
public interface MemberDAO extends CrudDao<Member> {
	public void save(Member member);
    public List<Member> listMemberByLevel(@Param("level")Integer level);
    public Member get(@Param("id")String id);
//    {
//        if (null != level) {
//            String hql = "from Member where levelCode=?";
//            return list(hql, new Integer[]{level});
//        } else {
//            String hql = "from Member ";
//            return list(hql, null);
//        }
//    }
    public Member getByIndex(@Param("index")String index);
//    {
//    	String hql = "from Member where indexCode=?";
//        return (Member) queryByHql(hql, new String[]{index});
//    }
    
    
    
    //需将level减1之后再传过来
    public Member getByLevelAndName(@Param("level")Integer level,@Param("name")String name);
//    {
//    	level--;
//    	String sql = "select * from Member where levelCode=:level and username=:name";
//    	Query query = createSQLQuery(sql.toString());
//    	query.setParameter("level", level);
//    	query.setParameter("name", name);
//    	List<Object[]> dataList = query.list();
//    	Object object =null;
//    	if(dataList.size()!=0){
//    		object= dataList.get(0);
//    	}
//    	Member member=null;
//    	if(object!=null){
//    		Member temp=(Member)object;
//    		member.setId(temp.getId());
//    		member.setCreateTime(temp.getCreateTime());
//    		member.setUpdateTime(temp.getUpdateTime());
//    		member.setCreateBy(temp.getCreateBy());
//    		member.setUpdateBy(temp.getUpdateBy());
//    		member.setBirthDay(temp.getBirthDay());
//    		member.setDeathDay(temp.getDeathDay());
//    		member.setIndex(temp.getIndex());
//    		member.setUsername(temp.getUsername());
//    		member.setMobile(temp.getMobile());
//    		member.setWork(temp.getWork());
//    		member.setHome(temp.getHome());
//    		member.setSex(temp.getSex());
//    		member.setFather(temp.getFather());
//    		member.setMother(temp.getMother());
//    		member.setChildren(temp.getChildren());
//    		member.setLevel(temp.getLevel());
//    		member.setStatus(temp.getStatus());
//    	}
//        return member ;
//    }
    
    public List<Member> findByName(@Param("Name")String Name);
//    {
//        String hql = "from Member where userName like ?";
//        return list(hql, new String[]{"%"+Name+"%"});
//    }
    public List<String> getChildrenIndexListByFatherIndex(@Param("fatherIndex")String fatherIndex);
//    {
//    	String hql = "from Member where index like ?";
//    	List<Member> list = list(hql, new String[]{fatherIndex+"[1-9]"});
//    	List<String> indexList = new ArrayList<String>();
//    	for(Member member:list){
//    		indexList.add(member.getUsername());
//    	}
//    	return indexList;
//    }
}

