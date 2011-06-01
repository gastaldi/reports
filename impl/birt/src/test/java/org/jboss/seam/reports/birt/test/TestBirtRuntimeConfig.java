package org.jboss.seam.reports.birt.test;

import org.jboss.seam.reports.birt.BirtRuntimeConfig;

public class TestBirtRuntimeConfig implements BirtRuntimeConfig {
    @Override
    public String getBirtHome() {
        return "D:/temp/birt-runtime-2_6_2";
    }

    @Override
    public String getEngineHome() {
        return "D:/temp/birt-runtime-2_6_2/ReportEngine";
    }
}
