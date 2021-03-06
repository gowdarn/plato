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
package eu.scape_project.pw.idp.application;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans.
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {
    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext
    private EntityManager em;

    /**
     * Creates a logger for the provided injectionPoint.
     * 
     * @param injectionPoint
     *            the class where this logger will be used
     * @return the logger
     */
    @Produces
    public Logger createLogger(InjectionPoint injectionPoint) {
        return org.slf4j.LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
