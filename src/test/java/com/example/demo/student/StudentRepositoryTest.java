package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }
    @Test
    void itShouldCheckIfStudentExistsByEmail() {
        String email = "Jamila@gmail.com";
        Student student = new Student(
                "Jamila", email, Gender.FEMALE
        );

        studentRepository.save(student);
        boolean studentExists = studentRepository.selectExistsEmail(email);

        assertThat(studentExists).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesntExist() {
        String email = "Jamila@gmail.com";
        boolean studentExists = studentRepository.selectExistsEmail(email);

        assertThat(studentExists).isFalse();
    }
}
