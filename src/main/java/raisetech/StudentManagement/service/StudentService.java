package raisetech.StudentManagement.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  private StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //生徒情報のリスト表示
  public List<Student> searchStudentList(int minAge, int maxAge) {
    List<Student> studentList = repository.studentSearch();
    {
      return studentList.stream()
          .filter(student -> student.getAge() >= minAge && student.getAge() <= maxAge)
          .collect(Collectors.toList());
    }
  }

  //生徒コース情報のリスト表示
  public List<StudentsCourses> searchStudentCourseList(String courseId) {
    List<StudentsCourses> courseList = repository.studentsCourseSearch();
    return courseList.stream()
        .filter(StudentsCourses -> StudentsCourses.getCourseId().equals(courseId))
        .collect(Collectors.toList());
  }

  //生徒コース情報の全件表示
  public List<StudentsCourses> searchAllStudentCourseList() {
    return repository.studentsCourseSearch();
  }

  //コースの全件検索
  public List<Course> searchCourseList() {
    return repository.coursesSearch();
  }

  //生徒情報の新規追加
  public void addStudent(StudentDetail studentDetail) {
    //生徒情報と受講コースの両方で使用するもの
    //生徒IDの決定
    List<Student> studentList = repository.studentSearch();
    String studentId = Integer.toString(studentList.size() + 1);

    //生徒情報追加に関する処理
    //引数のオブジェクトからデータをset
    Student student = studentDetail.getStudent();
    //生徒IDのset
    student.setStudentId(studentId);
    //論理削除フラグをset
    student.setDeleted(false);
    //studentsテーブルに追加
    repository.studentInsert(student);

    //受講コースの追加に関する処理
    List<CourseDetail> courseDetails = studentDetail.getCourseDetail();
    //日付の取得
    LocalDate startDate = LocalDate.now();
    //終了日の計算
    LocalDate expectedCompletionDate = startDate.plusYears(1);
    for (CourseDetail courseDetail : courseDetails) {
      //引数のオブジェクトからデータをset
      StudentsCourses studentsCourses = courseDetail.getStudentsCourses();
      //生徒IDをset
      studentsCourses.setStudentId(studentId);
      //開始日をset
      studentsCourses.setStartDate(Date.valueOf(startDate));
      //終了日をset
      studentsCourses.setExpectedCompletionDate(Date.valueOf(expectedCompletionDate));
      //students_coursesテーブルに追加
      repository.studentCourseInsert(studentsCourses);
    }
  }
}