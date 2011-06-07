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

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.spi.ReportOutputBinding;

@Docx
public class DocxSeamReportRenderer implements ReportRenderer<DocxSeamReport> {

    @Inject
    private InjectionPoint ip;

    @Inject
    private BeanManager bm;

    @Override
    public void render(DocxSeamReport report, OutputStream output) throws IOException {
        ReportOutputBinding an = getAnnotation(ip.getAnnotated(), ReportOutputBinding.class, bm);
        String outputType = an.value();
        if ("PDF".equals(outputType)) {
            PdfConversion conversion = new Conversion(report.getDelegate());
            try {
                conversion.output(output);
            } catch (Docx4JException e) {
                throw new ReportException(e);
            }
        } else {
            throw new IllegalStateException("Report type not supported " + outputType);
        }
    }

}
