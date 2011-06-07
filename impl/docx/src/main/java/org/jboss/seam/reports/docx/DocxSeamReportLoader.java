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
package org.jboss.seam.reports.docx;

import java.io.InputStream;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.LoadFromZipNG;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;

@Docx
public class DocxSeamReportLoader implements ReportLoader {

    @Override
    public DocxSeamReportDefinition loadReportDefinition(InputStream input) throws ReportException {
        LoadFromZipNG loader = new LoadFromZipNG();
        try {
            WordprocessingMLPackage mlPackage = (WordprocessingMLPackage) loader.get(input);
            return new DocxSeamReportDefinition(mlPackage);
        } catch (Docx4JException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public DocxSeamReportDefinition loadReportDefinition(String name) throws ReportException {
        LoadFromZipNG loader = new LoadFromZipNG();
        try {
            WordprocessingMLPackage mlPackage = (WordprocessingMLPackage) loader.get(name);
            return new DocxSeamReportDefinition(mlPackage);
        } catch (Docx4JException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public DocxSeamReport loadReport(InputStream input) throws ReportException {
        LoadFromZipNG loader = new LoadFromZipNG();
        try {
            WordprocessingMLPackage mlPackage = (WordprocessingMLPackage) loader.get(input);
            return new DocxSeamReport(null, mlPackage);
        } catch (Docx4JException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public DocxSeamReport loadReport(String name) throws ReportException {
        LoadFromZipNG loader = new LoadFromZipNG();
        try {
            WordprocessingMLPackage mlPackage = (WordprocessingMLPackage) loader.get(name);
            return new DocxSeamReport(null, mlPackage);
        } catch (Docx4JException e) {
            throw new ReportException(e);
        }
    }

}