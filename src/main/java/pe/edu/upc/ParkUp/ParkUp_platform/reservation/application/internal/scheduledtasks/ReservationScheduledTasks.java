package pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.scheduledtasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.application.internal.outboundservices.NotificationService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.model.queries.GetReservationsNeedingRemindersQuery;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.domain.services.ReservationQueryService;
import pe.edu.upc.ParkUp.ParkUp_platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;

import java.time.LocalDateTime;

/**
 * Scheduled tasks for reservation management
 * Handles automated operations like reminders and expiration checks
 */
@Component
public class ReservationScheduledTasks {

    private final ReservationRepository reservationRepository;
    private final ReservationQueryService reservationQueryService;
    private final NotificationService notificationService;

    public ReservationScheduledTasks(ReservationRepository reservationRepository,
                                     ReservationQueryService reservationQueryService,
                                     NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.reservationQueryService = reservationQueryService;
        this.notificationService = notificationService;
    }

    /**
     * Sends reminder notifications for upcoming reservations
     * Runs every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
    @Transactional(readOnly = true)
    public void sendReservationReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterTime = now;
        LocalDateTime beforeTime = now.plusMinutes(15);

        var query = new GetReservationsNeedingRemindersQuery(afterTime, beforeTime);
        var reservations = reservationQueryService.handle(query);

        reservations.forEach(reservation -> {
            if (reservation.shouldSendReminder(15)) {
                notificationService.sendReservationReminderNotification(
                        reservation.getUserId(),
                        reservation.getId()
                );
            }
        });

        if (!reservations.isEmpty()) {
            System.out.println("Sent " + reservations.size() + " reminder notifications");
        }
    }

    /**
     * Checks for expired reservations and marks them as expired
     * Runs every minute
     */
    @Scheduled(fixedRate = 60000) // 1 minute = 60,000 milliseconds
    @Transactional
    public void checkExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        var expiredReservations = reservationRepository.findExpiredReservations(now);

        expiredReservations.forEach(reservation -> {
            reservation.expire();
            reservationRepository.save(reservation);
            
            // Send expiration notification
            notificationService.sendReservationExpiredNotification(
                    reservation.getUserId(),
                    reservation.getId()
            );
        });

        if (!expiredReservations.isEmpty()) {
            System.out.println("Marked " + expiredReservations.size() + " reservations as expired");
        }
    }
}
