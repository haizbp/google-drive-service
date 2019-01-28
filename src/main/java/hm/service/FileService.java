package hm.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Get;
import com.google.api.services.drive.model.File;

import hm.model.FileMeta;

@Service
public class FileService {

	@Autowired
	private Drive drive;

	public InputStream test() throws IOException {
		return new URL("http://localhost:8080/file/test").openStream();
	}

	public FileMeta download(String id) throws IOException {
		FileMeta meta = new FileMeta();

		Get get = drive.files().get(id);
		meta.setIn(get.executeAsInputStream());
		
		return meta;
	}

}
