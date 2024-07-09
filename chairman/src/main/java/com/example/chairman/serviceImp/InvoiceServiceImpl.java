package com.example.chairman.serviceImp;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import com.example.chairman.mapper.InvoiceMapper;
import com.example.chairman.model.invoice.FilterRequest;
import com.example.chairman.model.invoice.InvoiceRequest;
import com.example.chairman.model.invoice.InvoiceResponse;
import com.example.chairman.model.invoice.TableInvoiceResponse;
import com.example.chairman.repository.InvoiceRepository;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.InvoiceService;
import com.example.chairman.specification.specificationFormer.InvoiceSpecificationFormer;
import com.example.chairman.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceSpecificationFormer invoiceSpecificationFormer;
    private final UploadFileUtil uploadFileUtil;
    private final Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository,
                              InvoiceMapper invoiceMapper, InvoiceSpecificationFormer invoiceSpecificationFormer,
                              UploadFileUtil uploadFileUtil) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.invoiceMapper = invoiceMapper;
        this.invoiceSpecificationFormer = invoiceSpecificationFormer;
        this.uploadFileUtil = uploadFileUtil;
    }

    @Override
    public void createInvoice(InvoiceRequest invoiceRequest) {
        logger.info("createInvoice - Creating invoice "+invoiceRequest.toString());
        User user = getUser(invoiceRequest.userId());
        String file = uploadFileUtil.saveMultipartFile(invoiceRequest.file());
        String number = getNumber();
        LocalDate localDate = LocalDate.now();
        Invoice invoice = invoiceMapper.invoiceRequestToInvoice(invoiceRequest, user, file, number, localDate);
        invoiceRepository.save(invoice);
        logger.info("createInvoice - Invoice has been created");
    }

    @Override
    public Page<TableInvoiceResponse> getTableInvoiceResponses(FilterRequest filterRequest) {
        logger.info("getTableInvoiceResponses - Getting table invoice responses "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<Invoice> invoicePage = getFilteredInvoices(filterRequest, pageable);
        List<TableInvoiceResponse> tableInvoiceResponses = invoiceMapper.invoiceListToTableInvoiceResponseList(invoicePage.getContent());
        Page<TableInvoiceResponse> tableInvoiceResponsePage = new PageImpl<>(tableInvoiceResponses, pageable, invoicePage.getTotalElements());
        logger.info("getTableInvoiceResponses - Table invoice responses have been got");
        return tableInvoiceResponsePage;
    }

    private Page<Invoice> getFilteredInvoices(FilterRequest filterRequest, Pageable pageable) {
        Specification<Invoice> invoiceSpecification = invoiceSpecificationFormer.formTableSpecification(filterRequest);
        return invoiceRepository.findAll(invoiceSpecification, pageable);
    }

    @Override
    public void deleteInvoices(Long[] invoiceIds) {
        logger.info("deleteInvoices - Deleting invoices by ids "+invoiceIds.toString());
        List<Invoice> invoices = invoiceRepository.findAllById(List.of(invoiceIds));
        for(Invoice invoice: invoices){
            invoice.setDeleted(true);
        }
        invoiceRepository.saveAll(invoices);
        logger.info("deleteInvoices - Invoices have been deleted");
    }

    @Override
    public InvoiceResponse getInvoiceResponse(Long id) {
        logger.info("getInvoiceResponse - Getting invoice response by id "+id);
        Invoice invoice = getInvoiceById(id);
        InvoiceResponse invoiceResponse = invoiceMapper.invoiceToInvoiceResponse(invoice);
        logger.info("getInvoiceResponse - Invoice response has been got");
        return invoiceResponse;
    }

    @Override
    public void updateInvoice(Long id, InvoiceRequest invoiceRequest) {
        logger.info("updateInvoice - Updating invoice by id "+id+" "+invoiceRequest.toString());
        Invoice invoice = getInvoiceById(id);
        User user = getUser(invoiceRequest.userId());
        String file = updateFile(invoiceRequest.file(), invoice);
        invoiceMapper.updateInvoice(invoice, user, file);
        invoiceRepository.save(invoice);
        logger.info("updateInvoice - Invoice has been updated");
    }

    private String updateFile(MultipartFile file, Invoice invoice) {
        String currentFile = invoice.getFile();
        if(file.isEmpty()){
            return currentFile;
        } else {
            uploadFileUtil.deleteFile(currentFile);
            return uploadFileUtil.saveMultipartFile(file);
        }
    }

    @Override
    public String getNumber() {
        logger.info("getNumber - Getting invoice number");
        Optional<Invoice> lastInvoice = invoiceRepository.findLast();
        String number = lastInvoice.map(invoice -> formNumber(invoice.getNumber())).orElse("0000000001");
        logger.info("getNumber - Invoice number has been got");
        return number;
    }
    private String formNumber(String number) {
        int numberPart = Integer.parseInt(number);
        return StringUtils.leftPad(Integer.toString(numberPart + 1), 10, "0");
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User was not found by id "+id));
    }
    private Invoice getInvoiceById(Long id){
        return invoiceRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Invoice was not found by id "+id));
    }
}
