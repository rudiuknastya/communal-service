package com.example.chairman.serviceImp;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import com.example.chairman.mapper.InvoiceMapper;
import com.example.chairman.model.invoice.InvoiceRequest;
import com.example.chairman.repository.InvoiceRepository;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.InvoiceService;
import com.example.chairman.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final InvoiceMapper invoiceMapper;
    private final UploadFileUtil uploadFileUtil;
    private final Logger logger = LogManager.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserRepository userRepository,
                              InvoiceMapper invoiceMapper, UploadFileUtil uploadFileUtil) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.invoiceMapper = invoiceMapper;
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
}
