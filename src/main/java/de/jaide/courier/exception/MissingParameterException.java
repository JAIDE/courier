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
 * Thrown if an expected parameter is missing.
 * 
 * @author Rias A. Sherzad, JAIDE GmbH // http://www.jaide.de
 */
public class MissingParameterException extends RuntimeException {

  private static final long serialVersionUID = 8832065480539294718L;

  /**
   * Default constructor. Does nothing special, just looks good.
   */
  public MissingParameterException() {
    super();
  }

  /**
   * Creates a new MissingParameterException.
   * 
   * @param message The message that yielded this Exception.
   * @param throwable The wrapped throwable.
   */
  public MissingParameterException(String message, Throwable throwable) {
    super(message, throwable);
  }

  /**
   * Creates a new MissingParameterException.
   * 
   * @param message The message that yielded this Exception.
   */
  public MissingParameterException(String message) {
    super(message);
  }

  /**
   * Creates a new MissingParameterException.
   * 
   * @param throwable The wrapped throwable.
   */
  public MissingParameterException(Throwable throwable) {
    super(throwable);
  }
}
