/*******************************************************************************
 * Copyright 2006 - 2012 Vienna University of Technology,
 * Department of Software Technology and Interactive Systems, IFS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.scape_project.planning.services.taverna.executor;

public class TavernaExecutorException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TavernaExecutorException() {
        super();
    }

    public TavernaExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TavernaExecutorException(String message) {
        super(message);
    }

    public TavernaExecutorException(Throwable cause) {
        super(cause);
    }

}
