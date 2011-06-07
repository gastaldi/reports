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
package org.jboss.seam.reports.docx.test;


import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportDataSource;
import org.jboss.seam.reports.ReportDefinition;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.docx.Docx;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;
import de.oio.jpdfunit.document.util.TextSearchType;

@RunWith(Arquillian.class)
@SuppressWarnings("rawtypes")
public class DocxReportsTest {
    
    @Inject
    @Resource("testdoc.docx")
    InputStream sourceReport;
    
    @Inject
    @Docx
    ReportLoader loader;

    @Inject
    @Docx
    @PDF
    ReportRenderer<Report> pdfRenderer;

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true,"org.jboss.seam.solder")
                .addPackages(true,"org.jboss.seam.reports.annotation")
                .addPackages(true,"org.jboss.seam.reports.docx")
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Test
    public void testLoaderNotNull() throws Exception {
        assertNotNull(loader);
    }

    @Test
    public void testReportLifecycle() throws Exception {
        // source
        ReportDefinition report = loader.loadReportDefinition(sourceReport);

        Map<String, Object> params = new HashMap<String, Object>();
        // Preparing parameters
        params.put("name", "George Gastaldi");
        Report reportInstance = report.fill(getDataSource(), params);

        ByteArrayOutputStream os = new ByteArrayOutputStream(); // OutputStream
        // Render output as the desired content
        pdfRenderer.render(reportInstance, os);
        FileOutputStream fos = new FileOutputStream("D:\\cu.pdf");
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
        DocumentTester tester = new DocumentTester(new ByteArrayInputStream(os.toByteArray()));
        try {
            tester.assertPageCountEquals(1);
            tester.assertContentContainsText("Hello George Gastaldi", TextSearchType.CONTAINS);
        } finally {
            tester.close();
        }
    }

    private ReportDataSource getDataSource() throws Exception {
        return null;
    }
}