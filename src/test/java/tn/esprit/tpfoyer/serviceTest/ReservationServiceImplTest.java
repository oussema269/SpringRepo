package tn.esprit.tpfoyer.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllReservations() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        List<Reservation> reservations = List.of(reservation1, reservation2);

        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.retrieveAllReservations();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("123");

        when(reservationRepository.findById("123")).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.retrieveReservation("123");

        assertNotNull(result);
        assertEquals("123", result.getIdReservation());
        verify(reservationRepository, times(1)).findById("123");
    }

    @Test
    void testAddReservation() {
        Reservation reservation = new Reservation();

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.addReservation(reservation);

        assertNotNull(result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testModifyReservation() {
        Reservation reservation = new Reservation();

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.modifyReservation(reservation);

        assertNotNull(result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRemoveReservation() {
        String reservationId = "123";

        doNothing().when(reservationRepository).deleteById(reservationId);

        reservationService.removeReservation(reservationId);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void testTrouverResSelonDateEtStatus() {
        Date date = new Date();
        boolean status = true;
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        List<Reservation> reservations = List.of(reservation1, reservation2);

        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(eq(date), eq(status))).thenReturn(reservations);

        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, status);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, status);
    }
}
