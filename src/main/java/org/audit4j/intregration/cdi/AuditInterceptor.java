package org.audit4j.intregration.cdi;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.audit4j.core.AuditManager;
import org.audit4j.core.annotation.Audit;

/**
 * The Class Interceptor.
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
 */
@Audit
@Interceptor
public class AuditInterceptor {

    /**
     * Before.
     * 
     * @param joinPoint
     *            the join point
     * @return the object
     * @throws Throwable
     *             the throwable
     */
    @AroundInvoke
    public Object before(InvocationContext joinPoint) throws Throwable {
        AuditManager.getInstance().audit(joinPoint.getTarget().getClass(), joinPoint.getMethod(),
                joinPoint.getParameters());
        return joinPoint.proceed();
    }
}
