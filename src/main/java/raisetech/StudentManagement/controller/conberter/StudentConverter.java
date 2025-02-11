package raisetech.StudentManagement.controller.conberter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> studentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses, List<Course> courses) {
    //生徒情報＋リスト(受講情報+コース情報)を持つreturn用リストオブジェクト作成
    List<StudentDetail> studentDetails = new ArrayList<>();
    //生徒情報ごとに処理
    for (Student student : students) {
      //受講情報＋コース情報のリストの作成
      List<CourseDetail> courseDetails = new ArrayList<>();
      //生徒情報に一致する受講情報を検索
      for (StudentsCourses studentsCourse : studentsCourses) {
        //受講情報＋コース情報のオブジェクト作成
        CourseDetail courseDetail = new CourseDetail();
        if (student.getStudentId().equals(studentsCourse.getStudentId())) {
          //受講情報に一致するコース情報を検索
          for (Course course : courses) {
            //受講情報とコース情報の突合処理
            if (studentsCourse.getCourseId().equals(course.getCourseId())) {
              courseDetail.setCourse(course);
              courseDetail.setStudentsCourses(studentsCourse);
            }
          }
          //リスト(受講情報+コース情報)に追加
          courseDetails.add(courseDetail);
        }
      }
      //生徒情報＋受講情報+コース情報をset
      if (!courseDetails.isEmpty()) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setCourseDetail(courseDetails);
        studentDetails.add(studentDetail);
      }
    }
    return studentDetails;
  }
}