package raisetech.StudentManagement.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.studentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  private StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //生徒情報のリスト表示
  public List<Student> searchStudentList(int minAge,int maxAge) {
    List<Student> studentList = repository.studentSearch();
    return studentList.stream()
        .filter(student -> student.getAge() >= minAge && student.getAge() <= maxAge)
        .collect(Collectors.toList());
  }

  //コース情報のリスト表示
  public List<studentsCourses> searchCourseList(String courseId) {
    List<studentsCourses> courseList = repository.courseSearch();
    return courseList.stream()
        .filter(studentsCourses -> studentsCourses.getCourseId().equals(courseId))
        .collect(Collectors.toList());
  }
}
