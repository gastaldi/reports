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
package org.jboss.seam.reports.jasperreports;

import net.sf.jasperreports.engine.JasperPrint;

import org.jboss.seam.reports.Report;

public class JasperSeamReport implements Report {

    private static final long serialVersionUID = 1L;

    private JasperPrint jasperPrint;
    private JasperSeamReportDefinition reportDefinition;
    
    public JasperSeamReport(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JasperSeamReport(JasperPrint jasperPrint, JasperSeamReportDefinition reportDefinition) {
        this.jasperPrint = jasperPrint;
        this.reportDefinition = reportDefinition;
    }

    @Override
    public JasperSeamReportDefinition getReportDefinition() {
        return reportDefinition;
    }
    
    @Override
    public JasperPrint getDelegate() {
        return jasperPrint;
    }
}