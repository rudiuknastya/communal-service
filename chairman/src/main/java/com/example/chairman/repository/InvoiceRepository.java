package com.example.chairman.repository;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    @Query(value = "SELECT * FROM invoices WHERE deleted = false ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Invoice> findLast();
}
