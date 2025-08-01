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
    Student student = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "東京",
        25,
        "男性",
        null,
        false
    );

    return student;
  }

  /*受講生情報一覧*/
  private static List<Student> getSampleStudents() {
    //受講生1
    Student student1 = new Student(
        "1",
        "山本太郎",
        "ヤマモトタロウ",
        "タロ",
        "taro@exampl.com",
        "東京",
        25,
        "男性",
        null,
        true
    );

    //受講生2
    Student student2 = new Student(
        "2",
        "鈴木一郎",
        "スズキイチロウ",
        "イチ",
        "ichiro@exampl.com",
        "大阪",
        30,
        "男性",
        null,
        false
    );
    //受講生3
    Student student3 = new Student(
        "3",
        "田中花子",
        "タナカハナコ",
        "ハナ",
        "hana@exampl.com",
        "北海道",
        22,
        "女性",
        null,
        false
    );

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
    Course course1 = new Course("00001", "Javaコース");
    //コース情報1
    Course course2 = new Course("00002", "AWSコース");
    //リターン用オブジェクトの作成
    List<Course> courses = new ArrayList<>();
    courses.add(course1);
    courses.add(course2);

    return courses;
  }

  /*受講コース情報一覧*/
  private static List<CourseDetail> getSampleCourseDetails() {
    //コース情報
    Course course = new Course("00001", "Javaコース");

    //受講情報1
    StudentsCourses studentsCourses1 = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    //受講情報2
    StudentsCourses studentsCourses2 = StudentsCourses.builder()
        .id(2)
        .studentId("2")
        .courseId("00001")
        .startDate(Date.valueOf("2024-01-01"))
        .expectedCompletionDate(Date.valueOf("2025-01-01"))
        .build();

    //受講情報3
    StudentsCourses studentsCourses3 = StudentsCourses.builder()
        .id(3)
        .studentId("3")
        .courseId("00001")
        .startDate(Date.valueOf("2024-01-01"))
        .expectedCompletionDate(Date.valueOf("2025-01-01"))
        .build();

    //受講コース情報１
    CourseDetail courseDetail1 = new CourseDetail(course, studentsCourses1);
    //受講コース情報２
    CourseDetail courseDetail2 = new CourseDetail(course, studentsCourses2);
    //受講コース情報２
    CourseDetail courseDetail3 = new CourseDetail(course, studentsCourses3);
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
    StudentsCourses studentsCourses1 = StudentsCourses.builder()
        .id(1)
        .studentId("1")
        .courseId("00001")
        .startDate(Date.valueOf("2023-09-01"))
        .expectedCompletionDate(Date.valueOf("2024-09-01"))
        .build();

    //受講情報2
    StudentsCourses studentsCourses2 = StudentsCourses.builder()
        .id(2)
        .studentId("2")
        .courseId("00002")
        .startDate(Date.valueOf("2024-01-01"))
        .expectedCompletionDate(Date.valueOf("2025-01-01"))
        .build();

    //リターン用オブジェクトの作成
    List<StudentsCourses> studentsCoursesList = new ArrayList<>();
    studentsCoursesList.add(studentsCourses1);
    studentsCoursesList.add(studentsCourses2);

    return studentsCoursesList;
  }

}
