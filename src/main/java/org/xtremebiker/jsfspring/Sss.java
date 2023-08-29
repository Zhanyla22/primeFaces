//package org.infosystema.esf.controller.doc;
//
//
//import org.infosystema.esf.controller.FileUploadController;
//import org.infosystema.esf.conversiation.Conversational;
//import org.infosystema.esf.dto.AdvancePaymentDetailDto;
//import org.infosystema.esf.dto.AdvancePaymentDocumentDto;
//import org.infosystema.esf.model.Dictionary;
//import org.infosystema.esf.model.LegalPersonBankAccount;
//import org.infosystema.esf.model.catalog.Tax;
//import org.infosystema.esf.model.document.PrimaryAccountingDetail;
//import org.infosystema.esf.model.document.advance_payment.AdvancePaymentDetail;
//import org.infosystema.esf.service.ContractorCatalogService;
//import org.infosystema.esf.service.DictionaryService;
//import org.infosystema.esf.service.impl.AdvancePaymentDocumentFacadeService;
//import org.infosystema.esf.util.Util;
//import org.infosystema.esf.util.web.LoginUtil;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.enterprise.context.ConversationScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.transaction.Transactional;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.List;
//
//@Named
//@ConversationScoped
//public class Sss extends Conversational implements Serializable {
//
//    @EJB
//    private AdvancePaymentDocumentFacadeService advancePaymentDocumentFacadeService;
//
//    @Inject
//    private LoginUtil loginUtil;
//
//    @Inject
//    private FileUploadController controller;
//
//    @EJB
//    private DictionaryService dictService;
//
//    private AdvancePaymentDocumentDto advancePaymentDocumentDto;
//
//    private AdvancePaymentDetailDto advancePaymentDetailDto;
//
//    private Dictionary currency;
//
//
//    @PostConstruct
//    public void init() {
//        advancePaymentDocumentDto = new AdvancePaymentDocumentDto();
//        currency = getCurrency();
//        advancePaymentDocumentDto.setCurrencyId(currency);
//    }
//
//
//    public String getAddViewAdvancePayment() {
//        return "/view/document/advance_payment_form.xhtml?faces-redirect=true";
//    }
//
//    @Transactional
//    public String saveAdvancedPayment() {
//        try {
//            advancePaymentDocumentFacadeService.createDocument(advancePaymentDocumentDto);
//            return "/view/document/advance_payment_list.xhtml?faces-redirect=true";
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public void addProductsToAdvancePayment() throws Exception {
//        AdvancePaymentDetailDto advancePaymentDetailDto = new AdvancePaymentDetailDto();
//        advancePaymentDocumentDto.getDetails().add(advancePaymentDetailDto);
//    }
//
//    public void updateDetailAmount(AdvancePaymentDetailDto advancePaymentDetailDto) {
//        advancePaymentDocumentDto.getDetails().forEach(i -> {
//            if (advancePaymentDetailDto.equals(i)) {
//                if (i.getAmount() != null)
//                    i.setAmount(i.getAmount());
//            }
//        });
//    }
//
//    public void deleteItem(AdvancePaymentDetailDto advancePaymentDetailDto) {
//        advancePaymentDocumentDto.getDetails().removeIf(e -> e.equals(advancePaymentDetailDto));
//    }
//
//    public BigDecimal updateTotalAmount() {
//        advancePaymentDocumentDto.setTotalAmount(Util.updateTotalAmountAdvancePaymentDetail(advancePaymentDocumentDto.getDetails()));
//        return advancePaymentDocumentDto.getTotalAmount();
//    }
//
//    public String cancel() {
//        return "/view/document/advance_payment_list.xhtml?faces-redirect=true";
//    }
//
//    public List<Dictionary> getReceiptType(String query) {
//        return advancePaymentDocumentFacadeService.getReceiptType(query);
//    }
//
//    public List<LegalPersonBankAccount> getLegalPersonBankAccounts(String query) {
//        return advancePaymentDocumentFacadeService.getLegalPersonBanksAccounts(query);
//    }
//
//    public List<LegalPersonBankAccount> getContractorBankAccounts(String query) {
//        return advancePaymentDocumentFacadeService.getLegalPersonContractorBanksAccounts(query);
//    }
//    public List<Dictionary> getInvoiceDeliveryTypes(String query) {
//        return advancePaymentDocumentFacadeService.getInvoiceDeliveryTypes(query);
//    }
//
//    public List<Dictionary> getPaymentTypes(String query) {
//        return advancePaymentDocumentFacadeService.getPaymentTypes(query);
//    }
//
//    public List<Tax> getTaxVat(String query) {
//        return advancePaymentDocumentFacadeService.getTaxVat(query);
//    }
//
//    public List<Tax> getTaxSt(String query) {
//        return advancePaymentDocumentFacadeService.getTaxSt(query);
//    }
//
//    public Dictionary getCurrency() {
//        return advancePaymentDocumentFacadeService.getCurrency();
//    }
//
//    public AdvancePaymentDocumentDto getAdvancePaymentDocumentDto() {
//        return advancePaymentDocumentDto;
//    }
//
//    public void setAdvancePaymentDocumentDto(AdvancePaymentDocumentDto advancePaymentDocumentDto) {
//        this.advancePaymentDocumentDto = advancePaymentDocumentDto;
//    }
//
//    public AdvancePaymentDetailDto getAdvancePaymentDetailDto() {
//        return advancePaymentDetailDto;
//    }
//
//    public AdvancePaymentController setAdvancePaymentDetailDto(AdvancePaymentDetailDto advancePaymentDetailDto) {
//        this.advancePaymentDetailDto = advancePaymentDetailDto;
//        return this;
//    }
//
//    public AdvancePaymentController setCurrency(Dictionary currency) {
//        this.currency = currency;
//        return this;
//    }
//}