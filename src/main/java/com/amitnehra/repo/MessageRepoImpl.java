package com.amitnehra.repo;

import com.amitnehra.exceptions.MessagesException;
import com.amitnehra.models.Message;
import net.sf.jasperreports.engine.util.JRStyledText;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MessageRepoImpl implements MessageRepo {
    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;
    private static final Logger logger = LogManager.getLogger(MessageRepoImpl.class.getName());

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }

    @Override
    @Transactional
    public List<Message> findBySenderAndReceiver(String receiverId, String senderId){
        try {
            Session session = getCurrentSession();
            Query query = session.createNativeQuery("Select * from message where (fromaccount_id = :sender AND toaccount_id = :receiver)"+
                    "OR (fromaccount_id = :receiver AND toaccount_id = :sender) ORDER BY timestamp DESC;", Message.class);
            query.setParameter("sender", senderId);
            query.setParameter("receiver", receiverId);
            return (List<Message>) query.getResultList();
        }catch(RuntimeException e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public void save(Message message) {
        try {
            Session session = getCurrentSession();
            session.save(message);
        } catch (Exception e) {
            logger.error("Error in save in AccountRepoImpl.");
            logger.error(e.getMessage());
            throw new MessagesException("Exception while saving message in database.");
        }
    }

    @Override
    @Transactional
    public Message getLastMessage(String id, String id1) {
        try{
            Session session = getCurrentSession();
            String s = "Select * from message where (fromaccount_id = :ab AND toaccount_id = :ba) "+
                    "OR (fromaccount_id = :ba AND toaccount_id = :ab) ORDER BY timestamp DESC LIMIT 2;";
            Query query = session.createNativeQuery(s, Message.class);
            query.setParameter("ab", id);
            query.setParameter("ba", id1);
            List<Message> list = query.getResultList();
            return list.get(0);
        }catch (IndexOutOfBoundsException e){
            logger.error("No message found among two users in getLastMessage.");
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

