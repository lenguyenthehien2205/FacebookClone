package com.project.facebook.repositories;

import com.project.facebook.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {
}
