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

import org.audit4j.core.IAuditManager;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AuditInterceptorTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    InvocationContext methodInvocation;
    @Mock
    IAuditManager auditManager;

    AuditInterceptor interceptor;

    @Before
    public void init(){
        interceptor = new AuditInterceptor(auditManager);
    }

    @org.junit.Test
    public void testInterceptorCreate() {
        Method method;
        try {
            method = Test.class.getDeclaredMethod("testmethod", String.class);
        } catch (NoSuchMethodException e){
            Assert.fail(e.getMessage());
            return;
        }

        Mockito.when(methodInvocation.getMethod()).thenReturn(method);

        Mockito.when(methodInvocation.getTarget()).thenReturn(new Test());
        Object[] arguments = new Object[]{"Value"};
        Mockito.when(methodInvocation.getParameters()).thenReturn(arguments);

        try {
            interceptor.before(methodInvocation);
        } catch (Throwable e){
            Assert.fail(e.getMessage());
            return;
        }

        ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<Method> methodCaptor = ArgumentCaptor.forClass(Method.class);
        ArgumentCaptor<Object[]> parameterCaptor = ArgumentCaptor.forClass(Object[].class);

        Mockito.verify(auditManager, Mockito.times(1)).audit(classCaptor.capture(), methodCaptor.capture(), parameterCaptor.capture());

        Assert.assertThat(Test.class, Matchers.equalTo(classCaptor.getValue()));
        Assert.assertThat(method, Matchers.equalTo(methodCaptor.getValue()));
        Assert.assertTrue(Arrays.equals(arguments, parameterCaptor.getValue()));

    }

    private static class Test {
        public void testmethod(String value){};
    }
}
