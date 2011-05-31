/*
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

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;

/**
 * BIRT Report Loader
 * 
 * @author George Gastaldi
 */
@Birt
public class BirtSeamReportLoader implements ReportLoader {

    @Inject @Birt
    private IReportEngine reportEngine;

    @Override
    public BirtSeamReportDefinition loadReportDefinition(InputStream input) throws ReportException {
        try {
            IReportRunnable reportDesign = reportEngine.openReportDesign(input);
            return new BirtSeamReportDefinition(reportEngine,reportDesign);
        } catch (EngineException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public BirtSeamReportDefinition loadReportDefinition(String name) throws ReportException {
        try {
            IReportRunnable reportDesign = reportEngine.openReportDesign(name);
            return new BirtSeamReportDefinition(reportEngine,reportDesign);
        } catch (EngineException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public BirtSeamReport loadReport(InputStream input) throws ReportException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BirtSeamReport loadReport(String name) throws ReportException {
        try {
            IReportDocument reportDocument = reportEngine.openReportDocument(name);
            return new BirtSeamReport(reportDocument);
        } catch (EngineException e) {
            throw new ReportException(e);
        }
    }
}