import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import org.apache.camel.CamelContext;
import org.apache.camel.opentracing.OpenTracingTracer;

public class Tracer {

	public static OpenTracingTracer initializeTracer(CamelContext context, String name, String endpoint) {
		OpenTracingTracer openTracingTracer = new OpenTracingTracer();
		openTracingTracer.setTracer(
			new Configuration(name).withReporter(
					new ReporterConfiguration().withSender(
						new SenderConfiguration().withEndpoint(endpoint)
					)
				)
				.withSampler(
					new SamplerConfiguration().withType("const").withParam(1)
				)
				.getTracer()
		);
		openTracingTracer.init(context);
		return openTracingTracer;
	}
}
