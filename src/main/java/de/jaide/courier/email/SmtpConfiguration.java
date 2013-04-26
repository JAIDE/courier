/*
 * Copyright 2008-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.jaide.courier.email;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.tonian.director.dm.json.JSONWriter;

/**
 * Holds different SMTP configurations for different message types.<br/>
 * For instance the sending of a "Registration completed" e-mail might need to be made from a "no-reply" account while support e-mails might
 * always have to have a proper sender address that the recipient may reply to.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public class SmtpConfiguration implements JSONAware {

  private String configurationName;
  private String smtpHostname;
  private Integer smtpPort;
  private boolean authentication;
  private String username;
  private String password;
  private String fromEMail;
  private String fromSenderName;

  /**
   * Creates a new SMTP configuration.
   */
  public SmtpConfiguration() {
  }

  /**
   * Creates a new SMTP configuration.
   * 
   * @param configurationName The name of this configuration.
   * @param smtpHostname The SMTP hostname.
   * @param smtpPort The port of the SMTP server.
   * @param authentication True, if authentication against the SMTP server is to be done.
   * @param username The username on the SMTP server.
   * @param password The password of the username on the SMTP server.
   * @param fromEMail The e-mail address to be set as the sender.
   * @param fromSenderName The full name to be set as the sender.
   */
  public SmtpConfiguration(String configurationName, String smtpHostname, Integer smtpPort, boolean authentication, String username,
      String password, String fromEMail, String fromSenderName) {
    this.configurationName = configurationName;
    this.smtpHostname = smtpHostname;
    this.smtpPort = smtpPort;
    this.authentication = authentication;
    this.username = username;
    this.password = password;
    this.fromEMail = fromEMail;
    this.fromSenderName = fromSenderName;
  }

  /**
   * The name of this configuration. Used to differentiate between SMTP servers that are used for different purposes.
   * 
   * @return The name of this configuration.
   */
  public String getConfigurationName() {
    return configurationName;
  }

  /**
   * Sets the name of this configuration. Used to differentiate between SMTP servers that are used for different purposes.
   * 
   * @param configurationName The name of this configuration to set.
   */
  public void setConfigurationName(String configurationName) {
    this.configurationName = configurationName;
  }

  /**
   * The hostname, meaning: the IP address or fully-qualified hostname of the SMTP server.
   * 
   * @return The SMTP hostname.
   */
  public String getSmtpHostname() {
    return smtpHostname;
  }

  /**
   * Sets the hostname, meaning: the IP address or fully-qualified hostname of the SMTP server.
   * 
   * @param smtpHostname The SMTP hostname to set.
   */
  public void setSmtpHostname(String smtpHostname) {
    this.smtpHostname = smtpHostname;
  }

  /**
   * The port of the SMTP server.
   * 
   * @return The port of the SMTP server.
   */
  public Integer getSmtpPort() {
    return smtpPort;
  }

  /**
   * Sets the port of the SMTP server.
   * 
   * @param smtpPort The port of the SMTP server to set.
   */
  public void setSmtpPort(Integer smtpPort) {
    this.smtpPort = smtpPort;
  }

  /**
   * True if authentication against the SMTP server needs to be done.
   * 
   * @return True, if authentication against the SMTP server is to be done.
   */
  public boolean isAuthentication() {
    return authentication;
  }

  /**
   * Sets the need for authentication against the SMTP server.
   * 
   * @param authentication Set to true if authentication against the SMTP server needs to be done.
   */
  public void setAuthentication(boolean authentication) {
    this.authentication = authentication;
  }

  /**
   * The username on the SMTP server that has permission to send e-mails.
   * 
   * @return The username on the SMTP server.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username on the SMTP server that has permission to send e-mails.
   * 
   * @param username The username on the SMTP server to set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * The password of the username on the SMTP server that has permission to send e-mails.
   * 
   * @return The password of the username on the SMTP server.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the username on the SMTP server that has permission to send e-mails.
   * 
   * @param password The password of the username on the SMTP server to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Used in the "From:" line of the e-mails that are sent out.
   * 
   * @return The e-mail address to be set as the sender.
   */
  public String getFromEMail() {
    return fromEMail;
  }

  /**
   * Sets the e-mail that is to be used in the "From:" line of the e-mails that are sent out.
   * 
   * @return fromEMail The e-mail address to be set as the sender.
   */
  public void setFromEMail(String fromEMail) {
    this.fromEMail = fromEMail;
  }

  /**
   * The full name (firstname lastname) that is to be used in the "From:" line of the e-mails that are sent out.
   * 
   * @return The full name to be set as the sender.
   */
  public String getFromSenderName() {
    return fromSenderName;
  }

  /**
   * Sets the full name (firstname lastname) that is to be used in the "From:" line of the e-mails that are sent out.
   * 
   * @return fromSenderName The full name to be set as the sender.
   */
  public void setFromSenderName(String fromSenderName) {
    this.fromSenderName = fromSenderName;
  }

  /**
   * Returns the SMTP configuration as a JSON string.
   * 
   * @return The SMTP configuration as a JSON string.
   */
  @SuppressWarnings("unchecked")
  @Override
  public String toJSONString() {
    JSONObject entry = new JSONObject();

    Map<String, Object> configuration = new LinkedHashMap<String, Object>();
    configuration.put("smtpHostname", smtpHostname);
    configuration.put("smtpPort", new Integer(smtpPort));
    configuration.put("authentication", authentication);
    configuration.put("username", username);
    configuration.put("password", password);
    configuration.put("fromEMail", fromEMail);
    configuration.put("fromSenderName", fromSenderName);

    entry.put(configurationName, configuration);

    /*
     * The JSONWriter will pretty-print the output
     */
    Writer jsonWriter = new JSONWriter();
    try {
      entry.writeJSONString(jsonWriter);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }

    return jsonWriter.toString();
  }
}
