package tn.esprit.tpfoyer.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);

        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> result = chambreService.retrieveAllChambres();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveChambre() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.retrieveChambre(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddChambre() {
        Chambre chambre = new Chambre();

        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.addChambre(chambre);

        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testModifyChambre() {
        Chambre chambre = new Chambre();

        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.modifyChambre(chambre);

        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testRemoveChambre() {
        Long chambreId = 1L;

        doNothing().when(chambreRepository).deleteById(chambreId);

        chambreService.removeChambre(chambreId);

        verify(chambreRepository, times(1)).deleteById(chambreId);
    }

    @Test
    void testRecupererChambresSelonTyp() {
        TypeChambre type = TypeChambre.SIMPLE;
        Chambre chambre = new Chambre();
        chambre.setTypeC(type);

        when(chambreRepository.findAllByTypeC(type)).thenReturn(List.of(chambre));

        List<Chambre> result = chambreService.recupererChambresSelonTyp(type);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getTypeC());
        verify(chambreRepository, times(1)).findAllByTypeC(type);
    }

    @Test
    void testTrouverChambreSelonEtudiant() {
        long cin = 12345678L;
        Chambre chambre = new Chambre();

        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        Chambre result = chambreService.trouverchambreSelonEtudiant(cin);

        assertNotNull(result);
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }
}
