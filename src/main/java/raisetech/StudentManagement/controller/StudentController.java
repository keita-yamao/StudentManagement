package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.conberter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  private StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //生徒情報のリスト表示
  @GetMapping("/studentList")
  public String getStudentList(Model model,@RequestParam(defaultValue = "0") int minAge,
      @RequestParam(defaultValue = "130") int maxAge, @RequestParam(defaultValue = "00001") String courseId) {
    List<Student> students = service.searchStudentList(minAge, maxAge);
    List<StudentsCourses> studentsCourses = service.searchCourseList(courseId);

    model.addAttribute("studentList",converter.studentDetails(students, studentsCourses));
    return "studentList";
  }

  //コース情報のリスト表示
  @GetMapping("/courseList")
  public List<StudentsCourses> getCourseList(@RequestParam String courseId) {
    return service.searchCourseList(courseId);
  }
}
