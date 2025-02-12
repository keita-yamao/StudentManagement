package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Course;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  @Select("SELECT * FROM courses")
  List<Course> searchCourses();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Insert(
      "INSERT INTO students (student_id,name,furigana,nickname,email,address,age,gender,is_deleted) "
          + "VALUES (#{studentId},#{name},#{furigana},#{nickname},#{email},#{address},#{age},#{gender},#{isDeleted})")
  void insertStudent(Student student);

  @Insert(
      "INSERT INTO students_courses (student_id,course_id,start_date,expected_completion_date) "
          + "VALUES (#{studentId},#{courseId},#{startDate},#{expectedCompletionDate})")
  void insertStudentCourse(StudentsCourses studentsCourses);
}
