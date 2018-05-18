package com.example.sleuthdemo;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SleuthService {

	@Autowired
	private Tracer tracer;

	public void doSomeWorkSameSpan() throws InterruptedException{
		Thread.sleep(1000L);
		log.info("Doing some work");
	}

	public void doSomeWorkNewSpan() throws InterruptedException {
		log.info("I'm in the original span");

		Span newSpan = tracer.newTrace().name("newSpan").start();
		try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
			Thread.sleep(1000L);
			log.info("I'm in the new span doing some cool work that needs its own span");
		} finally {
			newSpan.finish();
		}

		log.info("I'm in the original span");
	}

	@Async
	public void asyncMethod() throws InterruptedException {
		log.info("Start Async Method");
		log.info("traceId: {}", Tracing.current().currentTraceContext().get().traceIdString());
		Thread.sleep(1000L);
		log.info("End Async Method");
	}

}
