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

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.enterprise.context.NonexistentConversationException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Method responsible for handling exceptions not caught by the application.
 * 
 * @author Markus Hamm
 */
public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private ExceptionHandler wrapped;

    /**
     * Constructs a new exception handler for the given wrapped handler.
     * 
     * @param wrapped
     *            the wrapped exception handler
     */
    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() {
        // For each causing exception
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            // Get causing exception
            Throwable exception = i.next().getContext().getException();
            i.remove();

            log.debug("Handling exception", exception);

            FacesContext fc = FacesContext.getCurrentInstance();

            if ((exception instanceof NonexistentConversationException) || (exception instanceof ViewExpiredException)) {
                // Redirect session/conversation-timeout error to the start-page

                try {
                    fc.getExternalContext().redirect(
                        fc.getExternalContext().getRequestContextPath() + "/index.jsf?sessionExpired=true");
                } catch (IOException e) {
                    log.error("Error redirecting to start page.");
                    throw new FacesException(e);
                }
            } else {
                // Redirect all other errors to the bug report-page

                // Set the request attributes
                HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();

                final Map<String, Object> sessionMap = fc.getExternalContext().getSessionMap();
                sessionMap.put(RequestDispatcher.ERROR_EXCEPTION, exception);
                sessionMap.put(RequestDispatcher.ERROR_EXCEPTION_TYPE, exception.getClass());
                sessionMap.put(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
                sessionMap.put(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
                sessionMap.put(RequestDispatcher.ERROR_STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                try {
                    fc.getExternalContext()
                        .redirect(fc.getExternalContext().getRequestContextPath() + "/bugreport.jsf");
                } catch (IOException e) {
                    log.error("Error redirecting to error page.");
                    throw new FacesException(e);
                }
            }

            // Remove remaining exceptions
            while (i.hasNext()) {
                i.next();
                i.remove();
            }

            wrapped.handle();
        }
    }
}
