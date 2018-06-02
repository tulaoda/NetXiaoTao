package com.xt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xt.entity.Chat;
import com.xt.entity.Wannas;


@Repository
public class ChatItemDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public List<Chat> findAllMyChatItem(Long s_userid,Long r_userid){
		String hql="from Chat c where c.s_userid=? and c.r_userid =? order by c.createTime";
		return sessionFactory.getCurrentSession().createQuery(hql).setLong(0, s_userid).setLong(1, r_userid).list();
	}
	
	public void removeChatItem(Chat c){
		sessionFactory.getCurrentSession().delete(c);
	}
	

	public List<Chat> findAllMyChatItemForPage(Long s_userid,Long r_userid,int pageSize,int page){
		String hql="from Chat c where c.s_userid=? and c.r_userid =? order by c.createTime";
		return	sessionFactory.getCurrentSession().createQuery(hql).setLong(0, r_userid).setLong(1, s_userid)
		.setFirstResult((page-1)*pageSize)
		.setMaxResults(pageSize)
		.setMaxResults(pageSize).list(); 
		
	}
	
	public void addNewChatItem(Chat c){
		sessionFactory.getCurrentSession().save(c);
	}
	
	public Chat findMaxIdChatItem(){
		String hql = "from Chat where id=(select max(id) from Chat) ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Chat chatItem=(Chat) query.uniqueResult();
		return chatItem;
	}
}
