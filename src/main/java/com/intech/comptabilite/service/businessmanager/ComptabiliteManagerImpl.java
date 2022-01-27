package com.intech.comptabilite.service.businessmanager;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intech.comptabilite.model.CompteComptable;
import com.intech.comptabilite.model.EcritureComptable;
import com.intech.comptabilite.model.JournalComptable;
import com.intech.comptabilite.model.LigneEcritureComptable;
import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.service.entityservice.CompteComptableService;
import com.intech.comptabilite.service.entityservice.EcritureComptableService;
import com.intech.comptabilite.service.entityservice.JournalComptableService;
import com.intech.comptabilite.service.entityservice.SequenceEcritureComptableService;
import com.intech.comptabilite.service.exceptions.FunctionalException;
import com.intech.comptabilite.service.exceptions.NotFoundException;

@Service
public class ComptabiliteManagerImpl implements ComptabiliteManager {

    @Autowired
    private EcritureComptableService ecritureComptableService;
    @Autowired
    private JournalComptableService journalComptableService;
    @Autowired
    private CompteComptableService compteComptableService;
    @Autowired
    private SequenceEcritureComptableService sequenceEcritureComptableService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return compteComptableService.getListCompteComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JournalComptable> getListJournalComptable() {
        return journalComptableService.getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return ecritureComptableService.getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    // TODO à implémenter et à tester
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(pEcritureComptable.getDate());
        int last;
        try {
            last = sequenceEcritureComptableService
                    .getDernierValeurByCodeAndAnnee(pEcritureComptable.getJournal().getCode(), cal.get(Calendar.YEAR));
        } catch (NotFoundException ex) {
            last = 0;
        }
        int newSeq = last + 1;

        String stringSeq = String.format("%05d", newSeq);

        pEcritureComptable.setReference(pEcritureComptable.getJournal().getCode() + "-" + cal.get(Calendar.YEAR) + "/" + stringSeq);

        SequenceEcritureComptable sequence = new SequenceEcritureComptable();
        sequence.setAnnee(cal.get(Calendar.YEAR));
        sequence.setDerniereValeur(last);
        sequenceEcritureComptableService.upsert(sequence);
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * {@inheritDoc}
     */
    // TODO tests à compléter
    public void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!ecritureComptableService.isEquilibree(pEcritureComptable)) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
            throw new FunctionalException(
                    "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        //===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
        if (pEcritureComptable.getReference() != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(pEcritureComptable.getDate());

            var string = pEcritureComptable.getReference().split("-", 4);

            if(!string[0].equals(pEcritureComptable.getJournal().getCode())){
                throw new FunctionalException(
                        "Le code dans la reference de l'ecriture comptable ne correspond pas au code du journal.");
            }


           if(!string[1].split("/", 5)[0].equals(String.valueOf(cal.get(Calendar.YEAR)))){
               throw new FunctionalException(
                       "La date dans la reference de l'ecriture comptable ne correspond pas à la date de l'ecriture.");
           }
        }
    }

        /**
         * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
         * (unicité de la référence, année comptable non cloturé...)
         *
         * @param pEcritureComptable -
         * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
         */
        protected void checkEcritureComptableContext (EcritureComptable pEcritureComptable) throws FunctionalException {
            // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
            if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
                try {
                    // Recherche d'une écriture ayant la même référence
                    EcritureComptable vECRef = ecritureComptableService.getEcritureComptableByRef(pEcritureComptable.getReference());

                    // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                    // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                    // c'est qu'il y a déjà une autre écriture avec la même référence
                    if (pEcritureComptable.getId() == null
                            || !pEcritureComptable.getId().equals(vECRef.getId())) {
                        throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                    }
                } catch (NotFoundException vEx) {
                    // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertEcritureComptable (EcritureComptable pEcritureComptable) throws FunctionalException {
            this.checkEcritureComptable(pEcritureComptable);
            ecritureComptableService.insertEcritureComptable(pEcritureComptable);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void updateEcritureComptable (EcritureComptable pEcritureComptable) throws FunctionalException {
            ecritureComptableService.updateEcritureComptable(pEcritureComptable);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void deleteEcritureComptable (Integer pId){
            ecritureComptableService.deleteEcritureComptable(pId);
        }

        protected Validator getConstraintValidator () {
            Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
            ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
            Validator vValidator = vFactory.getValidator();
            return vValidator;
        }
    }
