package ua.dgma.electronicDeansOffice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.impl.FacultyServiceImpl;
import ua.dgma.electronicDeansOffice.utill.validators.FacultyValidator;

import java.util.Set;

@SpringBootApplication
public class ElectronicDeansOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicDeansOfficeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
