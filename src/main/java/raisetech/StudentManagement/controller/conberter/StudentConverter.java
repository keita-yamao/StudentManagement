package raisetech.StudentManagement.controller.conberter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> studentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    for (Student student : students) {
      List<StudentsCourses> convertStudentCourses = new ArrayList<>();
      for (StudentsCourses studentsCourse : studentsCourses) {
        if (student.getStudentId().equals(studentsCourse.getStudentId())) {
          convertStudentCourses.add(studentsCourse);
        }
      }
      if (!convertStudentCourses.isEmpty()) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(convertStudentCourses);
        studentDetails.add(studentDetail);
      }
    }
    return studentDetails;
  }
}