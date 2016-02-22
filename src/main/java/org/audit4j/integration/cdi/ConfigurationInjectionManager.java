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

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * The Class ConfigurationInjectionManager.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ConfigurationInjectionManager {
    
    /** The Constant MANDATORY_PARAM_MISSING. */
    static final String MANDATORY_PARAM_MISSING = "No definition found for a mandatory configuration parameter : '{0}'";
    
    /** The bundle file name. */
    private final String BUNDLE_FILE_NAME = "audit4j";
    
    /** The bundle. */
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_FILE_NAME);
    
    /**
     * Inject configuration.
     *
     * @param ip the ip
     * @return the string
     * @throws IllegalStateException the illegal state exception
     */
    @Produces
    @InjectedProperty
    public String injectConfiguration(InjectionPoint ip) throws IllegalStateException {
        InjectedProperty param = ip.getAnnotated().getAnnotation(InjectedProperty.class);
        if(!bundle.containsKey(param.key()) && param.mandatory()) {
            throw new IllegalStateException(MessageFormat.format(MANDATORY_PARAM_MISSING, new Object[]{param.key()}));
        } else if(!bundle.containsKey(param.key())) {
            return param.defaultValue();
        } else {
            return bundle.getString(param.key());
        }
    }
}
