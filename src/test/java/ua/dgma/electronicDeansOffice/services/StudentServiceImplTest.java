package ua.dgma.electronicDeansOffice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import ua.dgma.electronicDeansOffice.services.impl.StudentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestComponent
public class StudentServiceImplTest {

    private StudentServiceImpl service;

    @BeforeEach
    void prepare() {
    }

    @Test
    void test(){
        assertTrue(false);
    }

    @Test
    void shouldFindStudent(){
//        Mockito.doReturn(true).when(service).findOne(12729303l);
        var findStudent = service.findOne(12723909L);


    }
}
