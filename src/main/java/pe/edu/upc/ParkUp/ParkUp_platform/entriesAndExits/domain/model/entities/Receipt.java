package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.entities.AuditableModel;

import java.math.BigDecimal;

/**
 * Receipt entity
 * <p>
 *     This entity represents the payment receipt issued after 
 *     the payment of a parking session.
 * </p>
 */
@Entity
@Table(name = "receipts")
@Getter
@Setter
public class Receipt extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "qr_code", length = 500)
    private String qrCode;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", length = 3)
    private String currency;

    public Receipt() {
        this.currency = "PEN"; // Default to Peruvian Sol
    }

    public Receipt(Long paymentId, BigDecimal totalAmount) {
        this();
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
    }

    /**
     * Generate QR code for the receipt
     * @param qrCode the QR code string
     * @return the receipt with updated QR code
     */
    public Receipt generateQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    /**
     * Set PDF URL for the receipt
     * @param pdfUrl the PDF URL
     * @return the receipt with updated PDF URL
     */
    public Receipt setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
        return this;
    }

    /**
     * Check if receipt has QR code
     * @return true if QR code is set, false otherwise
     */
    public boolean hasQrCode() {
        return this.qrCode != null && !this.qrCode.isEmpty();
    }

    /**
     * Check if receipt has PDF
     * @return true if PDF URL is set, false otherwise
     */
    public boolean hasPdf() {
        return this.pdfUrl != null && !this.pdfUrl.isEmpty();
    }
}
