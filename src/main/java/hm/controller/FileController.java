package hm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.service.FileService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Resource> get(@PathVariable("id") String id) throws IOException {
		Resource resource = new InputStreamResource(fileService.download(id));

//		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//		.contentType(MediaType.parseMediaType("video/mp4")).contentLength(100)
//		.body(resource);
//		
		return ResponseEntity
	            .status(HttpStatus.PARTIAL_CONTENT)
	            .eTag("123121")
	            .contentType(MediaType.parseMediaType("video/mp4"))
	            .contentLength(1000000)
	            .body(resource);
	}

}
