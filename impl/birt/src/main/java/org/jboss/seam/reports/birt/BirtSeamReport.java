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

import org.eclipse.birt.report.engine.api.IReportDocument;
import org.jboss.seam.reports.Report;

/**
 * BIRT Report (a .rptdocument)
 * 
 * @author George Gastaldi
 */
public class BirtSeamReport implements Report {

    private static final long serialVersionUID = 1L;

    private IReportDocument reportDocument;
    private BirtSeamReportDefinition origin;

    public BirtSeamReport(IReportDocument reportDocument) {
        super();
        this.reportDocument = reportDocument;
    }

    public BirtSeamReport(BirtSeamReportDefinition origin, IReportDocument reportDocument) {
        super();
        this.origin = origin;
        this.reportDocument = reportDocument;
    }

    @Override
    public BirtSeamReportDefinition getReportDefinition() {
        return origin;
    }

    @Override
    public IReportDocument getDelegate() {
        return reportDocument;
    }

}
