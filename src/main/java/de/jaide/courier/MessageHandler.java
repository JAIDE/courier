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

import java.util.Map;

import de.jaide.courier.exception.CourierException;

/**
 * Defines the interface that a message handler needs to support.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public interface MessageHandler {

  /**
   * Sends the message and in that process uses the mapped parameters.
   * 
   * @param mappedParameters Mapped parameters that may be used in the templating/message sending process.
   * @throws CourierException Thrown in case of a Runtime problem.
   */
  public void handleMessage(Map<String, Object> mappedParameters) throws CourierException;
}
