package hm;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.services.drive.Drive;

import hm.google.drive.DriveQuickstart;

@Configuration
public class AppConfiguration {

	@Bean
	public Drive drive() {
		try {
			return new Drive.Builder(DriveQuickstart.getTransport(), DriveQuickstart.getJsonFactory(),
					DriveQuickstart.getCredentials(DriveQuickstart.getTransport()))
							.setApplicationName(DriveQuickstart.APPLICATION_NAME).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
