package com.amitnehra.repo;


import com.amitnehra.mappers.FriendRequestMapper;
import com.amitnehra.models.FriendRequests;
import com.amitnehra.models.enums.RequestStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Collections;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

@Repository
public class FriendRequestsRepoImpl implements FriendRequestsRepo {

    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;
    private static final Logger logger = LogManager.getLogger(FriendRequestsRepoImpl.class.getName());

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }

    @Override
    @Transactional
    public List<FriendRequests> findPendingRequestsBy(String id) {
        try {
            Session session = getCurrentSession();
            String sql = "SELECT fr.requestId AS requestId, fr.fromAccount AS fromAccount, " +
                    "       fr.toAccount AS toAccount, fr.timeStamp AS timeStamp, fr.status AS status " +
                    "FROM FriendRequests fr " +
                    "WHERE fr.fromAccount = :id AND fr.status = :status";

            Query<Tuple> query = session.createQuery(sql, Tuple.class);
            query.setParameter("id", id);
            query.setParameter("status", RequestStatus.PENDING);
            List<FriendRequests> pendingRequests = FriendRequestMapper.mapTuple(query.getResultList());
            return pendingRequests;
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        }
        catch (RuntimeException e){
            logger.error("Error in findPendingRequest in friend Requests repo");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void save(FriendRequests friendRequests) {
        try {
            Session session = getCurrentSession();
            session.save(friendRequests);
        }
        catch (RuntimeException e){
            logger.error("Error in save in friend Requests repo");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public FriendRequests findRequest(String id, String id1) {
        try {
            Session session = getCurrentSession();
            System.out.println("Request from "+ id+"  | to " + id1);
            Query<FriendRequests> query = session.createQuery("From FriendRequests  fr where fr.fromAccount = :id and fr.toAccount = :id1", FriendRequests.class);
            query.setParameter("id", id);
            query.setParameter("id1", id1);
            return query.getSingleResult();
        }
        catch (NoResultException e1){
            System.out.println("No Request found in Repo");
            logger.error(e1.getMessage());
            return null;
        }
        catch (RuntimeException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void remove(FriendRequests friendRequests) {
        try {
            Session session = getCurrentSession();
            session.remove(friendRequests);
        }
        catch (RuntimeException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<Object[]> findFriendSuggestions(String id) {
        int ordValue = RequestStatus.ACCEPTED.ordinal();
        String s = "SELECT fr.toAccount AS suggestedFriend, COUNT(fr.toAccount) AS mutualFriendCount\n" +
                "        FROM FriendRequests fr\n" +
                "        WHERE fr.fromAccount IN (\n" +
                "            SELECT f.toAccount \n" +
                "            FROM FriendRequests f \n" +
                "            WHERE f.fromAccount = :currentUserId AND f.status = :statusValue\n" +
                "        )\n" +
                "        AND fr.status = :statusValue\n" +
                "        AND fr.toAccount != :currentUserId\n" +
                "        AND fr.toAccount NOT IN (\n" +
                "            SELECT f.toAccount \n" +
                "            FROM FriendRequests f \n" +
                "            WHERE f.fromAccount = :currentUserId AND f.status = :statusValue\n" +
                "        )\n" +
                "        GROUP BY fr.toAccount\n" +
                "        ORDER BY mutualFriendCount DESC limit 10";
        try {
            Session session = getCurrentSession();
            Query query = session.createNativeQuery(s);
            query.setParameter("currentUserId", id);
            query.setParameter("statusValue", ordValue);
            return query.getResultList();
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        }
        catch (RuntimeException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<FriendRequests> findPendingRequestsTo(String id) {
        try {
            Session session = getCurrentSession();
            String sql = "FROM FriendRequests fr " +
                    "WHERE fr.toAccount = :id AND fr.status = :status";

            Query query = session.createQuery(sql, FriendRequests.class);
            query.setParameter("id", id);
            query.setParameter("status", RequestStatus.PENDING);
            return query.getResultList();
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        }
        catch (RuntimeException e){
            logger.error("Error in findPendingRequest in friend Requests repo");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<FriendRequests> findAcceptedRequests(String id) {
        try {
            Session session = getCurrentSession();
            String sql = "FROM FriendRequests fr " +
                    "WHERE (fr.toAccount = :id OR fr.fromAccount = :id) AND fr.status = :status";

            Query query = session.createQuery(sql, FriendRequests.class);
            query.setParameter("id", id);
            query.setParameter("status", RequestStatus.ACCEPTED);
            return query.getResultList();
        }
        catch (NoResultException e1){
            logger.error(e1.getMessage());
            return null;
        }
        catch (RuntimeException e){
            logger.error("Error in findAcceptedRequested in friend Requests repo");
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
