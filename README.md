# audit4j-cdi
Audit4j for CDI spec implementations

requires an audit4j resource bundle (not sure this is appropriate, as these are typically used for UIs) with the minimum settings

audit.handlers org.audit4j.core.handler.ConsoleAuditHandler
audit.layout org.audit4j.core.layout.SimpleLayout

available options are

audit.handlers
audit.layout
audit.filters
audit.options
audit.metaData
audit.properties
