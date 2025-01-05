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
            Query query = session.createNativeQuery("Select m from message where (m.fromaccount_id = :sender AND m.toaccount_id = :receiver)"+
                    "(m.fromaccount_id = :receiver AND m.toaccount_id = :sender) ORDER BY m.timestamp ASC");
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
}

