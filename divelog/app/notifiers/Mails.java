package notifiers;

import play.*;
import play.mvc.*;
import play.i18n.*;

import java.util.*;

import models.FriendRequestTask;
import models.InvitationMailTask;
import models.MailTask;
import models.User;

public class Mails extends Mailer {

	public static void confirmation(User user) {
		Logger.info("Confirmation email: sending to " + user.email);

		setSubject(Messages.get("emailconfirmationsubject"));
		addRecipient(user.email);
		setFrom("notification@beansight.com");

		send(user);
	}

	public static void followNotification(FriendRequestTask task) {
		sendMailTask(task, Messages.get("emailfollownotificationsubject", task.follower.userName), "Mails/followNotification.html");
	}

	public static void invitation(InvitationMailTask task) {
		sendMailTask(task, Messages.get("emailinvitationsubject", task.invitation.invitor.userName), "Mails/invitation.html");
	}
	
	private static void sendMailTask(MailTask task, String subject, String templateName) {
		task.attempt++;
		Logger.info("MailTask " + task.getClass().getSimpleName() + " to: " + task.sendTo);

		setSubject(subject);
		addRecipient(task.sendTo);
		setFrom("notification@divelog.com");

		send(templateName, task);
	}
	
}
