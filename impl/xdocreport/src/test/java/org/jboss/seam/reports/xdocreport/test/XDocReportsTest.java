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
package org.jboss.seam.reports.xdocreport.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import org.apache.tika.Tika;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.reports.Report;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.xdocreport.XDocReport;
import org.jboss.seam.reports.xdocreport.XDocReportSeamReport;
import org.jboss.seam.reports.xdocreport.XDocReportSeamReportLoader;
import org.jboss.seam.solder.resourceLoader.Resource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.opensagres.xdocreport.template.IContext;

@RunWith(Arquillian.class)
public class XDocReportsTest
{
   @Inject
   @Resource("DocxProjectWithVelocity.docx")
   InputStream sourceReport;

   @Inject
   @XDocReport
   ReportRenderer<Report> reportRenderer;

   @Inject
   @XDocReport
   XDocReportSeamReportLoader reportLoader;

   @Deployment
   public static JavaArchive createArchive()
   {
      return ShrinkWrap.create(JavaArchive.class).addPackages(true, "org.jboss.seam.solder")
               .addPackages(true, "org.jboss.seam.reports.annotation")
               .addPackages(true, "org.jboss.seam.reports.xdocreport")
               .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
   }

   @Test
   public void testDocx() throws Exception
   {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      XDocReportSeamReport reportDefinition = reportLoader.loadReportDefinition(sourceReport);
      IContext dataSource = reportDefinition.getDelegate().createContext();
      dataSource.put("project", "Seam Reports");
      Report report = reportDefinition.fill(dataSource, null);
      reportRenderer.render(report, output);
      // Extracting the result
      String text = new Tika().parseToString(new ByteArrayInputStream(output.toByteArray()));
      assertTrue(text.contains("Seam Reports Rocks"));
   }
   

   @Test
   public void testDocxWithMap() throws Exception
   {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      XDocReportSeamReport reportDefinition = reportLoader.loadReportDefinition(sourceReport);
      Map<String, String> dataSource = Collections.singletonMap("project", "Seam Reports");
      Report report = reportDefinition.fill(dataSource, null);
      reportRenderer.render(report, output);
      // Extracting the result
      String text = new Tika().parseToString(new ByteArrayInputStream(output.toByteArray()));
      assertTrue(text.contains("Seam Reports Rocks"));
   }

   @Test
   public void testDocxWithMapAsParameter() throws Exception
   {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      XDocReportSeamReport reportDefinition = reportLoader.loadReportDefinition(sourceReport);
      Map<String, Object> parameters = Collections.singletonMap("project", (Object)"Seam Reports");
      Report report = reportDefinition.fill(null, parameters);
      reportRenderer.render(report, output);
      // Extracting the result
      String text = new Tika().parseToString(new ByteArrayInputStream(output.toByteArray()));
      assertTrue(text.contains("Seam Reports Rocks"));
   }
}