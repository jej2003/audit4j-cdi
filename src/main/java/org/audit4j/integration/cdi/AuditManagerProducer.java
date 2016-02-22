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

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.CoreConstants;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.MetaData;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.util.ReflectUtil;

/**
 * The Class AuditManagerProducer.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * @author <a href="mailto:jej2003@gmail.com">Jamie Johnson</a>
 */
public class AuditManagerProducer {

    /** The handlers. */
    @Inject
    @InjectedProperty(key = "audit.handlers", mandatory = true)
    String handlers;

    /** The layout. */
    @Inject
    @InjectedProperty(key = "audit.layout", mandatory = true)
    String layout;

    /** The filters. */
    @Inject
    @InjectedProperty(key = "audit.filters", mandatory = false)
    String filters;

    /** The options. */
    @Inject
    @InjectedProperty(key = "audit.options", mandatory = false)
    String options;

    /** The meta data. */
    @Inject
    @InjectedProperty(key = "audit.metaData", mandatory = false)
    String metaData;

    /** The properties. */
    @Inject
    @InjectedProperty(key = "audit.properties", mandatory = false)
    String properties;

    /**
     * Initialize audit4j.
     */
    @Produces
    public IAuditManager auditManager() {
        Configuration config = Configuration.INSTANCE;
        config.setHandlers(new ReflectUtil<Handler>().getNewInstanceList(handlers.split(CoreConstants.SEMI_COLON)));
        config.setLayout(new ReflectUtil<Layout>().getNewInstance(layout));
        if(filters.length() > 0) {
            config.setFilters(new ReflectUtil<AuditEventFilter>().getNewInstanceList(filters
                    .split(CoreConstants.SEMI_COLON)));
        }
        if(options.length() > 0) {
            config.setCommands(options);
        }
        if(metaData.length() > 0) {
            config.setMetaData(new ReflectUtil<MetaData>().getNewInstance(metaData));
        }
        if(properties.length() > 0) {
            String[] propertiesList = properties.split(CoreConstants.SEMI_COLON);
            for (String property : propertiesList) {
                String[] keyValue = property.split(CoreConstants.COLON);
                config.addProperty(keyValue[0], keyValue[1]);
            }
        }
        AuditManager.startWithConfiguration(config);
        return AuditManager.getInstance();
    }

    /**
     * Stop.
     */
    public void stop(@Disposes IAuditManager manager) {
        AuditManager.shutdown();
    }
}
