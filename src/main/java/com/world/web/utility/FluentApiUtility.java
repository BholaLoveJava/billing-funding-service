package com.world.web.utility;

import java.util.function.Consumer;

class Mailer {

    private Mailer() {
    }

    public Mailer from(String from) {
        System.out.println("from :: " + from);
        return this;
    }

    public Mailer to(String to) {
        System.out.println("to :: " + to);
        return this;
    }

    public Mailer subject(String subjectLine) {
        System.out.println("subject :: " + subjectLine);
        return this;
    }

    public static void send(Consumer<Mailer> mailerConsumer) {
        Mailer mailer = new Mailer();
        mailerConsumer.accept(mailer);
        System.out.println("sending...");
    }
}

public class FluentApiUtility {

    public static void main(String[] args) {
        Mailer.send(mail ->
                mail.from("bhola.kumar@gmail.com")
                        .to("neha.kumari@gmail.com")
                        .subject("UAT Artifacts"));
    }
}
