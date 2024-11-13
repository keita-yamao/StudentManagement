package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.studentsCourses;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  private StudentController(StudentService service) {
    this.service = service;
  }

  //生徒情報のリスト表示
  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  //コース情報のリスト表示
  @GetMapping("/courseList")
  public List<studentsCourses> getCourseList() {
    return service.searchCourseList();
  }

}
