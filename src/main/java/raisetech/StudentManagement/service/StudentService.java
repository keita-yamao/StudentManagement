package raisetech.StudentManagement.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  //生徒情報のリスト表示
  public List<Student> searchStudentList(int minAge, int maxAge) {
    List<Student> studentList = repository.searchStudent();
    return studentList.stream()
        .filter(student -> student.getAge() >= minAge && student.getAge() <= maxAge)
        .collect(Collectors.toList());
  }

  //studentIdから生徒情報の表示
  public Student searchStudent(String studentId) {
    List<Student> studentList = repository.searchStudent();
    return studentList.stream()
        .filter(student -> student.getStudentId().equals(studentId))
        .findFirst().orElseGet(Student::new);
  }

  //studentIdから受講コース情報の表示
  public List<StudentsCourses> studentsCourses(String studentId) {
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCourses();
    List<StudentsCourses> studentsCourses = new ArrayList<>();
    for (StudentsCourses studentsCourse : studentsCoursesList) {
      if (studentsCourse.getStudentId().equals(studentId)) {
        studentsCourses.add(studentsCourse);
      }
    }
    return studentsCourses;
  }

  //生徒コース情報のリスト表示
  public List<StudentsCourses> searchStudentCourseList(String courseId) {
    List<StudentsCourses> studentsCourseList = repository.searchStudentsCourses();
    if (courseId.isEmpty()) {
      return studentsCourseList;
    } else {
      return studentsCourseList.stream()
          .filter(StudentsCourses -> StudentsCourses.getCourseId().equals(courseId))
          .collect(Collectors.toList());
    }
  }

  //コースの全件検索
  public List<Course> searchCourseList() {
    return repository.searchCourses();
  }

  //生徒情報の新規追加
  @Transactional
  public void addStudent(StudentDetail studentDetail) {
    //生徒情報と受講コースの両方で使用するもの
    //生徒IDの決定
    List<Student> studentList = repository.searchStudent();
    String studentId = Integer.toString(studentList.size() + 1);

    //生徒情報追加に関する処理
    //引数のオブジェクトからデータをset
    Student student = studentDetail.getStudent();
    //生徒IDのset
    student.setStudentId(studentId);
    //論理削除フラグをset
    student.setDeleted(false);
    //studentsテーブルに追加
    repository.insertStudent(student);

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
      repository.insertStudentCourse(studentsCourses);
    }
  }

  //生徒情報の変更
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.updateStudent(student);
    List<CourseDetail> courseDetails = studentDetail.getCourseDetail();
    for (CourseDetail courseDetail : courseDetails) {
      StudentsCourses studentsCourses = courseDetail.getStudentsCourses();
      repository.updateStudentCourses(studentsCourses);
    }
  }
}