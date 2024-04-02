package ma.emsi.hopital;

import ma.emsi.hopital.entities.*;
import ma.emsi.hopital.repositories.ConsultationRepository;
import ma.emsi.hopital.repositories.MedecinRepository;
import ma.emsi.hopital.repositories.PatientRepository;
import ma.emsi.hopital.repositories.RendezVousRepository;
import ma.emsi.hopital.service.IpHopitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HopitalApplication {

    public static void main(String[] args) {

        SpringApplication.run(HopitalApplication.class, args);
    }
    @Bean
    CommandLineRunner start(IpHopitalService hopitalService,
                            PatientRepository patientRepository,
                            RendezVousRepository rendezVousRepository,
                            MedecinRepository medecinRepository ){
        return args -> {
            Stream.of("Mohamed","Hassan","Najat")
                    .forEach(name->{
                        Patient patient = new Patient();
                        patient.setNom(name);
                        patient.setDateNaissance(new Date());
                        patient.setMalade(false);
                        hopitalService.savePatient(patient);
                    });
            Stream.of("aymen","Hanane","yasmine")
                    .forEach(name->{
                        Medecin medecin = new Medecin();
                        medecin.setNom(name);
                        medecin.setEmail(name+"@gmail.com");
                        medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
                        hopitalService.saveMedecin(medecin);
                    });
            Patient patient=patientRepository.findById(1L).orElse(null);
            Patient patient1=patientRepository.findByNom("Mohamed");

            Medecin medecin=medecinRepository.findByNom("yasmine");

            RendezVous rendezVous=new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setStatus(StatusRDV.PENDING);
            rendezVous.setMedecin(medecin);
            rendezVous.setPatient(patient);
            RendezVous saveDRDV=hopitalService.saveRDV(rendezVous);
            System.out.println(saveDRDV.getId());



            RendezVous rendezVous1=rendezVousRepository.findAll().get(0);
            Consultation consultation=new Consultation();
            consultation.setDateConsultation(new Date());
            consultation.setRendezVous(rendezVous1);
            consultation.setRapport("Rapport de la consultation ....");
            hopitalService.saveConsultation(consultation);






        };
    }
}
