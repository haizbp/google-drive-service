package hm.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.model.FileMeta;
import hm.service.FileService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@GetMapping(value = "/test")
	public ResponseEntity<Resource> test() {
		Resource resource = new ClassPathResource("test.mp4");
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("video/mp4")).body(resource);
	}

	@GetMapping(value = "/playback")
	public void urlStream(HttpServletResponse response, ServletServerHttpResponse response2) throws IOException {

		InputStream is = fileService.test();
		transfer(is, 17839845, response);
	}

	@GetMapping(value = "/{id}")
	public void get(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		FileMeta meta = fileService.download(id);
		InputStream is = meta.getIn();
		transfer(is, meta.getLength(),response);
	}

	private void transfer(InputStream is, int length, HttpServletResponse response) throws IOException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("video/mp4");
		response.setContentLength(length);
		response.addHeader("Accept-Ranges", "bytes");
		response.addHeader("Content-Disposition", "inline; filename=playback.mp4;");

		IOUtils.copy(is, response.getOutputStream());
	}

}
