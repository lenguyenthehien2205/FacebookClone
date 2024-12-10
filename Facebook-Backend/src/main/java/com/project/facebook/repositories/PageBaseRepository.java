package com.project.facebook.repositories;

import com.project.facebook.models.PageBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface PageBaseRepository extends JpaRepository<PageBase, Long> {
    boolean existsByPathName(String pathname);

    @Query(value = "SELECT CASE \n" +
            "           WHEN :pathname IS NOT NULL THEN \n" +
            "               CASE \n" +
            "                   WHEN p.display_format = 'firstname_lastname' THEN CONCAT(p.first_name, ' ', p.last_name)\n" +
            "                   WHEN p.display_format = 'lastname_firstname' THEN CONCAT(p.last_name, ' ', p.first_name)\n" +
            "               END\n" +
            "           ELSE pb.path_name\n" +
            "       END AS fullname\n" +
            "FROM page_bases pb\n" +
            "LEFT JOIN profiles p ON p.base_id = pb.id\n" +
            "WHERE pb.path_name = :pathname", nativeQuery = true)
    String getFullnameByPathname(@Param("pathname") String pathname);
}
