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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.birt.core.archive.FileArchiveWriter;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunTask;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportException;

/**
 * BIRT report designs are stored as XML files. By default the extension is rptdesign.
 * 
 * This class represents a {@link IReportRunnable} instance that represents the engine's view of the report design.
 * 
 * @author George Gastaldi
 */
public class BirtSeamReportDefinition implements ReportDefinition<BirtSeamReportDataSource, BirtSeamReport> {

    private IReportRunnable reportDesign;
    private IReportEngine reportEngine;

    public BirtSeamReportDefinition(IReportEngine reportEngine, IReportRunnable reportDesign) {
        super();
        this.reportEngine = reportEngine;
        this.reportDesign = reportDesign;
    }

    @Override
    public BirtSeamReport fill(BirtSeamReportDataSource dataSource, Map<String, Object> parameters) throws ReportException {
        IRunTask runTask = reportEngine.createRunTask(reportDesign);
        runTask.setParameterValues(parameters);
        runTask.setAppContext(dataSource.getDelegate());
        FileArchiveWriter writer = null;
        String file = (parameters != null) ? (String) parameters.get("OUTPUT") : null;
        try {
            if (file == null) {
                File tempFile = File.createTempFile("seambirt", ".rptdocument");
                tempFile.deleteOnExit();
                file = tempFile.getAbsolutePath();
            }
            writer = new FileArchiveWriter(file);
            runTask.run(writer);
            IReportDocument reportDoc = reportEngine.openReportDocument(file);
            return new BirtSeamReport(reportDoc);
        } catch (IOException ioe) {
            throw new ReportException(ioe);
        } catch (EngineException e) {
            throw new ReportException(e);
        }
    }
}
