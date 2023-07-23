SELECT s.id, s.name, s.age, f.name FROM student AS s INNER JOIN faculty AS f ON s.faculty_id = f.id;
/*   использовал INNER JOIN так как в моем проекте у студента факультет  NOT NULL   */

SELECT s.id, s.name, s.avatar_url, a.id FROM student AS s INNER JOIN avatar AS a ON s.id = a.student_id;