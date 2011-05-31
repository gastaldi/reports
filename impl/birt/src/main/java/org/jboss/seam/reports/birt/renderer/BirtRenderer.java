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
package org.jboss.seam.reports.birt.renderer;

import static org.jboss.seam.solder.reflection.AnnotationInspector.getAnnotation;

import java.io.OutputStream;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.eclipse.birt.report.engine.api.IRenderTask;
import org.eclipse.birt.report.engine.api.IReportDocument;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.jboss.seam.reports.ReportException;
import org.jboss.seam.reports.ReportRenderer;
import org.jboss.seam.reports.birt.Birt;
import org.jboss.seam.reports.birt.BirtSeamReport;
import org.jboss.seam.reports.spi.ReportOutputBinding;

/**
 * BIRT Report Renderer
 * 
 * @author George Gastaldi
 */
@Birt
public class BirtRenderer implements ReportRenderer<BirtSeamReport> {
    @Inject
    private InjectionPoint ip;

    @Inject
    private BeanManager bm;

    @Inject @Birt
    private IReportEngine engine;

    @Override
    public void render(BirtSeamReport report, OutputStream output) throws ReportException {
        ReportOutputBinding an = getAnnotation(ip.getAnnotated(), ReportOutputBinding.class, bm);
        IReportDocument doc = report.getDelegate();
        IRenderTask task = engine.createRenderTask(doc);
        if ("PDF".equals(an.value())) {
//            Map contextMap = null;
//            PDFRenderOption options = null;
//
//            // Set Render context to handle url and image locataions, and apply to the
//            // task
//            renderContext = new HTMLRenderContext();
//            renderContext.setImageDirectory("image");
//            contextMap = new HashMap();
//            contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);
//            task.setAppContext(contextMap);
//
//            // This will set the output file location, the format to rener to, and
//            // apply to the task
//            options = new HTMLRenderOption();
//            options.setOutputFileName("c:/temp/output.html");
//            options.setOutputFormat("html");
//            task.setRenderOption(options);
//
//            // Cross our fingers and hope everything is set
//            try {
//                task.run();
//            } catch (Exception e) {
//            }

        }

    }
}
