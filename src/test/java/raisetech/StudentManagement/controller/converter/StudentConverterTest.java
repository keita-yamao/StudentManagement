package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import factory.TestDataFactory;
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
    List<Student> students = TestDataFactory.createSampleStudents();
    List<CourseDetail> courseDetails = TestDataFactory.createSampleCourseDetails();
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
    Student student = TestDataFactory.createSampleStudent();
    List<CourseDetail> courseDetails = TestDataFactory.createSampleCourseDetails();
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
    List<StudentsCourses> studentsCourses = TestDataFactory.createSampleStudentsCourses();
    List<Course> courses = TestDataFactory.createSampleCourses();
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
}
