What is this?
-------------

This library lets you send multi-lingual and Freemarker-templated e-mails using one of many pre-configured SMTP sender accounts.

With this framework you can:

* Statically specify different sender accounts (name + e-mail address), via a JSON configuration file.
* ... or overwrite them in your code at run-time, for each call, if needed.
* Decide, at run-time, that e-mail X should be sent from a valid reply-to address while e-mail Y should be sent from a bouncing address.
* Use all of Freemarkers templating toolsets to create your HTML or text-only e-mails.
* You may (optionally) specify the e-mail headers, the subject line and the body - all decorated with Freemarker code, if needed.

All templates may reside in different folders, following a clean and pre-defined filename structure:

    my-email-templates/ (or whatever you want to call this folder)
      en/ (additional directory level, to separate multi-lingual templates from each other)
        signup_headers.ftl (optional)
        signup_subject.ftl (mandatory)
        signup_body.ftl (mandatory)
      de/
        signup_headers.ftl (optional)
        signup_subject.ftl (mandatory)
        signup_body.ftl (mandatory)

Example
-------

The following code creates the mapped parameters, containing framework- and template-specific variables, and specifies who to send the e-mail to.

    Map<String, Object> mappedParameters = new HashMap<String, Object>();

    // This is one of the specified senders in the smtp.json configuration file
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_CONFIGURATION_NAME, "info");

    // The path where the Freemarker template files reside
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH, "/email_templates/en/");

    // Optional: if you want to use this class' classloader for loading the templates
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH_CLASS, this.getClass());

    // Alternatively to the above two commands: give it a directory path to load the templates from
    // mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_PATH_FILE, new File("/var/www/mywebsite/htdocs/email_templates/en/"));

    // The prefix of the template files. They need to look like: PREFIX_headers.ftl, PREFIX_subject.ftl and PREFIX_body.ftl
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_NAME, "signup");

    // The type of e-mail to send: HTML or raw e-mail. HTML e-mail templates have the extension .ftl.html and raw e-mails have .ftl.txt.
    // TemplateTypeEnum.BOTH = Send both, with e-mail clients not capable of showing HTML e-mails falling back to the Text-only version.
    // TemplateTypeEnum.ANY = Send whatever template is found. Incurs a slight performance overhead due to a try/catch block when looking for the templates.
    // TemplateTypeEnum.HTML = Only look for and send an HTML e-mail. Will look for signup_body.ftl.html when assembling the body of the e-mail.
    // TemplateTypeEnum.TEXT = Only look for and send a Text-only e-mail. Will look for signup_body.ftl.txt when assembling the body of the e-mail.
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_TEMPLATE_TYPE, TemplateTypeEnum.BOTH);

    // The firstname, lastname and e-mail address of the recipient
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_FIRSTNAME, "Peter");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_LASTNAME, "Recipientname");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_RECIPIENT_EMAIL, "peter.recipientname@mydomain.com");

    // Of course you may also add any Freemarker variables you need
    mappedParameters.put("link", "http://www.jaide.de/projects/notify/confirm?id=123");
    
    // And as of version 1.1 you may also add attachments
    List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
    EmailAttachment attachment = new EmailAttachment();
    attachment.setPath("target/test-classes/BabyOngBak.jpg");
    attachment.setDisposition(EmailAttachment.ATTACHMENT);
    attachment.setDescription("Picture of Baby Ong Bak");
    attachment.setName("BabyOngBak.jpg");
    attachments.add(attachment);
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_ATTACHMENTS, attachments);

The template file for the subject could look like this:

    signup_subject.ftl:
    <#if !recipientFirstname??>Please activate your account<#else>${recipientFirstname}, please activate your account</#if>

The template file for the body content could look like this:

    signup_body.ftl:
    Hello<#if recipientFirstname != ""> ${recipientFirstname}</#if>,
    
    You have registered a new account on our website but it has not been activated because your e-mail address has not been confirmed yet.
    Please activate your account by clicking the following link.
    Activate account: ${link}
    
    Thanks
    The Team

The header file `PREFIX_headers.ftl` doesn't have to be there, you only have to provide a `PREFIX_subject.ftl` and `PREFIX_body.ftl` template file:

    signup_headers.ftl:
    Reply-To: Peter Replyname <peter.replyname@mydomain.com>

Now send the e-mail, using the `smtp.json` file that resides in the root of your classpath as `/smtp.json`:

    CourierService.getInstance().getMessageHandlerEMail("/smtp.json").handleMessage(mappedParameters);

This will use the SMTP sender configuration specified as "info" in your smtp.json:

    [{
       "info":{
          "smtpHostname":"smtp.gmail.com",
          "smtpPort":25,
          "authentication":true,
          "username":"info@mydomain.com",
          "password":"XXXXXXX",
          "fromEMail":"info@mydomain.com",
          "fromSenderName":"John Doe - Company Inc."
       }
    },{
       "default":{
          "smtpHostname":"smtp.gmail.com",
          "smtpPort":25,
          "authentication":true,
          "username":"peter.sendername@mydomain.com",
          "password":"xxxxxxx",
          "fromEMail":"peter.sendername@mydomain.com",
          "fromSenderName":"Peter Sendername"
       }
    },{
       "bounce":{
          "smtpHostname":"smtp.gmail.com",
          "smtpPort":25,
          "authentication":true,
          "username":"no-reply@mydomain.com",
          "password":"xxxxxxx",
          "fromEMail":"no-reply@mydomain.com",
          "fromSenderName":"No-Reply"
       }
    }]

And as you can see the sender is also specified there. If, for any reason, this statically defined sender is not what you want to show up you may override it by specifying a different sender prior to the call:

    // For the next e-mail sending specify a different person as the sender than what was configured in smtp.json
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_FIRSTNAME, "Peter Other");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_LASTNAME, "Sendername");
    mappedParameters.put(MessageHandlerEMail.MAPPING_PARAM_SENDER_EMAIL, "peter-other.sendername@mydomain.com");
    mappedParameters.put("link", "http://www.jaide.de/projects/notify/confirm?id=456");

... and then send the e-mail:

    CourierService.getInstance().getMessageHandlerEMail("/smtp.json").handleMessage(mappedParameters);

Integrating into your code
--------------------------

* Clone this project.
* You will need Java 6 and Maven 3 to build the code.
* Build your code with Maven: `mvn clean package -DskipTests=false`.
* You will see that the test will fail - it fails because you didn't provide your smtp.json configuration file.
* Copy the `smtp.json.template` file and save it as `smtp.json` somewhere in your classpath, preferrably at its root so you don't have to modify the provided test class.
* Adjust the settings in that smtp.json configuration file and run `mvn clean package -DskipTests=false` again.
* Things should work fine now - now put the resulting .jar file into your application's library folder.

If you're using Maven to build your project add this framework's Maven dependencies to your pom.xml:

    <!-- JAIDE's Courier framework helps with sending out multi-lingual  -->
    <!-- and Freemarker-templated e-mails with dynamic sender addresses. -->
    <dependency>
      <groupId>de.jaide</groupId>
      <artifactId>courier</artifactId>
      <version>1.2</version>
    </dependency>

* Note: this framework will show up on Maven Central soon - it's not there yet. The above Maven coordinates might not work as of now, so please build the library yourself and deploy it into your local Maven repository until then by running "mvn install".

What's next?
------------

If/when the need arises we <a href="http://twitter.com/JAIDE">@JAIDE</a> will add additional message handlers, besides the one we added for e-mails, so you can also send templated Apple Push Notifications.
You're invited to join and implement message handlers yourself - just implement MessageHandler.java for your specific needs and initiate a pull request.

Developed By
------------

* Rias A. Sherzad, JAIDE GmbH

License
-------

    Copyright 2013 JAIDE GmbH
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.