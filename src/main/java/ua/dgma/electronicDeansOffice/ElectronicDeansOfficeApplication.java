package ua.dgma.electronicDeansOffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.dgma.electronicDeansOffice.models.Student;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;

@SpringBootApplication
public class ElectronicDeansOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicDeansOfficeApplication.class, args);
	}

}
