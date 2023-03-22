package ua.dgma.electronicDeansOffice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import static org.junit.jupiter.api.Assertions.*;

@TestComponent
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService service;


    @BeforeEach
    void prepare() {
    }

    @Test
    void test(){
        assertFalse(false);
    }

    @Test
    void shouldFindPerson(){
//        Mockito.doReturn(true).when(service).findOne(12729303l);
        var findPerson = service.findByUid(12723909L);
        assertEquals("Mykyta",findPerson.getName());
    }
}
