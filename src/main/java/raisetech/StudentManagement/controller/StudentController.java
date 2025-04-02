package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
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
  public String getStudentList(Model model, @RequestParam(defaultValue = "0") int minAge,
      @RequestParam(defaultValue = "130") int maxAge,
      @RequestParam(defaultValue = "") String courseId) {
    List<Student> students = service.searchStudentList(minAge, maxAge);
    List<Course> courses = service.searchCourseList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList(courseId);
    List<CourseDetail> courseDetails = converter.courseDetails(studentsCourses, courses);
    model.addAttribute("studentList",
        converter.studentDetails(students, courseDetails));
    return "studentList";
  }

  //コース情報のリスト表示
  @GetMapping("/courseList")
  public List<StudentsCourses> getCourseList(@RequestParam String courseId) {
    return service.searchStudentCourseList(courseId);
  }

  //生徒情報の登録処理画面への遷移
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  //生徒情報の変更処理画面への遷移
  @GetMapping("/editStudent")
  public String editStudent(Model model, @RequestParam String studentId) {
    Student student = service.searchStudent(studentId);
    List<StudentsCourses> studentsCourses = service.studentsCourses(studentId);
    List<Course> courses = service.searchCourseList();
    List<CourseDetail> courseDetails = converter.courseDetails(studentsCourses, courses);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setCourseDetail(courseDetails);
    model.addAttribute("studentDetail", studentDetail);
    model.addAttribute("courses", courses);
    return "updateStudent";
  }

  //生徒情報の登録処理
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    service.addStudent(studentDetail);
    return "redirect:/studentList";
  }

  //生徒情報の更新処理
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudent(studentDetail);
    return "redirect:/studentList";
  }
}
