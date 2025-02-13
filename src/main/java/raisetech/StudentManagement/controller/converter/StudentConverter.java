package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> studentDetails(List<Student> students,
      List<CourseDetail> courseDetails) {
    //生徒情報＋リスト(受講情報+コース情報)を持つreturn用リストオブジェクト作成
    List<StudentDetail> studentDetails = new ArrayList<>();
    //生徒情報ごとに処理
    students.forEach(student -> {
      //生徒情報＋リスト(受講情報+コース情報)オブジェクト作成
      StudentDetail studentDetail = new StudentDetail();
      //リスト(受講情報+コース情報)オブジェクト作成
      List<CourseDetail> courseDetailList = courseDetails.stream().filter(
              //生徒IDをもとに生徒情報とリスト(受講情報+コース情報)の突合処理
              courseDetail -> student.getStudentId()
                  .equals(courseDetail.getStudentsCourses().getStudentId()))
          .collect(Collectors.toList());
      //生徒情報＋受講情報+コース情報をset
      if (!courseDetailList.isEmpty()) {
        studentDetail.setStudent(student);
        studentDetail.setCourseDetail(courseDetailList);
        studentDetails.add(studentDetail);
      }
    });
    return studentDetails;
  }

  public List<CourseDetail> courseDetails(List<StudentsCourses> studentsCourses,
      List<Course> courses) {
    //受講情報＋コース情報のリストの作成
    List<CourseDetail> courseDetails = new ArrayList<>();
    //受講情報ごとに処理
    studentsCourses.forEach(studentsCourse -> {
      //受講情報＋コース情報のオブジェクト作成
      CourseDetail courseDetail = new CourseDetail();
      //受講情報とコース情報の突合処理
      courses.stream().filter(course -> studentsCourse.getCourseId().equals(course.getCourseId()))
          .forEach(course -> {
            courseDetail.setCourse(course);
            courseDetail.setStudentsCourses(studentsCourse);
          });
      //リスト(受講情報+コース情報)に追加
      courseDetails.add(courseDetail);
    });
    return courseDetails;
  }
}


