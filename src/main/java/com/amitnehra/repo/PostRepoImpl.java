package com.amitnehra.repo;

import com.amitnehra.models.Account;
import com.amitnehra.models.Post;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Controller
public class PostRepoImpl implements PostRepo{
    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;
    private static final Logger logger = LogManager.getLogger(PostRepoImpl.class.getName());

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }
    @Override
    @Transactional
    public List<Post> fetchPosts(String id) {
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery("From Post where accountId = :id");
            query.setParameter("id", id);
            return (List<Post>) query.getResultList();
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error in findUser in PostRepo while retrieving posts.");
            logger.error(e.getMessage());
            return null;
        }
    }
}
