package com.example.sleuthdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SleuthController {

	private final SleuthService sleuthService;

	@GetMapping("/")
	public String helloSleuth() {
		log.info("Hello Sleuth");
		return "success";
	}

	@GetMapping("/same-span")
	public String helloSleuthSameSpan() throws InterruptedException {
		log.info("Same Span");
		sleuthService.doSomeWorkSameSpan();
		return "success";
	}

	@GetMapping("/new-span")
	public String helloSleuthNewSpan() throws InterruptedException {
		log.info("New Span");
		sleuthService.doSomeWorkNewSpan();
		return "success";
	}

	@GetMapping("/async")
	public String helloSleuthAsync() throws InterruptedException {
		log.info("Before Async Method Call");
		sleuthService.asyncMethod();
		log.info("After Async Method Call");

		return "success";
	}
}
