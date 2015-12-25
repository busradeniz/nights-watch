package com.nightswatch.service;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;

import java.io.Serializable;

public interface MailingService extends Serializable{

    void sendPasswordResetEmail(final User user);

    void sendViolationUpdatedEmail(final User user, final Violation violation);
}
