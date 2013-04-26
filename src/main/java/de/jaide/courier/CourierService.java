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
package de.jaide.courier;

import java.io.IOException;

import de.jaide.courier.email.MessageHandlerEMail;

/**
 * Instantiates the Singleton and provides static methods for returning handler services.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public class CourierService {
  /**
   * Singleton pattern...
   */
  private static CourierService instance = new CourierService();

  /**
   * The message handler for sending e-mails
   */
  MessageHandlerEMail email = null;

  /**
   * Singleton pattern...
   */
  private CourierService() {
  }

  /**
   * Returns the only instance of this class.
   * 
   * @return The only instance of this class.
   */
  public static CourierService getInstance() {
    return instance;
  }

  /**
   * Returns the message handler for e-mails.
   * 
   * @param smtpConfiguration The classpath: URL to the SMTP configuration (JSON file). May be null if called more than once.
   * @return The message handler for e-mails
   * @throws IOException Thrown, if the SMTP configuration couldn't be read.
   */
  public MessageHandlerEMail getMessageHandlerEMail(String smtpConfiguration) throws IOException {
    /*
     * Lazily initialized
     */
    if (email == null)
      email = new MessageHandlerEMail(smtpConfiguration);

    return email;
  }
}
