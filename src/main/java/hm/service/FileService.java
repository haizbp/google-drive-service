package hm.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.google.api.services.drive.Drive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FileService {

	@Autowired
	private Drive drive;

	public InputStream download(String id) throws IOException {
		InputStream is = drive.files().get(id).executeMediaAsInputStream();
		return is;
	}

}
