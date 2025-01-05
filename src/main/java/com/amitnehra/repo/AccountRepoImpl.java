package com.amitnehra.repo;

import com.amitnehra.models.Account;
import com.amitnehra.models.Profile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AccountRepoImpl implements AccountRepo{

    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;
    private static final Logger logger = LogManager.getLogger(AccountRepoImpl.class.getName());

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }

    @Override
    @Transactional
    public Account findUser(String userId) {
        try {
            Session session = getCurrentSession();
            return session.get(Account.class, userId);
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error in findUser in AccountRepoImpl while retrieving User.");
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public boolean save(Account account) {
        try {
            Session session = getCurrentSession();
            session.save(account);
            return true;
        } catch (Exception e) {
            logger.error("Error in save in AccountRepoImpl.");
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void update(Account account) {
        try {
            Session session = getCurrentSession();
            session.update(account);
        } catch (Exception e) {
            logger.error("Error in update in AccountRepoImpl.");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void merge(Account account) {
        try {
            Session session = getCurrentSession();
            session.merge(account);
        } catch (Exception e) {
            logger.error("Error in update in AccountRepoImpl.");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public boolean checkForEmail(String email) {
        try {
            Session session = getCurrentSession();
            String s = "FROM Account where email = ?1";
            Query query = session.createQuery(s);
            query.setParameter(1, email);
            Account firstResult = (Account) query.getSingleResult();
            if(firstResult != null) return true;
            return false;
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return true;
        } catch (Exception e) {
            logger.error("Error in checkForEmail in AccountRepoImpl.");
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean checkForId(String id) {
        try {
            Session session = getCurrentSession();
            String s = "FROM Account where id = ?1";
            Query query = session.createQuery(s);
            query.setParameter(1, id);
            Account firstResult = (Account) query.getSingleResult();
            if(firstResult != null) return true;
            return false;
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return true;
        } catch (Exception e) {
            logger.error("Error in checkForId in AccountRepoImpl.");
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public List<Profile> findProfiles(String str) {
        List<Profile> retList = new ArrayList<>();
        if(str == null || str.length() == 0) return retList;
        try {
            Session session = getCurrentSession();
            String c = "%"+str+"%";
            String s = "select * from account where id like '" + c + "' or name like '"+c+"';";
            Query query = session.createNativeQuery(s, Account.class);
            List<Account> resultList = query.getResultList();
            for(Account acc : resultList) {
                retList.add(acc.getProfile());
            }
            return retList;
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error in findProfiles in AccountRepo.");
            return null;
        }
    }

    @Override
    @Transactional
    public List<Account> getAccountsForFriends(Integer a) {
        try {
            Session session = getCurrentSession();
            String s = "select * from account limit " + a.toString() +";";
            Query query = session.createNativeQuery(s, Account.class);
            List<Account> resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error in getAccountsForFriends in AccountRepo.");
            return null;
        }
    }
}
