package com.nightswatch.service.impl;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.service.MailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
//@Transactional(propagation = Propagation.REQUIRED)
public class MailingServiceImpl implements MailingService {

    private static final Logger logger = LoggerFactory.getLogger(MailingServiceImpl.class);

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailingServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(User user) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setSubject("Nights-Watch: Reset Password");
        simpleMailMessage.setText(
                "Dear " + user.getFullName() + "\n\n "
                        + "A new password is created as you wish. \n"
                        + "New Password: " + user.getPassword() + " \n "
                        + " Please change it on your first login. \n\n " +
                        "Best Regards "
        );

        try{
            this.mailSender.send(simpleMailMessage);
        }catch (Exception e){
            logger.error("Error while sending reset password email", e);
        }

    }

    @Override
    public void sendViolationUpdatedEmail(User user, Violation violation) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setSubject("Nights-Watch: Reset Password");
        simpleMailMessage.setText(
                "Dear " + user.getFullName() + "\n\n "
                        + "The violation " + violation.getTitle() + " is updated. \n"
                        + "You can check it through our web site. " +
                        "Best Regards "
        );

        try{
            this.mailSender.send(simpleMailMessage);
        }catch (Exception e){
            logger.error("Error while sending violation updated email", e);
        }
    }

}
