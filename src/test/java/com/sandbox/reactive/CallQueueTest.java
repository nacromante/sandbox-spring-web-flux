package com.sandbox.reactive;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.CallState;
import com.sandbox.reactive.document.Doctor;
import com.sandbox.reactive.queue.CustomQueue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

//@RunWith(SpringRunner.class)
//@DataMongoTest
@SpringBootTest
public class CallQueueTest {



    @Test
    public void testCallQueue(){

        Doctor a = Doctor.builder().code(1).name("A").build();
        Doctor b = Doctor.builder().code(2).name("B").build();
        Doctor c = Doctor.builder().code(3).name("C").build();
        Doctor d = Doctor.builder().code(4).name("D").build();

        CustomQueue<Doctor> doctorScale1 = new CustomQueue<>();
        doctorScale1.add(a);
        doctorScale1.add(b);

        CustomQueue<Doctor> doctorScale2 = new CustomQueue<>();
        doctorScale1.add(a);
        doctorScale1.add(c);
        doctorScale1.add(d);

        CustomQueue<Doctor> doctorNormalBusy = new CustomQueue<>();

        Call call1 = Call.builder()
                .requestedUserCode(1)
                .uuid(UUID.randomUUID().toString())
                .build();
        Call call2 = Call.builder()
                .requestedUserCode(2)
                .uuid(UUID.randomUUID().toString())
                .build();

        Thread enf1 = new Thread(() -> {
            while(true){
                try {
                    Doctor doctor = doctorScale1.peek();
                    if(!doctorNormalBusy.contains(doctor)) {
                        Thread.sleep(15000);
                        call1.setDoctor(doctor);
                        call1.setCallState(CallState.CALLING);
                        doctorNormalBusy.addFirst(doctor);

                        System.out.println("[scale 1] Enfermeiro 1 -> " + doctor.getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        enf1.start();

//        Thread enf2 = new Thread(() -> {
//            while(true){
//                try {
//                    Doctor doctor = doctorScale2.peek();
//                    if(!doctorNormalBusy.contains(doctor)) {
//                        Thread.sleep(15000);
//                        call2.setDoctor(doctor);
//                        call2.setCallState(CallState.CALLING);
//                        doctorNormalBusy.addFirst(doctor);
//
//                        System.out.println("Enfermeiro 2 -> " + doctor.getName());
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });



    }


}
