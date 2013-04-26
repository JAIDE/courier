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
package de.jaide.courier.exception;

/**
 * Thrown if the sending of a message failed.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public class CourierException extends RuntimeException {
  private static final long serialVersionUID = 3475714527745368076L;

  /**
   * Default constructor. Does nothing special, just looks good.
   */
  public CourierException() {
    super();
  }

  /**
   * Creates a new NotificationException.
   * 
   * @param message The message that yielded this Exception.
   * @param throwable The wrapped throwable.
   */
  public CourierException(String message, Throwable throwable) {
    super(message, throwable);
  }

  /**
   * Creates a new NotificationException.
   * 
   * @param message The message that yielded this Exception.
   */
  public CourierException(String message) {
    super(message);
  }

  /**
   * Creates a new NotificationException.
   * 
   * @param throwable The wrapped throwable.
   */
  public CourierException(Throwable throwable) {
    super(throwable);
  }
}
