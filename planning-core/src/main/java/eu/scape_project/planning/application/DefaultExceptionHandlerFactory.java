/*******************************************************************************
 * Copyright 2006 - 2012 Vienna University of Technology,
 * Department of Software Technology and Interactive Systems, IFS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.scape_project.planning.application;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Factory for ExceptionHandler specified in faces-config.
 */
public class DefaultExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    /**
     * Constructs an exception handler factory for the provided parent.
     * 
     * @param parent
     *            the parent
     */
    public DefaultExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler eh = new DefaultExceptionHandler(parent.getExceptionHandler());
        return eh;
    }
}
