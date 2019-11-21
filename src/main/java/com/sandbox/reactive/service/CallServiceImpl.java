package com.sandbox.reactive.service;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.CallState;
import com.sandbox.reactive.document.Doctor;
import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.queue.CustomQueue;
import com.sandbox.reactive.repository.CallRepository;
import com.sandbox.reactive.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@AllArgsConstructor
@Service
public class CallServiceImpl implements CallService{

    private CallRepository repository;

    @Autowired
    private ReactiveMongoTemplate template;

    @Override
    public Flux<Call> testReactiveDataBase() {
        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(
                        newAggregation(
                                Call.class,
                                Aggregation.match(Criteria.where("operationType").is("insert"))
                        )
                ).returnFullDocumentOnUpdate().build();

        Flux<Call> callFlux = template.changeStream("playList", options, Call.class)
                .map(ChangeStreamEvent::getBody)
                .doOnNext(p -> System.out.println("*** >>>> " + p));
        return callFlux;
    }

    public void testCallQueue() throws InterruptedException {

        Doctor a = Doctor.builder().code(1).name("A").build();
        Doctor b = Doctor.builder().code(2).name("B").build();
        Doctor c = Doctor.builder().code(3).name("C").build();
        Doctor d = Doctor.builder().code(4).name("D").build();

        CustomQueue<Doctor> doctorScale1 = new CustomQueue<>();
        doctorScale1.add(a);
        doctorScale1.add(b);

        CustomQueue<Doctor> doctorScale2 = new CustomQueue<>();
        doctorScale2.add(a);
        doctorScale2.add(c);
        doctorScale2.add(d);

        CustomQueue<Doctor> doctorNormalBusy = new CustomQueue<>();

        Call call1 = Call.builder()
                .requestedUserCode(1)
                .uuid(UUID.randomUUID().toString())
                .build();
        Call call2 = Call.builder()
                .requestedUserCode(2)
                .uuid(UUID.randomUUID().toString())
                .build();
        System.out.println("[Chamando...] ");
        Thread enf1 = new Thread(() -> {
            Iterator<Doctor> itDoctors = doctorScale1.iterator();
            while(itDoctors.hasNext()){
                try {
                    Doctor doctor = itDoctors.next();
                    if(!doctorNormalBusy.contains(doctor)) {

                        call1.setDoctor(doctor);
                        call1.setCallState(CallState.CALLING);
                        doctorNormalBusy.addLast(doctor);

                        Thread.sleep(5000);

//                        call1.setCallState(null);
//                        doctorNormalBusy.removeLast();

                        System.out.println("[scale 1] Enfermeiro 1 -> " + doctor.getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread enf2 = new Thread(() -> {
            Iterator<Doctor> itDoctors = doctorScale2.iterator();
            while(itDoctors.hasNext()){
                try {
                    Doctor doctor = itDoctors.next();
                    if(!doctorNormalBusy.contains(doctor)) {

                        call2.setDoctor(doctor);
                        call2.setCallState(CallState.CALLING);
                        doctorNormalBusy.addLast(doctor);

                        Thread.sleep(5000);

//                        call2.setCallState(null);
//                        doctorNormalBusy.removeLast();

                        System.out.println("[scale 2] Enfermeiro 2 -> " + doctor.getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        enf1.start();
        Thread.sleep(1000);
        enf2.start();


    }


}
