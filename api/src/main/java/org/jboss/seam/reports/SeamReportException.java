package org.jboss.seam.reports;

import java.io.IOException;

public class SeamReportException extends IOException {

    private static final long serialVersionUID = 1L;

    public SeamReportException() {
        super();
    }

    public SeamReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeamReportException(String message) {
        super(message);
    }

    public SeamReportException(Throwable cause) {
        super(cause);
    }
    
    
}
