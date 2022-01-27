package com.intech.comptabilite.service.businessmanager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.intech.comptabilite.model.*;
import com.intech.comptabilite.service.entityservice.SequenceEcritureComptableService;
import com.intech.comptabilite.service.exceptions.NotFoundException;
import com.intech.comptabilite.utils.Tools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.intech.comptabilite.service.exceptions.FunctionalException;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ComptabiliteManagerImplTest {

    @MockBean
    SequenceEcritureComptableService sequenceEcritureComptableService;

    @Autowired
    private ComptabiliteManagerImpl manager;

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));


        Assertions.assertDoesNotThrow(() -> manager.checkEcritureComptableUnit(vEcritureComptable));

        vEcritureComptable.setJournal(null);
        Assertions.assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });

    }

    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        Assertions.assertThrows(FunctionalException.class,
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                }
        );

        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("libelle");
        vEcritureComptable.setJournal(new JournalComptable("code", "libelle"));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        Assertions.assertDoesNotThrow(
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                }
        );
    }

    @Test
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));

        Assertions.assertThrows(FunctionalException.class,
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                }
        );

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, new BigDecimal(1234),
                null));

        Assertions.assertDoesNotThrow(
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                });
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        Assertions.assertThrows(FunctionalException.class,
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                }
        );

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));

        Assertions.assertDoesNotThrow(
                () -> {
                    manager.checkEcritureComptableUnit(vEcritureComptable);
                }
        );
    }

        @Test
        public void checkEcritureComptableUnitRG5() throws FunctionalException {

        EcritureComptable vEcritureComptable= this.getValidEcritureComptable();

            Assertions.assertDoesNotThrow(
                    () -> {
                        manager.checkEcritureComptableUnit(vEcritureComptable);
                    }
            );

            vEcritureComptable.setReference("xx-1999/99999");

            Assertions.assertThrows(FunctionalException.class,
                    () -> {
                        manager.checkEcritureComptableUnit(vEcritureComptable);
                    }
            );
    }

    @Test
    public void checkEcritureComptable() throws FunctionalException {

        EcritureComptable vEcritureComptable;
        vEcritureComptable = this.getValidEcritureComptable();

        Assertions.assertDoesNotThrow(
                () -> {
                    manager.checkEcritureComptable(vEcritureComptable);
                }
        );

        vEcritureComptable.setReference("bullshit");

        Assertions.assertThrows(FunctionalException.class,
                () -> {
                    manager.checkEcritureComptable(vEcritureComptable);
                }
        );
    }

    @Test
    public void checkEcritureComptableContext() {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, new BigDecimal(123),
                null));

        vEcritureComptable.setReference("AC-2016/00001");

        Assertions.assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableContext(vEcritureComptable);
        });

        vEcritureComptable.setReference("AC-2016/9999");
        Assertions.assertDoesNotThrow(
                () -> {
                    manager.checkEcritureComptableContext(vEcritureComptable
                    );

                });
    }

    @Test
    public  void addReference() throws NotFoundException, FunctionalException {

        EcritureComptable vEcritureComptable = getValidEcritureComptable();
        final String code = vEcritureComptable.getJournal().getCode();

        SequenceEcritureComptable seq = new SequenceEcritureComptable();
        int yr = 2022;
        seq.setAnnee(yr);
        int pDerniereValeur = 1;
        seq.setDerniereValeur(pDerniereValeur);

        Mockito.
                when(sequenceEcritureComptableService.getDernierValeurByCodeAndAnnee(code, yr)).thenReturn(pDerniereValeur);

        final String sequence  = vEcritureComptable.getReference().split("/", 5)[1];

        manager.addReference(vEcritureComptable);

        Assertions.assertEquals(getEcritureComptableReference(vEcritureComptable, String.valueOf(pDerniereValeur+1)),
                vEcritureComptable.getReference());
    }

private String getEcritureComptableReference(EcritureComptable ecritureComptable, String seq){
    Calendar cal = new GregorianCalendar();
    cal.setTime((ecritureComptable.getDate()));
    return ecritureComptable.getJournal().getCode() + "-" + cal.get(Calendar.YEAR) + "/" + String.format("%05d", Integer.valueOf(seq));
}

    private EcritureComptable getValidEcritureComptable() throws FunctionalException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2022/00099");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, new BigDecimal(123),
                null));

        manager.checkEcritureComptable(vEcritureComptable);
       return vEcritureComptable;
    }
}
