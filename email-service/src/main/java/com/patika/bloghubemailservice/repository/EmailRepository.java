package com.patika.bloghubemailservice.repository;

import com.patika.bloghubemailservice.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
