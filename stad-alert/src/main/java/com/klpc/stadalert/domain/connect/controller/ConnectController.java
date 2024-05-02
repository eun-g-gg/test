package com.klpc.stadalert.domain.connect.controller;

import com.klpc.stadalert.domain.connect.controller.request.ConnectRequest;
import com.klpc.stadalert.domain.connect.controller.request.QrLoginRequest;
import com.klpc.stadalert.domain.connect.service.ConnectService;
import com.klpc.stadalert.global.service.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/connect")
public class ConnectController {

	final SseEmitters sseEmitters;
	final ConnectService connectService;

	@PostMapping()
	public ResponseEntity<SseEmitter> connect(@RequestBody ConnectRequest request) {
		log.info("CreateConnectRequest: "+ request);
		SseEmitter emitter = sseEmitters.subscribe(request.toCommand());
		return ResponseEntity.ok(emitter);
	}

	@PostMapping("/qrlogin")
	public ResponseEntity<?> qrLogin(@RequestBody QrLoginRequest request) {
		log.info("CreateConnectRequest: "+ request);
		connectService.qrLogin(request.toCommand());
		return ResponseEntity.ok().build();
	}
}
