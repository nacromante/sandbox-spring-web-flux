package com.sandbox.reactive;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.CallState;
import com.sandbox.reactive.document.Doctor;
import com.sandbox.reactive.queue.CustomQueue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.UUID;

//@RunWith(SpringRunner.class)
//@DataMongoTest
@SpringBootTest
public class CallQueueTest {


    @Test
    public void testCallQueue() throws InterruptedException {

        Doctor a = Doctor.builder().mapNumberOfCalls(new HashMap<>()).code(1).name("A").build();
        Doctor b = Doctor.builder().mapNumberOfCalls(new HashMap<>()).code(2).name("B").build();
        Doctor c = Doctor.builder().mapNumberOfCalls(new HashMap<>()).code(3).name("C").build();
        Doctor d = Doctor.builder().mapNumberOfCalls(new HashMap<>()).code(4).name("D").build();

        CustomQueue<Doctor> doctorScale1 = new CustomQueue<>();
        doctorScale1.add(a);
        doctorScale1.add(b);

        CustomQueue<Doctor> doctorScale2 = new CustomQueue<>();
        doctorScale2.add(a);
        doctorScale2.add(c);
        doctorScale2.add(d);

        CustomQueue<Doctor> doctorNormalBusy = new CustomQueue<>();


        Call call2 = Call.builder()
                .requestedUserCode(2)
                .uuid(UUID.randomUUID().toString())
                .build();

        System.out.println("[Chamando...] ");
        Thread enf1 = new Thread(() -> {
//            Iterator<Doctor> itDoctors = doctorScale1.iterator();
            Call call1 = Call.builder()
                    .requestedUserCode(1)
                    .uuid(UUID.randomUUID().toString())
                    .build();
            while (!doctorScale1.isEmpty()) {
                try {
                    Doctor doctor = doctorScale1.pop();
                    if (!doctorNormalBusy.contains(doctor)) {

                        if(doctor.getNumberOfCalls(call1.getUuid()) > 2){
                            Thread.currentThread().interrupt();
                            break;
                        }
                        doctor.incrementNumberCall(call1.getUuid());
                        call1.setDoctor(doctor);
                        call1.setCallState(CallState.CALLING);

                        doctorNormalBusy.addLast(doctor);

                        Thread.sleep(5000);

                        doctorNormalBusy.remove(doctor);
                        doctorScale1.addLast(doctor);

                        System.out.println("[scale 1] Enfermeiro 1 -> " + doctor.getName() + " -> num " + call1.getNumberOfCalls());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                catch (NoSuchElementException e ) {
//                    System.out.println(">>>> size "+doctorScale1.size());
//                    break;
//                }
            }
        });

/*
        Thread enf2 = new Thread(() -> {
//            Iterator<Doctor> itDoctors = doctorScale2.iterator();
            while (true) {
                try {
                    Doctor doctor = doctorScale2.pop();
                    if (!doctorNormalBusy.contains(doctor)) {

                        call2.setDoctor(doctor);
                        call2.setCallState(CallState.CALLING);
                        doctorNormalBusy.addLast(doctor);

                        Thread.sleep(5000);

//                        doctorNormalBusy.remove(doctor);
                        doctorScale2.addLast(doctor);
//                        call2.setCallState(null);
//                        doctorNormalBusy.removeLast();

                        System.out.println("[scale 2] Enfermeiro 2 -> " + doctor.getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        */

        enf1.start();
        Thread.sleep(1000);
//        enf2.start();

        while (true){

        }

    }

}
