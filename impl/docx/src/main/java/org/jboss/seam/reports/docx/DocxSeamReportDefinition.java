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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportException;

public class DocxSeamReportDefinition implements ReportDefinition<DocxSeamReportDataSource, DocxSeamReport> {

    private WordprocessingMLPackage mlPackage;

    public DocxSeamReportDefinition(WordprocessingMLPackage mlPackage) {
        this.mlPackage = mlPackage;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public DocxSeamReport fill(DocxSeamReportDataSource dataSource, Map<String, Object> parameters) throws ReportException {
        WordprocessingMLPackage newPackage = clonePackage();
        MainDocumentPart documentPart = newPackage.getMainDocumentPart();
        Document wmlDocumentEl = documentPart.getJaxbElement();
        String xml = XmlUtils.marshaltoString(wmlDocumentEl, true);
        //valorize template
        HashMap newParams = null;
        try {
            if (parameters instanceof HashMap) {
                newParams = (HashMap)parameters;
            } else {
                newParams = new HashMap();
                newParams.putAll(parameters);
            }
            //Stupid signature. Why not java.util.Map ?
            Object obj = XmlUtils.unmarshallFromTemplate(xml, newParams);
            documentPart.setJaxbElement((org.docx4j.wml.Document) obj);
        } catch (JAXBException e) {
            throw new ReportException(e);
        }
        return new DocxSeamReport(this, newPackage);
    }

    WordprocessingMLPackage clonePackage() throws ReportException {
        File f = null;
        try {
            f = File.createTempFile("tmpdocx4j", ".docx");
            mlPackage.save(f);
            WordprocessingMLPackage newPackage = WordprocessingMLPackage.load(f);
            return newPackage;
        } catch (Exception e) {
            throw new ReportException(e);
        } finally {
            if (f != null)
                f.delete();
        }
    }
}
