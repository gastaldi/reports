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
package org.jboss.seam.reports.birt.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportLoader;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.birt.Birt;
import org.jboss.seam.reports.birt.BirtExtension;
import org.jboss.seam.reports.output.PDF;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.oio.jpdfunit.DocumentTester;

/**
 * Test BIRT functionality
 * 
 * @author George Gastaldi
 */
@RunWith(Arquillian.class)
public class BirtRenderTest {

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class).addPackages(true, "org.jboss.seam.solder")
                .addPackages(true, "org.jboss.seam.reports.annotation").addPackages(true, "org.jboss.seam.reports.birt")
                .addAsServiceProvider(Extension.class, BirtExtension.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Inject
    @Resource("customers.rptdesign")
    private URL sourceReport;

    @Inject
    @Birt
    private ReportLoader loader;

    @Inject @Birt @PDF
    private ReportRenderer<Report> pdfRenderer;

    @Test
    public void renderReport() throws ReportException, IOException {
        Report report = loader.loadReport(sourceReport.getFile());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfRenderer.render(report, baos);
        assertTrue("Report is empty", baos.size() > 0);
        DocumentTester tester = new DocumentTester(new ByteArrayInputStream(baos.toByteArray()));
        try {
            tester.assertPageCountEquals(1);
        } finally {
            tester.close();
        }
    }
}
