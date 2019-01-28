package hm.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.model.FileMeta;
import hm.service.FileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value = "/test")
	public ResponseEntity<Resource> test() {
		Resource resource = new ClassPathResource("file_example_MP4_1920_18MG.mp4");
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("video/mp4")).body(resource);
	}

	@GetMapping(value = "/playback")
	public Mono<Void> urlStream(ServerHttpResponse response) throws IOException {
		HttpHeaders header = response.getHeaders();

		response.setStatusCode(HttpStatus.OK);
		header.add("content-type","video/mp4");
		header.add("Accept-Ranges", "bytes");
		header.add("Content-Disposition", "inline; filename=playback.mp4;");
		InputStream is = fileService.test();
		
		DataBuffer buffer = response.bufferFactory().allocateBuffer();
		
		IOUtils.copy(is, buffer.asOutputStream());
		
		header.setContentLength(buffer.writePosition());
		
		return response.writeWith(Flux.just(buffer));
	}

	@GetMapping(value = "/{id}")
	public Mono<Void> get(@PathVariable("id") String id, ServerHttpResponse response) throws IOException {
		HttpHeaders header = response.getHeaders();

		response.setStatusCode(HttpStatus.OK);
		header.add("content-type","video/mp4");
		header.add("Accept-Ranges", "bytes");
		header.add("Content-Disposition", "inline; filename=playback.mp4;");
		
		FileMeta meta = fileService.download(id);
		InputStream is = meta.getIn();
		
		DataBuffer buffer = response.bufferFactory().allocateBuffer();
		
		IOUtils.copy(is, buffer.asOutputStream());
		
		header.setContentLength(buffer.writePosition());
		
		return response.writeWith(Flux.just(buffer));
	}

	

}
