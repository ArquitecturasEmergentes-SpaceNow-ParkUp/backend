package pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.commands.CreateUserProfileCommand;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects.DocumentId;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects.FullName;
import pe.edu.upc.ParkUp.ParkUp_platform.profile.domain.model.valueobjects.PhoneNumber;
import pe.edu.upc.ParkUp.ParkUp_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * UserProfile aggregate root
 * Represents a user's profile and account configuration
 *
 * @see AuditableAbstractAggregateRoot
 */
@Entity
@Table(name = "user_profiles")
@Getter
public class UserProfile extends AuditableAbstractAggregateRoot<UserProfile> {

    @NotNull
    @Column(nullable = false, unique = true)
    private Long userId;

    @Embedded
    @NotNull
    private FullName fullName;

    @Embedded
    @NotNull
    private DocumentId documentId;

    @Embedded
    @NotNull
    private PhoneNumber phoneNumber;

    @Column(length = 500)
    private String profileImageUrl;

    @Column(nullable = false)
    private Boolean notificationsEnabled = true;

    @Column(nullable = false)
    private Boolean emailNotificationsEnabled = true;

    @Column(nullable = false)
    private Boolean smsNotificationsEnabled = false;

    protected UserProfile() {
    }

    /**
     * Constructor for creating a new user profile
     *
     * @param userId      The ID of the user
     * @param firstName   The user's first name
     * @param lastName    The user's last name
     * @param dni         The user's DNI
     * @param countryCode The country code for phone
     * @param phoneNumber The phone number
     */
    public UserProfile(Long userId, String firstName, String lastName, String dni, 
                      String countryCode, String phoneNumber) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        this.userId = userId;
        this.fullName = new FullName(firstName, lastName);
        this.documentId = new DocumentId(dni);
        this.phoneNumber = new PhoneNumber(countryCode, phoneNumber);
    }

    /**
     * Constructor from command
     *
     * @param command The create user profile command
     */
    public UserProfile(CreateUserProfileCommand command) {
        this(command.userId(), command.firstName(), command.lastName(), 
             command.dni(), command.countryCode(), command.phoneNumber());
    }

    /**
     * Updates the user's full name
     *
     * @param firstName The new first name
     * @param lastName  The new last name
     */
    public void updateFullName(String firstName, String lastName) {
        this.fullName = new FullName(firstName, lastName);
    }

    /**
     * Updates the user's phone number
     *
     * @param countryCode The new country code
     * @param phoneNumber The new phone number
     */
    public void updatePhoneNumber(String countryCode, String phoneNumber) {
        this.phoneNumber = new PhoneNumber(countryCode, phoneNumber);
    }

    /**
     * Updates the user's document ID
     *
     * @param dni The new DNI
     */
    public void updateDocumentId(String dni) {
        this.documentId = new DocumentId(dni);
    }

    /**
     * Updates the profile image URL
     *
     * @param imageUrl The new image URL
     */
    public void updateProfileImage(String imageUrl) {
        this.profileImageUrl = imageUrl;
    }

    /**
     * Enables or disables all notifications
     *
     * @param enabled True to enable, false to disable
     */
    public void setNotificationsEnabled(Boolean enabled) {
        this.notificationsEnabled = enabled;
    }

    /**
     * Enables or disables email notifications
     *
     * @param enabled True to enable, false to disable
     */
    public void setEmailNotificationsEnabled(Boolean enabled) {
        this.emailNotificationsEnabled = enabled;
    }

    /**
     * Enables or disables SMS notifications
     *
     * @param enabled True to enable, false to disable
     */
    public void setSmsNotificationsEnabled(Boolean enabled) {
        this.smsNotificationsEnabled = enabled;
    }
}
