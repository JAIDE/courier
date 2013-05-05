package de.jaide.courier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailAttachment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.jaide.courier.email.MessageHandlerEMail;
import de.jaide.courier.email.TemplateTypeEnum;
import de.jaide.courier.exception.CourierException;

/**
 * Tests the implemented message handlers.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public class TestMessageHandlers {

  @BeforeClass
  public void init() {
  }

  /**
   * Test the message handler for e-mails.
   * 
   * @throws IOException
   * @throws CourierException
   */
  @Test
  public void testMessageHandlerForEMails_Signup() throws CourierException, IOException {
    Map<String, Object> mappedParameters = new HashMap<String, Object>();

    /*
     * Configuration parameters
     */
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_CONFIGURATION_NAME, "info");

    // Load the templates using a regular Java classloader
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH, "/email_templates/en/");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH_CLASS, this.getClass());

    // Or, alternatively, give it a directory path to load the templates from
    // mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH_FILE, new File("target/classes/email_templates/en/"));

    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_NAME, "contact_accept");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_TYPE, TemplateTypeEnum.BOTH);
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_FIRSTNAME, "Peter");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_LASTNAME, "Recipientname");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_EMAIL, "peter.sendername@mydomain.com");

    /*
     * Set the parseable parameters for the actual template file
     */
    mappedParameters.put("link", "http://www.jaide.de/projects/notify/confirm?id=123");

    /*
     * Optional step: we want to overwrite the sender's firstname, lastname and e-mail that was configured in the smtp.json.
     * This is an absolute optional step - you can just go with the sender configured in the smtp.json if it doesn't change on each e-mail
     * sent out.
     */
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_FIRSTNAME, "Peter Different");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_LASTNAME, "Sendername");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_EMAIL, "peter-different.sendername@mydomain.com");

    /*
     * Send the e-mail
     */
    CourierService.getInstance().getMessageHandlerEMail("/smtp.json").handleMessage(mappedParameters);

    /*
     * Send a second e-mail, with a different sender name, a different template variable "link" and an attachment.
     */
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_FIRSTNAME, "Peter Other");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_LASTNAME, "Sendername");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_EMAIL, "peter-other.sendername@mydomain.com");
    mappedParameters.put("link", "http://www.jaide.de/projects/notify/confirm?id=456");

    List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
    EmailAttachment attachment = new EmailAttachment();
    attachment.setPath("target/test-classes/BabyOngBak.jpg");
    attachment.setDisposition(EmailAttachment.ATTACHMENT);
    attachment.setDescription("Picture of Baby Ong Bak");
    attachment.setName("BabyOngBak.jpg");
    attachments.add(attachment);
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_ATTACHMENTS, attachments);

    /*
     * And now send this e-mail as well.
     */
    CourierService.getInstance().getMessageHandlerEMail("/smtp.json").handleMessage(mappedParameters);
  }
}
