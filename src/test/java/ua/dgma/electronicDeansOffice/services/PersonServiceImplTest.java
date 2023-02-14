package ua.dgma.electronicDeansOffice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import ua.dgma.electronicDeansOffice.services.impl.PersonServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@TestComponent
@SpringBootTest
public class PersonServiceImplTest {

    @Autowired
    private PersonServiceImpl service;


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
        var findPerson = service.findOneByUid(12723909L);
        assertEquals("Mykyta",findPerson.getName());
    }
}
