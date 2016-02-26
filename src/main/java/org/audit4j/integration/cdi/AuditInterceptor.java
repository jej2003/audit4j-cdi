/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.integration.cdi;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.audit4j.core.IAuditManager;
import org.audit4j.core.annotation.Audit;

/**
 * The Audit Interceptor for CDI spec implementations.
 * 
 * For Jboss Weld:
 * 
 * <pre>
 * {@code
 * <beans
 *    ...
 *    <interceptors>
 *       <class>org.audit4j.intregration.cdi.AuditInterceptor</class>
 *    </interceptors>
 * </beans>
 * }
 * </pre>
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @author <a href="mailto:jej2003@gmail.com">Jamie Johnson</a>
 * 
 * @since core 2.3.1
 * @since audit4j-cdi 1.0.0
 */
@Audit
@Interceptor
public class AuditInterceptor {

    protected IAuditManager auditManager;

    @Inject
    public AuditInterceptor(IAuditManager auditManager) {
        this.auditManager = auditManager;
    }

    /**
     * Before method invocation.
     * 
     * @param joinPoint
     *            the join point
     * @return the object
     * @throws Throwable
     *             the throwable
     */
    @AroundInvoke
    public Object before(InvocationContext joinPoint) throws Exception {
        auditManager.audit(joinPoint.getTarget().getClass(), joinPoint.getMethod(),
                joinPoint.getParameters());
        return joinPoint.proceed();
    }
}
