package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
  public List<Student> getStudentList(@RequestParam(defaultValue = "0") int minAge,@RequestParam(defaultValue = "130") int maxAge) {
    return service.searchStudentList(minAge, maxAge);
  }

  //コース情報のリスト表示
  @GetMapping("/courseList/{courseId}")
  public List<studentsCourses> getCourseList(@PathVariable String courseId) {
    return service.searchCourseList(courseId);
  }
}
