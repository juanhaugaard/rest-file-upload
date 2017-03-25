package com.example.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by juan.haugaard on 3/24/2017.
 */
public class DiagnosticContextFilter implements javax.ws.rs.container.ContainerRequestFilter {

  private Logger log = LoggerFactory.getLogger(DiagnosticContextFilter.class);

  /**
   * Filter incoming requests to obtain existing Diagnostic Context, or generate a new random ID if it is not already provided with the request.
   * The Diagnostic Context Identifier is expected to exist in a header named "X-Correlation-ID".
   *
   * @param requestContext structure containing all relevant information to the request being serviced
   */
  @Override
  public void filter(javax.ws.rs.container.ContainerRequestContext requestContext) throws IOException {
    String diagnosticContext = requestContext.getHeaderString(DiagnosticContext.httpCorrelationIdHeader);
    if ((diagnosticContext == null) || (diagnosticContext.length() < 1)) {
      diagnosticContext = java.util.UUID.randomUUID().toString();
      org.slf4j.MDC.put(DiagnosticContext.correlationIDProperty, diagnosticContext);
      log.trace("DiagnosticContextFilter generated new id '{}'",diagnosticContext);
    } else {
      org.slf4j.MDC.put(DiagnosticContext.correlationIDProperty, diagnosticContext);
      log.trace("DiagnosticContextFilter retrieved id '{}' from header",diagnosticContext);
    }
  }
}
