package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domein.CourseDetail;
import raisetech.StudentManagement.domein.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  @DisplayName("受講生一覧と受講生コース情報のマッピング処理ができているか")
  void createStudentDetailsTest01() {
    //テストデータのセット
    List<Student> students = getSampleStudents();
    List<CourseDetail> courseDetails = getSampleCourseDetails();
    //実行
    List<StudentDetail> result = sut.createStudentDetails(students, courseDetails);
    //検証
    assertThat(result)
        .extracting(sd -> sd.getStudent().getStudentId(),
            sd -> sd.getStudent().getName(),
            sd -> sd.getCourseDetail().getFirst().getStudentsCourses().getId(),
            sd -> sd.getCourseDetail().getFirst().getStudentsCourses().getStudentId())
        .containsExactly(
            Tuple.tuple(students.get(0).getStudentId(),
                students.get(0).getName(),
                courseDetails.get(0).getStudentsCourses().getId(),
                courseDetails.get(0).getStudentsCourses().getStudentId()),
            Tuple.tuple(students.get(1).getStudentId(),
                students.get(1).getName(),
                courseDetails.get(1).getStudentsCourses().getId(),
                courseDetails.get(1).getStudentsCourses().getStudentId()),
            Tuple.tuple(students.get(2).getStudentId(),
                students.get(2).getName(),
                courseDetails.get(2).getStudentsCourses().getId(),
                courseDetails.get(2).getStudentsCourses().getStudentId())
        );
  }

  @Test
  @DisplayName("単一の受講生に紐づく受講生コース情報のマッピング処理が出来ているか")
  void createStudentDetailTest01() {
    //テストデータのセット
    Student student = getSampleStudent();
    List<CourseDetail> courseDetails = getSampleCourseDetails();
    //実行
    StudentDetail result = sut.createStudentDetail(student, courseDetails);
    //検証
    assertThat(result).extracting(sd -> sd.getStudent().getStudentId(),
            sd -> sd.getStudent().getName(),
            sd -> sd.getCourseDetail().getFirst().getStudentsCourses().getId(),
            sd -> sd.getCourseDetail().getFirst().getStudentsCourses().getStudentId())
        .containsExactly(student.getStudentId(),
            student.getName(),
            courseDetails.get(0).getStudentsCourses().getId(),
            courseDetails.get(0).getStudentsCourses().getStudentId()
        );
  }


  @Test
  @DisplayName("受講情報に紐づくコース情報のマッピング処理が出来ているか")
  void createCourseDetailsTest() {
    //テストデータのセット
    List<StudentsCourses> studentsCourses = getSampleStudentsCourses();
    List<Course> courses = getSampleCourses();
    //実行
    List<CourseDetail> result = sut.createCourseDetails(studentsCourses, courses);
    //検証
    assertThat(result).extracting(cd -> cd.getStudentsCourses().getCourseId(),
            cd -> cd.getCourse().getCourseId(),
            cd -> cd.getCourse().getCourse())
        .containsExactly(
            Tuple.tuple(studentsCourses.get(0).getCourseId(), courses.get(0).getCourseId(),
                courses.get(0).getCourse()),
            Tuple.tuple(studentsCourses.get(1).getCourseId(), courses.get(1).getCourseId(),
                courses.get(1).getCourse())
        );
  }

  /*
   * テストデータ
   * */

  /*単一受講生情報*/
  private static Student getSampleStudent() {
    //受講生情報
    Student student = new Student();
    student.setStudentId("1");
    student.setName("山本太郎");
    student.setFurigana("ヤマモトタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@exampl.com");
    student.setAddress("東京");
    student.setAge(25);
    student.setGender("男性");
    student.undelete();//削除フラグfalse

    return student;
  }

  /*受講生情報一覧*/
  private static List<Student> getSampleStudents() {
    //受講生1
    Student student1 = new Student();
    student1.setStudentId("1");
    student1.setName("山本太郎");
    student1.setFurigana("ヤマモトタロウ");
    student1.setNickname("タロ");
    student1.setEmail("taro@exampl.com");
    student1.setAddress("東京");
    student1.setAge(25);
    student1.setGender("男性");
    student1.delete();//削除フラグtrue
    //受講生2
    Student student2 = new Student();
    student2.setStudentId("2");
    student2.setName("鈴木一郎");
    student2.setFurigana("スズキイチロウ");
    student2.setNickname("イチ");
    student2.setEmail("ichiro@exampl.com");
    student2.setAddress("大阪");
    student2.setAge(30);
    student2.setGender("男性");
    student2.undelete();//削除フラグfalse
    //受講生3
    Student student3 = new Student();
    student3.setStudentId("3");
    student3.setName("田中花子");
    student3.setFurigana("タナカハナコ");
    student3.setNickname("ハナ");
    student3.setEmail("hana@exampl.com");
    student3.setAddress("北海道");
    student3.setAge(22);
    student3.setGender("女性");
    student3.undelete();//削除フラグfalse

    //リストに格納
    List<Student> students = new ArrayList<>();
    students.add(student1);
    students.add(student2);
    students.add(student3);

    return students;
  }

  /*コース一覧*/
  private static List<Course> getSampleCourses() {
    //コース情報1
    Course course1 = new Course();
    course1.setCourseId("00001");
    course1.setCourse("Javaコース");
    //コース情報1
    Course course2 = new Course();
    course2.setCourseId("00002");
    course2.setCourse("AWSコース");
    //リターン用オブジェクトの作成
    List<Course> courses = new ArrayList<>();
    courses.add(course1);
    courses.add(course2);

    return courses;
  }

  /*受講コース情報一覧*/
  private static List<CourseDetail> getSampleCourseDetails() {
    //コース情報
    Course course = new Course();
    course.setCourseId("00001");
    course.setCourse("Javaコース");

    //受講情報1
    StudentsCourses studentsCourses1 = new StudentsCourses();
    studentsCourses1.setId(1);
    studentsCourses1.setStudentId("1");
    studentsCourses1.setCourseId("00001");
    studentsCourses1.setStartDate(Date.valueOf("2023-09-01"));
    studentsCourses1.setExpectedCompletionDate(Date.valueOf("2024-09-01"));
    //受講情報2
    StudentsCourses studentsCourses2 = new StudentsCourses();
    studentsCourses2.setId(2);
    studentsCourses2.setStudentId("2");
    studentsCourses2.setCourseId("00001");
    studentsCourses2.setStartDate(Date.valueOf("2024-01-01"));
    studentsCourses2.setExpectedCompletionDate(Date.valueOf("2025-01-01"));
    //受講情報3
    StudentsCourses studentsCourses3 = new StudentsCourses();
    studentsCourses3.setId(3);
    studentsCourses3.setStudentId("3");
    studentsCourses3.setCourseId("00001");
    studentsCourses3.setStartDate(Date.valueOf("2024-01-01"));
    studentsCourses3.setExpectedCompletionDate(Date.valueOf("2025-01-01"));

    //受講コース情報１
    CourseDetail courseDetail1 = new CourseDetail();
    courseDetail1.setCourse(course);
    courseDetail1.setStudentsCourses(studentsCourses1);
    //受講コース情報２
    CourseDetail courseDetail2 = new CourseDetail();
    courseDetail2.setCourse(course);
    courseDetail2.setStudentsCourses(studentsCourses2);
    //受講コース情報２
    CourseDetail courseDetail3 = new CourseDetail();
    courseDetail3.setCourse(course);
    courseDetail3.setStudentsCourses(studentsCourses3);
    //リターンオブジェクトの作成
    List<CourseDetail> courseDetails = new ArrayList<>();
    courseDetails.add(courseDetail1);
    courseDetails.add(courseDetail2);
    courseDetails.add(courseDetail3);

    return courseDetails;
  }

  /*受講情報一覧*/
  private static List<StudentsCourses> getSampleStudentsCourses() {
    //受講情報１
    StudentsCourses studentsCourses1 = new StudentsCourses();
    studentsCourses1.setId(1);
    studentsCourses1.setStudentId("1");
    studentsCourses1.setCourseId("00001");
    studentsCourses1.setStartDate(Date.valueOf("2023-09-01"));
    studentsCourses1.setExpectedCompletionDate(Date.valueOf("2024-09-01"));
    //受講情報2
    StudentsCourses studentsCourses2 = new StudentsCourses();
    studentsCourses2.setId(2);
    studentsCourses2.setStudentId("2");
    studentsCourses2.setCourseId("00002");
    studentsCourses2.setStartDate(Date.valueOf("2024-01-01"));
    studentsCourses2.setExpectedCompletionDate(Date.valueOf("2025-01-01"));

    //リターン用オブジェクトの作成
    List<StudentsCourses> studentsCoursesList = new ArrayList<>();
    studentsCoursesList.add(studentsCourses1);
    studentsCoursesList.add(studentsCourses2);

    return studentsCoursesList;
  }

}
