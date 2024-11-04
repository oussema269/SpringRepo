package tn.esprit.tpfoyer.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllBlocs() {
        // Arrange
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc());
        mockBlocs.add(new Bloc());

        when(blocRepository.findAll()).thenReturn(mockBlocs);

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBlocsSelonCapacite() {
        // Arrange
        long capacite = 50;
        Bloc bloc1 = new Bloc(); bloc1.setCapaciteBloc(100);
        Bloc bloc2 = new Bloc(); bloc2.setCapaciteBloc(30);
        List<Bloc> mockBlocs = List.of(bloc1, bloc2);

        when(blocRepository.findAll()).thenReturn(mockBlocs);

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(capacite);

        // Assert
        assertEquals(1, result.size());
        assertEquals(100, result.get(0).getCapaciteBloc());
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        Bloc mockBloc = new Bloc();
        mockBloc.setIdBloc(blocId);

        when(blocRepository.findById(blocId)).thenReturn(Optional.of(mockBloc));

        // Act
        Bloc result = blocService.retrieveBloc(blocId);

        // Assert
        assertNotNull(result);
        assertEquals(blocId, result.getIdBloc());
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Assert
        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        Long blocId = 1L;

        // Act
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        // Arrange
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc());
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(mockBlocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        // Arrange
        String nomBloc = "Bloc A";
        long capaciteBloc = 100;
        List<Bloc> mockBlocs = List.of(new Bloc());

        when(blocRepository.findAllByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc)).thenReturn(mockBlocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap(nomBloc, capaciteBloc);

        // Assert
        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }
}
