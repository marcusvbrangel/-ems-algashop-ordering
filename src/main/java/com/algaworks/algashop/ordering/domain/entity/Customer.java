package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class Customer {

    private CustomerId id;
    private FullName fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;

    public Customer(CustomerId id,
                    FullName fullName,
                    LocalDate birthDate,
                    String email,
                    String phone,
                    String document,
                    Boolean promotionNotificationsAllowed,
                    OffsetDateTime registeredAt) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setRegisteredAt(registeredAt);
        this.setArchived(false);
        this.setLoyaltyPoints(LoyaltyPoints.ZERO);
    }

    public Customer(CustomerId id,
                    FullName fullName,
                    LocalDate birthDate,
                    String email,
                    String phone,
                    String document,
                    Boolean promotionNotificationsAllowed,
                    Boolean archived,
                    OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt,
                    LoyaltyPoints loyaltyPoints) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
    }

    public void addLoyaltyPoints(LoyaltyPoints points) {
        verityIfChangeable();
        this.setLoyaltyPoints(this.loyaltyPoints.add(points));
    }

    public void archive() {
        verityIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone("000-000-0000");
        this.setDocument("000-00-0000");
        this.setEmail(UUID.randomUUID() + "@anonymous.com");
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);
    }

    public void enablePromotionNotifications() {
        verityIfChangeable();
        this.setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotifications() {
        verityIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullName) {
        verityIfChangeable();
        this.setFullName(fullName);
    }

    public void changeEmail(String email) {
        verityIfChangeable();
        this.setEmail(email);
    }

    public void changePhone(String phone) {
        verityIfChangeable();
        this.setPhone(phone);
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public String document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setId(CustomerId id) {
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_IN_THE_FUTURE);
        }
        this.birthDate = birthDate;
    }

    private void setEmail(String email) {
        FieldValidations.requireValidEmail(email, VALIDATION_ERROR_EMAIL_IS_NOT_VALID);
        this.email = email;
    }

    private void setPhone(String phone) {
        Objects.requireNonNull(phone, VALIDATION_ERROR_PHONE_CANNOT_BE_NULL);
        this.phone = phone;
    }

    private void setDocument(String document) {
        Objects.requireNonNull(document, VALIDATION_ERROR_DOCUMENT_CANNOT_BE_NULL);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed, VALIDATION_ERROR_PROMOTION_NOTIFICATIONS_ALLOWED_CANNOT_BE_NULL);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived, VALIDATION_ERROR_ARCHIVED_STATUS_CANNOT_BE_NULL);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt, VALIDATION_ERROR_REGISTERED_AT_CANNOT_BE_NULL);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    private void verityIfChangeable() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
