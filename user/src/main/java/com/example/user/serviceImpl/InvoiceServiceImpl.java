package com.example.user.serviceImpl;

import com.amazonaws.services.s3.model.S3Object;
import com.example.user.entity.Invoice;
import com.example.user.entity.User;
import com.example.user.mapper.InvoiceMapper;
import com.example.user.model.invoice.FilterRequest;
import com.example.user.model.invoice.TableInvoiceResponse;
import com.example.user.repository.InvoiceRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.AmazonS3Service;
import com.example.user.service.InvoiceService;
import com.example.user.specification.specificationFormer.InvoiceSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceSpecificationFormer invoiceSpecificationFormer;
    private final AmazonS3Service amazonS3Service;
    private final Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository,
                              InvoiceMapper invoiceMapper, InvoiceSpecificationFormer invoiceSpecificationFormer,
                              AmazonS3Service amazonS3Service) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.invoiceMapper = invoiceMapper;
        this.invoiceSpecificationFormer = invoiceSpecificationFormer;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    public Page<TableInvoiceResponse> getInvoiceResponsesForTable(FilterRequest filterRequest) {
        logger.info("getInvoiceResponsesForTable() - Getting invoice responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<Invoice> invoicePage = getFilteredInvoices(filterRequest, pageable);
        List<TableInvoiceResponse> tableInvoiceResponses = invoiceMapper.invoiceListToTableInvoiceResponseList(invoicePage.getContent());
        Page<TableInvoiceResponse> tableInvoiceResponsePage = new PageImpl<>(tableInvoiceResponses, pageable, invoicePage.getTotalElements());
        logger.info("getInvoiceResponsesForTable() - Invoice responses have been got");
        return tableInvoiceResponsePage;
    }

    private Page<Invoice> getFilteredInvoices(FilterRequest filterRequest, Pageable pageable) {
        User user = getAuthenticatedUser();
        Specification<Invoice> invoiceSpecification = invoiceSpecificationFormer.formTableSpecification(filterRequest, user);
        return invoiceRepository.findAll(invoiceSpecification, pageable);
    }

    private User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsernameAndDeletedIsFalse(userDetails.getUsername()).orElseThrow(()-> new EntityNotFoundException("User was not found by username "+userDetails.getUsername()));
        return user;
    }

    @Override
    public String getInvoiceFile(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Invoice was not found by id "+id));
        S3Object s3Object = amazonS3Service.getS3Object(invoice.getFile());
        String encodedFile = null;
        try {
            encodedFile = Base64.getEncoder().encodeToString(s3Object.getObjectContent().readAllBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return encodedFile;
    }
}
