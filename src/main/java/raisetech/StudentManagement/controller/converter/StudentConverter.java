package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.CourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentsCoursesDetail;

/**
 * 受講生詳細を受講生や受講コース情報、もしくはその逆の変換を行う また、受講情報とコース情報から受講コース情報へ変換を行うコンバータークラス
 */

@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報のマッピング 受講生コース情報は受講生に対して複数存在するのでループ処理で受講生情報を作成する
   *
   * @param students      受講生一覧
   * @param courseDetails 受講コース情報リスト
   * @return 受講生詳細情報一覧
   */
  public List<StudentDetail> createStudentDetails(List<Student> students,
      List<CourseDetail> courseDetails) {
    //受講生情報＋受講コース情報を持つreturn用リストオブジェクト作成
    List<StudentDetail> studentDetails = new ArrayList<>();
    //受講生情報ごとに処理
    students.forEach(student -> {
      //受講生詳細情報オブジェクト作成
      StudentDetail studentDetail = new StudentDetail();
      //受講コース情報一覧オブジェクト作成
      List<CourseDetail> courseDetailList = courseDetails.stream().filter(
              //受講生IDをもとに受講生情報と受講コース情報の突合処理
              courseDetail -> student.getStudentId()
                  .equals(courseDetail.getStudentsCoursesDetail().getStudentsCourses().getStudentId()))
          .collect(Collectors.toList());
      //生徒情報＋受講情報+コース情報を受講生詳細情報にset
      if (!courseDetailList.isEmpty()) {
        studentDetail.setStudent(student);
        studentDetail.setCourseDetail(courseDetailList);
        studentDetails.add(studentDetail);
      }
    });
    return studentDetails;
  }

  /**
   * 単一の受講生に紐づく受講生コース情報のマッピング 受講生コース情報は受講生に対して複数存在するのでループ処理で受講生情報を作成する
   *
   * @param student       受講生情報
   * @param courseDetails 受講コース情報リスト
   * @return 受講生詳細情報
   */
  public StudentDetail createStudentDetail(Student student, List<CourseDetail> courseDetails) {
    //受講生コース情報一覧オブジェクト作成
    List<CourseDetail> courseDetailList = courseDetails.stream().filter(
            //受講生IDをもとに受講生情報と受講生コース情報を突合処理
            courseDetail -> student.getStudentId()
                .equals(courseDetail.getStudentsCoursesDetail().getStudentsCourses().getStudentId()))
        .collect(Collectors.toList());
    //受講生情報＋受講コース情報を持つreturn用オブジェクト作成
    StudentDetail studentDetail = new StudentDetail(student, courseDetailList);
    return studentDetail;
  }

  /**
   * 受講情報に紐づくコース情報のマッピング 受講情報はコース情報に対して複数存在するのでループ処理で受講生コース情報を作成する。
   *
   * @param studentsCoursesDetails 受講状態情報一覧
   * @param courses                コース情報一覧
   * @return 受講生コース情報一覧
   */
  public List<CourseDetail> createCourseDetails(List<StudentsCoursesDetail> studentsCoursesDetails,
      List<Course> courses) {
    //受講コース情報一覧の作成
    List<CourseDetail> courseDetails = new ArrayList<>();
    //受講情報ごとに処理
    studentsCoursesDetails.forEach(studentsCoursesDetail -> {
      //受講コース情報のオブジェクト作成
      CourseDetail courseDetail = new CourseDetail();
      //受講情報とコース情報の突合処理
      courses.stream().filter(course -> studentsCoursesDetail.getStudentsCourses().getCourseId()
              .equals(course.getCourseId()))
          .forEach(course -> {
            courseDetail.setCourse(course);
            courseDetail.setStudentsCoursesDetail(studentsCoursesDetail);
          });
      //受講コース情報一覧に追加
      courseDetails.add(courseDetail);
    });
    return courseDetails;
  }

  /**
   * 受講情報に紐づく受講状態のマッピングし受講状態情報を作成する。受講情報と受講状態は1:1。
   *
   * @param studentsCourses
   * @param courseStatuses
   * @return
   */
  public List<StudentsCoursesDetail> createStudentsCoursesDetail(
      List<StudentsCourses> studentsCourses, List<CourseStatus> courseStatuses) {
    //受講状態情報一覧のリターン用オブジェクトの作成
    List<StudentsCoursesDetail> studentsCoursesDetails = new ArrayList<>();
    //受講情報ごとに処理
    studentsCourses.forEach(studentsCourse -> {
      //受講状態一覧からIDの一致するレコードを取得
      CourseStatus courseStatus = courseStatuses.stream()
          .filter(cs -> studentsCourse.getId() == cs.getStudentsCoursesId())
          .findFirst()
          .orElseThrow();
      //受講状態情報オブジェクトの作成
      StudentsCoursesDetail studentsCoursesDetail = new StudentsCoursesDetail(studentsCourse,
          courseStatus);
      studentsCoursesDetails.add(studentsCoursesDetail);
    });
    return studentsCoursesDetails;
  }
}


