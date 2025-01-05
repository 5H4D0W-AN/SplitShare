package com.amitnehra.repo;


import com.amitnehra.models.Account;
import com.amitnehra.models.Comments;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository
public class CommentRepoImpl implements CommentRepo{
    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;
    private static final Logger logger = LogManager.getLogger(CommentRepoImpl.class.getName());

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }

    @Override
    @Transactional
    public List<Comments> getCommentsFor(Long id) {
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery("From Comment where id = :id");
            query.setParameter("id", id);
            return (List<Comments>) query.getResultList();
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error in findUser in CommentRepo while retrieving Comments for a particular post.");
            logger.error(e.getMessage());
            return null;
        }
    }
}
