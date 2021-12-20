package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;


    private StudentService studentService;

    Student student = new Student(
            "Jamila", "email", Gender.FEMALE
    );
    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void getAllStudents() {
        studentService.getAllStudents();
        //verify from mockito
        verify(studentRepository).findAll();
    }

    @Test
    void addStudentSuccess() {
        studentService.addStudent(student);
        //verify from mockito
        verify(studentRepository).selectExistsEmail("email");
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void addStudentFailsWhenEmailIsTaken() {
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining( "Email " + student.getEmail() + " taken");
        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudentSuccess() {
        given(studentRepository.existsById(student.getId())).willReturn(true);
        studentService.deleteStudent(student.getId());
        verify(studentRepository).deleteById(student.getId());
    }
    @Test
    void deleteStudentFailsIfIdDoesntExist() {
        given(studentRepository.existsById(student.getId())).willReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudent(student.getId()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists");
        verify(studentRepository, never()).deleteById(student.getId());
    }
}
