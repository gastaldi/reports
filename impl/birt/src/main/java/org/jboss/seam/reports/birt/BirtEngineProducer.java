/**
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.reports.birt;

import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

/**
 * This is needed by the app developer
 * 
 * @author george
 * 
 */
public class BirtEngineProducer {

    @Produces @Birt
    @ApplicationScoped
    public IReportEngine produceEngine(BirtRuntimeConfig runtimeConfig) throws BirtException {
        EngineConfig config = new EngineConfig();
        config.setBIRTHome(runtimeConfig.getBirtHome());
        config.setEngineHome(runtimeConfig.getEngineHome());
        //config.setLogConfig("D:/temp/birt-runtime-2_6_2/log", Level.FINEST);
        try {
            Platform.startup(config); // If using RE API in Eclipse/RCP application this is not needed.
        } catch (BirtException e) {
            e.printStackTrace();
            throw e;
        }
        IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine engine = factory.createReportEngine(config);
        engine.changeLogLevel(Level.WARNING);
        return engine;
    }

    public void destroyEngine(@Disposes @Birt IReportEngine engine) {
        engine.destroy();
        Platform.shutdown();
    }
}
