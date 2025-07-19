package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class Customer {

    private UUID id;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private Integer loyaltyPoints;

    public Customer(UUID id,
                    String fullName,
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
        this.setLoyaltyPoints(0);
    }

    public Customer(UUID id,
                    String fullName,
                    LocalDate birthDate,
                    String email,
                    String phone,
                    String document,
                    Boolean promotionNotificationsAllowed,
                    Boolean archived,
                    OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt,
                    Integer loyaltyPoints) {
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

    public void addLoyaltyPoints(Integer points) {
        verityIfChangeable();
        if (points <= 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE_OR_ZERO);
        }
        this.setLoyaltyPoints(this.loyaltyPoints + points);
    }

    public void archive() {
        verityIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName("Anonymous");
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

    public void changeName(String fullName) {
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

    public UUID id() {
        return id;
    }

    public String fullName() {
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

    public Integer loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setId(UUID id) {
        Objects.requireNonNull(id, VALIDATION_ERROR_ID_CANNOT_BE_NULL);
        this.id = id;
    }

    private void setFullName(String fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULL_NAME_CANNOT_BE_NULL);
        if (fullName.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_FULL_NAME_CANNOT_BE_BLANK);
        }
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

    private void setLoyaltyPoints(Integer loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints, VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NULL);
        if (loyaltyPoints < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE_OR_ZERO);
        }
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
